%Liam Kokab (lko015)
clear all;
clc;
load('data.mat');

kernelSizes = 9;
stepSize = 2;

myMap = imgaussfilt(map, kernelSizes);
%draw map
contour(myMap',50);
hold on;

for i=1:8

    posX = coord(i,1);
    posY = coord(i,2);
    vx = 0;
    vy = 0;
    dis = 0;
    step = 0;
    while true
        %getting gradiant
        [FX,FY] = gradient(posX,posY, myMap);
        %changing direction to downward
        vx = -FX;
        vy = -FY;
    
        %calculating vector lenght
        vectorLenght = sqrt(vx*vx + vy*vy);
        
        %dynamic stepSize;
        %stepSize = (vx^2 + 9*vy^2)/(2*vx^2 + 54*vy^2);
        
        %normalizing and multipling with stepSize to find news pos 
        nposX = posX + vx / vectorLenght * stepSize;
        nposY = posY + vy / vectorLenght * stepSize;
        
        %terminating if we get null vector
        if isnan(nposX) || isnan(nposY)
            scatter(posX, posY, 'ro');
            break;
        end
        
        %calculating height diffrence
        h = middleGround(posX, posY, myMap) - middleGround(nposX, nposY, myMap);
        
        %terminating if we start walking upwords
        if h <= 0
            scatter(posX, posY, 'ro');
            break;
        end
        %counting steps
        step = step + 1;
        
        plot([posX,nposX], [posY,nposY], 'r');
        %distance for this step will be added to total distance.
        dis = dis + sqrt((nposX-posX)^2 + (nposY-posY)^2 + h^2);
        %moving to new pos
        posX = nposX;
        posY = nposY;
        
    end
    fprintf('distance: %.2fm on %i steps\n', dis, step);
end

hold off;
