--Liam Kokab
module Oblig1 where
import Data.Char

data Ast = Nr Int
         | Sum Ast Ast
         | Mul Ast Ast
         | Min Ast
         | If Ast Ast Ast
         | Let String Ast Ast
         | Var String 
         deriving (Eq, Show)

parse :: String -> Ast
parse s = fst (parseExpr s "")

parseExpr :: String -> String -> (Ast, String)
parseExpr [] c = error "Empty String"

parseExpr ('+':xs) c = let (e1, r1) = parseExpr xs c;
                           (e2, r2) = parseExpr r1 c in (Sum e1 e2, r2)
                         
parseExpr ('*':xs) c = let (e1, r1) = parseExpr xs c;
                           (e2, r2) = parseExpr r1 c in (Mul e1 e2, r2)
                        
parseExpr ('-':xs) c = let (e1, r1) = parseExpr xs c in (Min e1, r1)

parseExpr ('i' : 'f':xs) c = let (e1, r1) = parseExpr xs c;
                                 (e2, r2) = parseExpr r1 c;
                                 (e3, r3) = parseExpr r2 c in (If e1 e2 e3, r3)

-- here I add any variable added by user to a list "c".
-- this list will later be used to check when a variable i used
parseExpr ('l' : 'e' : 't':xs) c = let l = dropWhile (' '==) xs
                                       e1 = takeWhile isUpper l
                                       r1 = dropWhile isUpper l                                       
                                   in if null e1 then error ("expected an Upper variable");
                                      else let c1 = c ++ e1
                                               (e2, r2) = parseExpr r1 c1;
                                               (e3, r3) = parseExpr r2 c1 in (Let e1 e2 e3, r3)
                                               
parseExpr (' ':xs) c = parseExpr xs c
parseExpr ('=':xs) c = parseExpr xs c
parseExpr ('i' : 'n' :xs) c = parseExpr xs c                                      
-- any upper that dose not come right after "let" is assumed to be a variable and need have been 
-- previsly declared.
-- if isUpper i use my search function to see if it exist in the list of set variables
-- if is't fund then we use it, if it's not found I will write a error massage explining what happend.                                 
parseExpr (x:xs) c = if isDigit x then (Nr (read (takeWhile isDigit (x:xs)) :: Int), (dropWhile isDigit xs))
                     else if isUpper x then 
                        if (search x c) then 
                            (Var [x], xs) 
                        else error ("the variable \"" ++ [x] ++ "\" has not been declared for " ++ [x] ++ xs)
                     else error ("syntax error at \"" ++ [x] ++ xs ++ "\"" )                      
                                                               
eva :: Int -> [Int] -> Ast -> Int
eva 1 li (Nr n) = n
eva 0 li (Nr n) = n `mod` 2

eva i li (Sum a b) = eva i li a + eva i li b
eva i li (Mul a b) = eva i li a * eva i li b
eva 0 li (Min a) = if (eva 0 li a) > 0 then 0 else 1 
eva 1 li (Min a) = - (eva 1 li a)
eva i li (If a b c) = let ea = eva i li a 
                      in if(i == ea || (i == 1 && i < ea)) then eva i li c else eva i li b

eva i li (Let a b c) = let index = (ord (head a))-65;
                           b1 = eva i li b;
                           li1 = rI index b1 li in eva i li1 c

eva i li (Var a) = li !! ((ord (head a))-65)

--search function with parameter char and String(list of char)
--return true if char is in String, false if it's not found !
search :: Char -> String -> Bool
search x (y:ys) = if x == y then True else search x ys
search x [] = False

--replacing elemtn at index
rI n newVal (x:xs) 
    | n == 0 = newVal:xs
    | otherwise = x:rI (n-1) newVal xs

--crearting a list of lenght 26
listOfN :: [Int]
listOfN = take 26 (repeat 0)

evb :: String -> Bool
evb s = (eva 0 listOfN (parse s)) > 0
--evb a = a `mod` 2 == 0

evi :: String -> Int
evi s = eva 1 listOfN (parse s)
