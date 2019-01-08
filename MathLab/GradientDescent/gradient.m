%Liam Kokab (lko015)
function [FX,FY] = gradient(x, y, myMap)
    FX = (middleGround (x+1, y, myMap) - middleGround (x-1, y, myMap))/2;
    FY = (middleGround (x, y+1, myMap) - middleGround (x, y-1, myMap))/2;
end
