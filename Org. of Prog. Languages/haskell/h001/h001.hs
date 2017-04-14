import System.Process
-- import Data.List.Split

getGitLog = do
  gitLog <- readProcess "git" ["log"] ""
  return gitLog


saveToFile gitLog = do
  writeFile "log1.txt" gitLog


splitWhen :: (Char -> Bool) -> String -> [String]
splitWhen p s = case dropWhile p s of
  "" -> []
  s' -> w : splitWhen p s''
    where (w, s'') = break p s'


main = do
  putStrLn "Getting Git Log..."
  gitLog <- getGitLog
  saveToFile gitLog
  print $ splitWhen (=='\n') gitLog
