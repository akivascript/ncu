(ns ncu.prompt
  (:require [numenera.character :as char]))

(defn input
  []
  (print  "> "))
  (flush)
  (read-line))

(defn help
  []
  (let [commands ["Numenera Character Utility\n"
                  "help or h\t\ttPrints this help documentation.\n"
                  "age [get|set [num]]\t\tAdjusts or returns a character's age"]]
   (apply println commands)))

(defn increase
  [c cmd]
  (case cmd
    "age" (update c :age inc)
    "effort" (update c :effort inc)
    "Please enter a proper value to increase\n\tage\n\teffort"))

(defn parse
  [cmd c & args]
  (case cmd
    ("help" "h") (help)
    ("age" "ag") (age c)
    ("arc" "ar") (arc c)
    ("artifacts" "arts") (filter #(= (:type %) :artifacts) (possessions c))
    ("birthplace" "bp") (birthplace c)
    ("cyphers" "cyph") (filter #(= (:type %) :cyphers) (possessions c))
    ("descriptor" "desc") (descriptor c)
    ("effort" "eff") (effort c)
    ("equipment" "eq") (filter #(= (:type %) :equipment) (possessions c))
    ("focus" "f") (focus c)
    ("gender" "g") (gender c)
    ("heritage" "h") (heritage c)
    ("name" "n") (name' c)
    ("oddities" "o") (filter #(= (:type %) :oddities) (possessions c))
    ("possessions" "p") (possessions c)
    ("stats" "st") (stat (first arg))
    ("story" "sto")  (story c)
    ("summary" "su") (summary c)
    ("type" "t") (type' c)

    "add" (case (first args)
            "artifact" (k)
            "equipment" ())

    "print" (case (first args)
              (output c))
    "set" (case (first args)
            ("age" "ag") (age c (second args))
            ("arc" "ar") (arc c (second args))
            ("birthplace" "bp") (birthplace c (second args))
            ("descriptor" "desc") (descriptor c (second args))
            ("effort" "eff") (effort c (second args))
            ("focus" "f") (focus c (second args))
            ("gender" "g") (gender c (second args))
            ("heritage" "h") (heritage c (second args))
            ("name" "n") (name' c (second args))
            ("stat" "st") (stat (rest args))
            ("story" "sto")  (story c (second args))
            ("summary" "su") (summary c (second args))
            ("type" "t") (type' c (second args))
            "Please enter a valid input.")))

(defn interface
  []
  (loop [cmd (input)]

    ))

