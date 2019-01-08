%Liam Kokab (lko015)
function v = middleGround (x, y, myMap)
%aka Bilinear interpolation
    cf = myMap(ceil(x), floor(y));
    cc = myMap(ceil(x), ceil(y));
    fc = myMap(floor(x), ceil(y));
    ff = myMap(floor(x), floor(y));

    FQ = [ff, fc; cf, cc];
    
    x = mod(x,1) + 1;
    y = mod(y,1) + 1;
    
    v = [2-x,x-1] * FQ * [2-y;y-1];
end
