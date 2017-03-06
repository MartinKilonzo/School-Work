-- numString = "   1 22      90    33  00001"
--
-- eraseSpace = map (\c -> if c == ' ' then ''; else c)
--
-- splitWhen :: (Char -> Bool) -> String -> [String]
--     wordsWhen c s = case dropWhile c s of
--                         '' -> []
--                         s' -> w : splitWhen c s''
--                               where (w, s'') = break c s'
--

symbol :: Parser Char
symbol = oneOf "!#$%&|*+-/:<=>?@^_~"

readExpr :: String -> String
readExpr input = case parse symbol "lisp" input of
    Left err -> "No match: " ++ show err
    Right val -> "Found value"

main :: IO ()
main = do
         (expr:_) <- getArgs
         putStrLn (readExpr expr)
