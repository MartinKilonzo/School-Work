function [w, minErr] = p5(X_train, Y_train, iterNum)
minErr = inf;
for iter = 1:iterNum
    iterWeights = randn(size(X_train, 2) + 1, 1);
    prediction = p4(iterWeights, X_train);   
    [err, confMat] = p2(prediction, Y_train);
    
    if err < minErr
        w = iterWeights;
        minErr = err;
    end
end

    