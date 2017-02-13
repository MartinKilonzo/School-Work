numString = "   1 22      90    33  00001"

eraseSpace = map (\c -> if c == ' ' then ''; else c)

splitWhen :: (Char -> Bool) -> String -> [String]
    wordsWhen c s = case dropWhile c s of
                        '' -> []
                        s' -> w : splitWhen c s''
                              where (w, s'') = break c s'
