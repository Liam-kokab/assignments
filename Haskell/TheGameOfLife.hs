-- Liam Kokab
-- s 2 3 b 3 3 30 25 4 24 5 24 6 24 7 26 7 27 7 28 6 28 4 25 7
-- when running the program, start it with life

import Control.Concurrent
import Data.List
import Data.Char

type Board = [Pos]
type Pos   = (Int, Int)
type Rule  = (Int, Int, Int, Int) -- bornMin bornMax sivMin siv Max
type Rules = (Rule, Int) -- Rule , size
type Nboard = (Rules, Board)

born :: Rule -> (Int, Int)
born (x,y,_,_) = (x,y)

siv :: Rule -> (Int, Int)
siv (_,_,x,y) = (x,y)

cls :: IO ()
cls = putStr "\ESC[2J"

writeat :: Pos -> String -> IO ()
writeat p xs = do goto p
                  putStr xs

goto :: Pos -> IO ()
goto (x,y) = putStr ("\ESC[" ++ show (y+1) ++ ";" ++ show (3*x+1) ++ "H")

size :: Int
size = 5

--retuns a list with pos for all dead cell
getDeadBoard :: Board -> Rules -> Board
getDeadBoard [] r = [ (x, y) | x <- [1..(snd r)] , y <- [1..(snd r)] ]
getDeadBoard (e:b) r = delete e (getDeadBoard b r)

showcells :: Board -> Rules -> IO ()
showcells b r = 
            do
            sequence_ [writeat p "X" | p <- b]
            sequence_ [writeat o "·" | o <- getDeadBoard b r]
            
isAlive :: Board -> Pos -> Bool
isAlive b p = elem p b

isEmpty :: Board -> Pos -> Bool
isEmpty b p = not (isAlive b p)

neighbs :: Pos -> [Pos]
neighbs (x,y) =     [ (x-1, y-1), (x, y-1), (x+1, y-1)
                    , (x-1, y  ),           (x+1, y  )
                    , (x-1, y+1), (x, y+1), (x+1, y+1)]

wrap :: Pos -> Rules -> Pos
wrap (x,y) r = (((x-1) `mod` (snd r)) + 1,
              ((y-1) `mod` (snd r)) + 1)

liveneighbs :: Board -> Pos -> Int
liveneighbs b = length . filter (isAlive b) . neighbs

survivors :: Board -> Rules -> [Pos]
survivors b r = [p | p <- b, elem (liveneighbs b p) [(fst (siv (fst r)))..(snd (siv (fst r)))]] 

births :: Board -> Rules -> [Pos]
births b r = if(fst (born (fst r)) /= 0) then [p | p <- rmdups (concat (map neighbs b)),
                                             outOfBounds p r, 
                                             isEmpty b p,
                                             liveneighbs b p >= fst (born (fst r)),
                                             liveneighbs b p <= snd (born (fst r))] 
             else [(x, y) | x <- [1..(snd r)] , y <- [1..(snd r)]] 

outOfBounds :: Pos -> Rules -> Bool
outOfBounds p r = (((fst p) <= (snd r)) && ((snd p) <= (snd r)) &&
                   ((fst p) >= 1      ) && ((snd p) >= 1))
                
                
rmdups :: Eq a => [a] -> [a]
rmdups [] = []
rmdups (x:xs) = x : rmdups (filter (/= x) xs)

nextgen :: Board -> Rules -> Board
nextgen b r = (survivors b r) ++ (births b r)

writeSideNum :: Int -> Rules -> IO ()
writeSideNum a r = if(a <= (snd r)) then do
                    goto (a, 0)
                    putStr (show a)
                    goto (0, a)
                    putStr (show a)
                    writeSideNum (a+1) r
                    else return ()
                    
--life :: Board -> IO ()
life = do
    cls
    lifeLoop [] ((3, 3, 2, 3) , 0)

lifeLoop :: Board -> Rules -> IO ()
lifeLoop b r = do
    writeSideNum 1 r
    showcells b r
    goto (0, (snd r)+1)
    putStr "commend:                          "
    goto (3, (snd r)+1)
    c <- getLine
    commend c b r

lifeLoopAuto :: Board -> Rules -> Int -> Int -> IO ()
lifeLoopAuto b r s i = let nb = (nextgen b r) in 
    if((i > 0) && (b /= nb)) then do
        cls
        writeSideNum 1 r
        showcells nb r
        threadDelay 50000
        lifeLoopAuto nb r s (i-1)
    else if(b == nb) then do
        goto (0, (snd r)+2)
        putStr ("Stable state! after runing " ++ show (s - i) ++ " times")
        --threadDelay 3000000
        lifeLoop nb r
    else 
        lifeLoop nb r
      
commend :: String -> Board -> Rules -> IO ()
commend [] b r = do 
    cls
    lifeLoop (nextgen b r) r
commend ('c':' ':xs) b r = do 
    cls
    lifeLoop [] ((fst r), (getNum xs))
commend ('n':' ':xs) b r = lifeLoop (b ++ [getPos xs]) r
commend ('d':' ':xs) b r = lifeLoop (delete (getPos xs) b) r
commend ('s':' ':xs) b r = let a = getPos xs in
    lifeLoop b (((fst (born (fst r))),(snd (born (fst r))), (fst a), (snd a)), (snd r))
commend ('b':' ':xs) b r = let a = getPos xs in
    lifeLoop b (((fst a), (snd a), (fst (siv (fst r))),(snd (siv (fst r)))), (snd r))
commend ('?':xs) b r = do
    goto (0, (snd r)+2)
    putStr ("Rules:                                       \n"  ++
                        "S: (x=" ++ (show (fst (siv (fst r)))) ++
                          ", y=" ++ (show (snd (siv (fst r)))) ++
                  ")\n   B: (x=" ++ (show (fst (born (fst r)))) ++
                          ", y=" ++ (show (snd (born (fst r)))) ++ ")")
    lifeLoop b r

commend ('r':' ':xs) b r = rFile xs
commend ('w':' ':xs) b r = do 
    writeFile xs (stringFile b r)
    lifeLoop b r
    
commend ('l':' ':xs) b r = lifeLoopAuto b r (getNum xs) (getNum xs)
commend ('q':xs) b r =  do 
    cls
    goto (0, 0)
    return ()
commend ('g':xs) b r = lifeLoop glider r
commend xs b r = do 
    goto (0, (snd r)+1)
    putStr "unkown commend!              "
    threadDelay 1000000
    lifeLoop b r 
    
glider :: Board
glider = [(4,2), (2,3), (4,3), (3,4), (4,4)]

getPos :: String -> (Int,Int)
getPos (' ':xs) = getPos (dropWhile (' '==) xs)
getPos xs = ((getNum xs), (getNum (dropWhile isDigit xs)))

getNum :: String -> Int
getNum (' ':xs) = getNum (dropWhile (' '==) xs)
getNum xs = (read (takeWhile isDigit xs) :: Int)

stringFile :: Board -> Rules -> String
stringFile b r = (getRule r) ++ (getBord b) 

getRule :: Rules -> String
getRule r = "s " ++ (show (fst (siv(fst r)))) ++ " " ++ (show (snd (siv(fst r)))) ++ " b " ++ (show (fst (born(fst r)))) ++ " " ++ (show (snd (born(fst r)))) ++ " " ++ (show (snd r)) 

getBord :: Board -> String
getBord [] = []
getBord (x:xs) = " " ++ (show (fst x)) ++ " " ++ (show (snd x)) ++ (getBord xs)

rFile :: String -> IO ()
rFile xs = do 
    cls
    s <- (readFile xs)
    rFile2 s
    
rFile2 xs = let n = fileParse (words xs) in
    lifeLoop (snd n) (fst n)

fileParse :: [String] -> Nboard
fileParse xs = (((fileParseR xs (0,0,0,0)),(getNum (head (drop 6 xs)))), fileParseB (drop 7 xs))

fileParseR :: [String] -> Rule -> Rule
fileParseR ("s":xs) r = 
    let a = (getNum (xs!!0), getNum (xs!!1)) in 
        let b = (born r) in 
            fileParseR (drop 2 xs) ((fst b),(snd b),(fst a),(snd a))

fileParseR ("b":xs) r = 
    let a = (getNum (xs!!0), getNum (xs!!1)) in 
        let b = (siv r) in 
            fileParseR (drop 2 xs) ((fst a),(snd a),(fst b),(snd b))

fileParseR xs b = b

fileParseB :: [String] -> Board
fileParseB [] = []
fileParseB xs = ([sToPos (take 2 xs)] ++ (fileParseB (drop 2 xs)))

sToPos:: [String] -> Pos
sToPos xs = (getNum (xs!!0), getNum (xs!!1))