import System.Process
-- import Text.Regex.Base
-- import Text.Regex.Posix



getGitLog = do
  gitLog <- readProcess "git" ["log"] ""
  return gitLog



main :: IO ()
putStrLn "Getting Git Log..."
-- gitLog <- getGitLog
(=~) :: string -> pattern -> result
"bar" =~ "(foo|bar)" :: Bool
