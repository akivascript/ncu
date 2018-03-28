(ns numenera.core
  (:require [numenera.character :as char])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [character (char/get-character (first args))]
    (println character)))
