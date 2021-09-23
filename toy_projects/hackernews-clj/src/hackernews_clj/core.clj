(ns hackernews-clj.core
  (:require [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.coercion :as coercion]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.multipart :as multipart]
            [reitit.ring.middleware.parameters :as parameters]
            [muuntaja.core :as m]
            [ring.adapter.jetty :as jetty]
            [clojure.java.jdbc :as sql]
            [crypto.password.bcrypt :as bcrypt]
            [clj-jwt.core :refer :all]
            [clj-time.core :refer [now plus days]]
            [clojure.string :as string])
  (:gen-class))


(def db-url "postgres://localhost/hackernews")
(def jwt-secret "oh-my-god-jesus-christ")

(defn jwt-encode [payload]
  (-> {:iss payload 
       :exp (plus (now) (days 1)) 
       :iat (now)}
    jwt 
    (sign :HS256 jwt-secret) 
    to-str))

(defn jwt-decode [token]
  (try
    (-> token str->jwt :claims)
    (catch Exception e nil)))

(defn get-all-posts []
  (sql/query db-url ["select * from posts"]))

(defn insert-new-post [user_id content]
  (sql/insert! db-url :posts {:user_id user_id 
                              :content content 
                              :score 0}))

(defn get-user-creds [username]
  (first (sql/query db-url [(format "select * from users where username='%s'" username)])))

(defn register-user [username password]
  (let [hashed-password (bcrypt/encrypt password)]
    (sql/insert! db-url :users {:username username
                                :password_hash hashed-password})))

(defn login-handler
  [{{{:keys [username password]} :body} :parameters}]
  (if-let [user-creds (get-user-creds username)]
    (if (bcrypt/check password (:password_hash user-creds))
      {:status 200 :body {:token (jwt-encode {:user_id (:id user-creds)
                                              :username (:username user-creds)})}}
      {:status 401 :body {:status "bad login"}})
    {:status 404}))
    
(defn ping-handler [& _]
  {:status 200
   :body {:status "pong"}})


(defn posts-handler [& _]
  {:status 200
   :body {:posts (get-all-posts)}})

(defn add-post-handler [{{{:keys [content]} :body} :parameters {{:keys [user_id]} :iss} :auth}]
  (let [new-record (insert-new-post user_id content)]
    {:status 200
     :body (first new-record)}))

(defn register-handler 
  [{{{:keys [username password]} :body} :parameters}]
  (let [new-user (register-user username password)]
    {:status 200
     :body {:user_id (:id (first new-user))}}))

(defn decode-auth-header [authorization-header]
  (when-not (nil? authorization-header)
    (let [[token-type token] (string/split authorization-header #" " 2)]
      (when (= "Bearer" token-type)
        (jwt-decode token)))))


(defn authenticated? [handler]
  (fn [request]
    (let [auth-header (get-in request [:headers "authorization"])]
      (if-let [decoded-auth-header (decode-auth-header auth-header)]
        (do
          (handler (assoc request :auth decoded-auth-header)))
        {:status 401
         :body {:status "must be authenticated to make this request"}}))))

(def app
  (ring/ring-handler
    (ring/router
      [["/login" {:post {:response {200 {:body {:status string?}}}
                            :parameters {:body {:username string?
                                                :password string?}}
                            :handler login-handler}}]
       ["/register" {:post {:response {200 {:body {:status string?}}}
                            :parameters {:body {:username string?
                                                :password string?}}
                            :handler register-handler}}]
       ["/ping" {:get {:response {200 {:body {:status string?}}}}
                 :handler ping-handler}]
       ["/posts" {:get {:response {200 {:body {:posts list?}}}
                        :handler posts-handler}
                  :post {:response {200 {:body {:content string?}}}
                         :parameters {:body {:content string?}} 
                         :middleware [authenticated?]
                         :handler add-post-handler}}]]
      
      ;; FIXME: copy pasted list of middlewares
      ;; i probably don't need all of them right now.
      {:exception pretty/exception
       :data {:coercion reitit.coercion.spec/coercion
              :muuntaja m/instance
              :middleware [;; swagger feature
                           swagger/swagger-feature
                           ;; query-params & form-params
                           parameters/parameters-middleware
                           ;; content-negotiation
                           muuntaja/format-negotiate-middleware
                           ;; encoding response body
                           muuntaja/format-response-middleware
                           ;; exception handling
                           exception/exception-middleware
                           ;; decoding request body
                           muuntaja/format-request-middleware
                           ;; coercing response bodys
                           coercion/coerce-response-middleware
                           ;; coercing request parameters
                           coercion/coerce-request-middleware
                           ;; multipart
                           multipart/multipart-middleware]}})))
                            
                           

(slurp
  (:body
    (app {:request-method :get
          :uri "/posts"
          :query-params {}})))

(app {:request-method :post
      :uri "/posts"
      :parameters {:body {:content "hello world omg"}}})



(defn start []
  (jetty/run-jetty #'app {:port 3000, :join? false})
  (println "server running in port 3000"))


(start)
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
