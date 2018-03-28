(ns numenera.io
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import java.io.PushbackReader))

(defn suffix-edn
  "If string s does not end with '.edn', suffix s with '.edn'."
  [s]
  (if (str/ends-with? s ".edn")
    s
    (str s ".edn")))

(defn read-file
  "Reads a character file."
  [file]
  (try
    (with-open [file (-> file
                         suffix-edn
                         io/resource
                         io/reader
                         PushbackReader.)]
      (edn/read file))
    (catch Exception e
      (println (str "OH GOD. Failed to load '" file "': " (.getLocalizedMessage e))))))
