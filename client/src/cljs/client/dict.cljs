(ns client.dict)

(def results
  {true "The portion of characters of the first string can be rearranged to match second string"
   false "The portion of characters of the first string can not be rearranged to match second string"})

(def errors
  {:first-string "First string is invalid. String must not be empty and should contain lower case letters (a-z)\n"
   :second-string "Second string is invalid. String must not be empty and should contain lower case letters (a-z)\n"})
