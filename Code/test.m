rng(0);  % set  random number generator to the same starting point

A = [ 1 0 3; 2 1 2; 1 3 3 ];
imSmall = cat(3,A,A,A);
scribbleSmall = [1 0 0; 1 0 0; 0 2 2];

lambda = 1;
numClusters = 0;
inftyCost = 1000;

[segm,e2]  = segmentGC(imSmall,scribbleSmall,lambda,numClusters,inftyCost)
