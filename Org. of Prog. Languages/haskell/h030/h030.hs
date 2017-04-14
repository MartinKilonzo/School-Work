import Control.Monad (unless)

main :: IO ()
main = evaluate

eval :: IO ()
eval = do
  putStrLn "Enter Expression"
  expression <- getLine
  unless (expression == "end") $ showExpression expression
 where
   showExpression expression = do putStrLn $ evaluateExpression expression
                      eval

evaluateExpression :: String -> String
evaluateExpression = show
