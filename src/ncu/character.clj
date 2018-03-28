(ns ncu.character
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [ncu.io :as io]))

(s/def ::age (s/and int? pos?))
(s/def ::arc (s/nilable string?))
(s/def ::birthplace (s/nilable string?))
(s/def ::descriptor string?)
(s/def ::effort (s/and int? (not neg?)))
(s/def ::focus string?)
(s/def ::gender string?)
(s/def ::heritage (s/nilable string?))
(s/def ::name string?)
(s/def ::note (s/nilable string?))
(s/def ::story (s/nilable string?))
(s/def ::summary (s/nilable string?))
(s/def ::tier (s/and int? (not neg?)))
(s/def ::type string?)
(s/def ::xp (s/and int? (not neg?)))

(s/def ::item (s/keys :req [::name]
                      :opt [::note]))
(s/def ::artifacts (s/coll-of ::item))
(s/def ::cyphers (s/coll-of ::item))
(s/def ::equipment (s/coll-of ::item))
(s/def ::oddities (s/coll-of ::item))
(s/def ::possessions (s/keys :req [::equipment]
                             :opt [::artifacts
                                   ::cyphers
                                   ::oddities]))

(s/def ::kind #{:intellect :might :speed})
(s/def ::level #{:trained :specialized})
(s/def ::skill (s/keys :req [::name
                             ::level
                             ::kind]
                       :opt [::note]))
(s/def ::skills (s/coll-of ::skill))

(s/def ::source keyword?)
(s/def ::value (s/and int? (not neg?)))
(s/def ::bonus (s/keys :req [::source
                             ::value]
                       :opt [::note]))

(s/def ::base (s/and int? pos?))
(s/def ::base (s/and int? pos?))
(s/def ::stat (s/keys :req [::kind
                            ::base
                            ::edge]
                      :opt [::bouses]))
(s/def ::stats (s/coll-of ::stat))

(s/def ::ability (s/keys :req [::name]
                         :opt [::note]))
(s/def ::abilities (s/coll-of ::ability))

(def new-char
  {::name nil
   ::descriptor nil
   ::type nil
   ::focus nil
   ::summary nil
   ::arc nil
   ::heritage nil
   ::gender nil
   ::age 18
   ::birthplace nil
   ::story nil
   ::tier 1
   ::effort 1
   ::xp 0
   ::stats [{::kind :intellect
             ::base 0
             ::edge 0}
            {::kind :might
             ::base 0
             ::edge 0}
            {::kind :speed
             ::base 0
             ::edge 0}]
   ::skills []
   ::abilities []
   ::possessions {:equipment []
                 :artifacts []
                 :oddities []
                 :cyphers []}})

(defn age
  ([c]
   (::age c))
  ([c v]
   ()
   (assoc c ::age v)))

(defn arc
  ([c]
   (::arc c))
  ([c v]
   (assoc c ::arc v)))

(defn birthplace
  ([c]
   (::birthplace c))
  ([c v]
   (assoc c ::birthplace v)))

(defn bonus
  ([s v]
   (bonus s v nil))
  ([s v n]
   {::source s ::value v ::note n}))

(defn descriptor
  ([c]
   (::descriptor c))
  ([c v]
   (assoc c ::descriptor v)))

(defn effort
  ([c]
   (::effort c))
  ([c v]
   (assoc c ::effort v)))

(defn focus
  ([c]
   (::focus c))
  ([c v]
   (assoc c ::focus v)))

(defn item
  ([name]
   (item name nil))
  ([name note]
   {::name name ::note note}))

(defn name'
  ([c]
   (::name c))
  ([c v]
   (assoc c ::name v)))

(defn stat
  ([c k]
   (get-in c [::stats k]))
  ([c k k' & v]
   (let [stat (get-in c [::stats k])]
     (case k'
       ::base (::base stat)
       ::pool (reduce + (::base stat)
                      (map ::bonus (::bonuses stat)))
       ::edge (::edge stat)
       "set base" ()))))

(defn story
  ([c]
   (println "Story:") (::story c))
  ([c v]
   (assoc c ::story v)))

(defn skill
  ([s l]
   (skill s l nil))
  ([s l n]
   {::name s ::level l ::note n}))

(defn tier
  ([c]
   (::tier c))
  ([c v]
   ()
   (assoc c ::tier v)))

(defn type'
  ([c]
   (::type c))
  ([c v]
   (assoc c ::type v)))

(defn xp
  ([c]
   (::xp c))
  ([c v]
   (assoc c ::xp v)))
(defn section-title
  [n]
  (println n)
  (println (apply (str (repeat (count n) "-")))))

(defn output
  [c]
  (println (name' c) "is a" (str/lower-case (descriptor c))
           (str/lower-case (type' c)) "who"
           (str/lower-case (str (focus c) ".")))

  (println "Tier:" (tier c))
  (println "Effort:" (effort c))
  (println "XP:" (xp c))

  (section-title "Statistics")

  (section-title "Skills")

  )


(defn get-character
  [name]
  (io/read-file name))

(defn input
  [prompt]
  (print (str (str/capitalize prompt) ": "))
  (flush)
  (read-line))

(defn set-name
  []
  (input "name"))

(defn set-age
  []
  (input "age"))

(defn new-character
  []
  (persistent! (assoc! (transient new-char)
                       :name (set-name)
                       :age (set-age))))
