import System.Process

getGitLog = do
  gitLog <- readProcess "git" ["log"] ""
  return gitLog


saveToFile gitLog = do
  writeFile "log.txt" gitLog


main = do
  putStrLn "Getting Git Log..."
  gitLog <- getGitLog
  saveToFile gitLog
  putStrLn gitLog
