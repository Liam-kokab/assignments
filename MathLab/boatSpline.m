clear all;
clc;
load('boatPoints.mat');
A = zeros(length(X));
h = zeros(size(X));
b = zeros(size(X));
u = zeros(size(X));
c = zeros(size(X));
d = zeros(size(X));
for i=1:(length(X)-1)
        h(i) = X(i+1) - X(i);
        b(i) = (Y(i+1) - Y(i))/h(i);
                      
    if(i ~= 1)
        A(i+1,i) = h(i);
        A(i,i+1) = h(i); 
        A(i,i) = 2*(h(i-1) + h(i));
        u(i) = 6*(b(i) - b(i-1));
    end
end

A = A(2:11,2:11);
u = u(2:11);
z =[0; A\u; 0];
z(1) = 0;
z(length(z)) = 0;
%finding C and D
for i=1:length(X)
    if(i<length(X))
        c(i) = (Y(i+1)/h(i)) - (z(i+1)/6 * h(i));
    else
        c(i) = 0;
    end
    d(i) = (Y(i)/h(i)) - (h(i)/6 * z(i));
end

figure;
hold on;
scatter(X, Y, 'o');
scatter(X, -Y, 'o');
for i=1:(length(X)-1)
   y = zeros(1,25);
   x = linspace(X(i),X(i+1),25);
   for j=1:length(x)
       temp1 = ((z(i+1))/(6*h(i)))*((x(j) - X(i  ))^3);
       temp2 = ( z(i)   /(6*h(i)))*((X(i+1) - x(j))^3);
       temp3 = c(i) * (x(j)   - X(i));
       temp4 = d(i) * (X(i+1) - x(j));
       
       y(j) = temp1 + temp2 + temp3 + temp4; 
   end
   plot(x,y);
   plot(x,-y);
end
hold off
