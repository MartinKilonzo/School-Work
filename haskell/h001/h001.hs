import System.Process
import Data.List.Split

getGitLog = do
  gitLog <- readProcess "git" ["log"] ""
  return gitLog


saveToFile gitLog = do
  writeFile "log.txt" gitLog


main = do
  putStrLn "Getting Git Log..."
  gitLog <- splitOn "commit" getGitLog
  saveToFile gitLog
  putStrLn gitLog
