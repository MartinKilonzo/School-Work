import Text.ParserCombinators.Parsec hiding (spaces)
import System.Environment

import Text.Regex.Base
import Text.Regex.BACKEND

float_constant :: Parser Char
float_constant =

integer_constant :: Parser Char
integer_constant = /(?<!\.)\d+(?!\.)/

factor :: Parser Char
factor = integer_constant
factor = float_constant

term = factor

expr = term



program -> fundecls ';' expr
fundecls ->

readExpr :: String -> String
readExpr input = case parse symbol "lisp" input of
    Left err -> "No match: " ++ show err
    Right val -> "Found value"
