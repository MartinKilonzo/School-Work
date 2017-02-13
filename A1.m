% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% 
% p1(X, Y, 3, 4)
% 
% 
% 
% C = [1; 2 ; 3; 3; 2; 1; 3; 2; 1];
% T = [1; 1 ; 3; 1; 2; 1; 3; 3; 1];
% 
% p2(C, T)
% 
% 
% 
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% X_new = [4 5; 1 3];
% 
% p3(X, Y, X_new, 1)
% 
% 
% 
% w = [-2; 1; -3];
% X = [-1 -2; -1 -4; 3 2];
% 
% p4(w, X)
% 
% 
% 
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [1; 1; 2; 2; 2; 1; 1];
% 
% [w, e] = p5(X, Y, 100)
% 
% [X_digit, Y_digit] = p1(X_train_full, Y_train_full, 5, 10);
% 
% [w, e] = p5(X_digit, Y_digit, 10000)
% 
% % Training Error
% % iter 100: err = 0.3102
% % iter 1000: err = 0.2812
% % iter 10000: err = 0.1968
% 
% [X_digit, Y_digit] = p1(X_test_full, Y_test_full, 5, 10);
% 
% [w, e] = p5(X_digit, Y_digit, 10000)
% 
% % Test Error
% % iter 100: err = 0.3089
% % iter 1000: err = 0.2546
% % iter 10000: err = 0.2310
% 
% 
% 
% X = [2 3; 4 3; 3 5 ; 1 3; 5 6 ];
% Y = [1; 1 ; 1; 2;  2 ];
% iterNum = 3000;
% wInit = [ 1 ;1 ;1 ];
% alpha = 0.1;
% 
% [w, err] = p6(X, Y, iterNum, wInit, alpha)
% 
% [X_digit, Y_digit] = p1(X_train_full, Y_train_full, 5, 10);
% iterNum = 30;
% wInit = ones(size(Y_digit(1)));
% alpha = 0.1;
% 
% [w, err] = p6(X_digit, Y_digit, iterNum, wInit, alpha)
% 
% % iter 30: err = 0.0497
% 
% 
% 
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; -9 8];
% W = [ 2 3 4; -1 -3 4; 5 6 -7];
% 
% p7(W, X)
% 
% 
% 
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% iterNum = 1;
% alpha = 0.1;
% wInit = [ 1 2 -1; 1 2 -1;1 1 -2; -1 2 1];
% 
% p8(X, Y, iterNum, wInit, alpha)
% 
% 
% iterNum = 100;
% wInit = randn(max(Y_train_full), size(X_train_full, 2) + 1);
% alpha = 0.01;
% 
% w = p8(X_train_full, Y_train_full, iterNum, wInit, alpha)
% C = p7(w, X_train_full);
% [e, CONF] = p2(C, Y_train_full);
% i = find(CONF == max(CONF(:)))
% 'The most frequently confused digits were:'
% max(0, floor(i / 10))
% max(0, mod(i, 10) - 1)
% 
% % iter 100: err = 0.1079
% % Most frequently confused digits were 1 and 1
% 
% 
% 
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% iterNum = 1;
% alpha = 0.1;
% wInit = [ 1 2 -1; 1 2 -1;1 1 -2; -1 2 1];
% 
% p9(X, Y, iterNum, wInit, alpha)
% 
% 
% iterNum = 100;
% wInit = randn(max(Y_train_full), size(X_train_full, 2) + 1);
% alpha = 0.01;
% 
% w = p9(X_train_full, Y_train_full, iterNum, wInit, alpha)
% C = p7(w, X_train_full);
% [e, CONF] = p2(C, Y_train_full);
% i = find(CONF == max(CONF(:)))
% 'The most frequently confused digits were:'
% max(0, floor(i / 10))
% max(0, mod(i, 10) - 1)
% 
% % iter 100: err = 0.9155
% % Most frequently confused digits were 3 and 0
% 
% 
% 
% setdemorandstream(pi);  
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8; 1 3 ];
% Y = [4; 1 ; 3; 2; 3; 3; 1; 4];
% H = [3, 2];
% regularizerWeight = 0.1;
% 
% [net, valErr] = p10a(X, Y, H, regularizerWeight);
% valErr
% % valErr = 0.5000
% 
% X_test = [1 1; 0 4; 2 2];
% Y_test = [4;1;3];
% 
% [err, CONF] = p10b(X_test, Y_test, net);
% err, CONF
% 
% % err = 0.6667
% % 
% % CONF =
% %      0     0     0     1
% %      0     0     0     0
% %      0     0     0     1
% %      0     0     0     1
% 
% 
% 10c)
% H = [100];
% regularizerWeight = 0.8;
% 
% [net, valErr] = p10a (X_train, Y_train, H, regularizerWeight);
% valErr
% % valErr = 0.0680
% 
% [err, CONF] = p10b(X_test, Y_test, net);
% err, CONF
% 
% % err = 0.0915
% % 
% % CONF =
% %    172     0     0     0     0     1     2     0     0     0
% %      0   232     0     0     0     0     2     0     0     0
% %      0     1   199     2     2     0     6     4     4     1
% %      0     0     1   182     0    11     2     7     2     2
% %      0     1     1     0   195     0     5     1     0    14
% %      3     0     1     5     2   159     2     2     5     0
% %      3     1     2     0     3     2   165     1     1     0
% %      0     7    11     0     3     0     0   173     0    11
% %      1     2     2     6     5     3     3     4   161     5
% %      1     0     0     4     4     2     0     2     2   179
% 
% 
%
% % 10d)
HMaster = [50; 100; 150];
HList = [];
for i = 1:size(HMaster, 1)
   for j = 1:size(HMaster, 1)
       HList = [HList; HMaster(i) HMaster(j)];
   end
end

AList = transpose(0.1:0.1:0.9);

results = [];
% Test single-hidden-layered NNs
for ia = 1:size(AList, 1)
    for ih = 1:size(HMaster, 1)
        A = AList(ia);
        H = HMaster(ih, :);
        [net, valErr] = p10a (X_train, Y_train, H, A);
        [err, CONF] = p10b(X_test, Y_test, net);
        newResults = [A, 0, H, valErr, err]
        results = [results; newResults];
    end
end

% Test multi-hidden-layered NNs
for ia = 1:size(AList, 1)
    for ih = 1:size(HList, 1)
        A = AList(ia);
        H = HList(ih, :);
        [net, valErr] = p10a (X_train, Y_train, H, A);
        [err, CONF] = p10b(X_test, Y_test, net);
        newResults = [A, H, valErr, err]
        results = [results; newResults];
    end
end

[m, mi] = min(results);
results(mi(size(mi, 2) - 1), :)
% 
% Lowest Validation Error:
%   ans = 0.8000  150.0000  150.0000    0.0407    0.0730
% Lowest Test Error:
%   ans = 0.7000  100.0000   50.0000    0.0527    0.0715
% 
% [net, valErr] = p10a (X_train, Y_train,[100, 50], 0.7);
% valErr
% % valErr = 0.0467
% 
% [err, CONF] = p10b(X_test, Y_test, net);
% err, CONF
% 
% % err = 0.0715
% % CONF =
% %    173     0     0     0     0     0     2     0     0     0
% %      0   232     0     0     0     0     2     0     0     0
% %      2     1   205     0     0     0     3     2     3     3
% %      0     0     5   189     0     5     1     3     3     1
% %      0     0     1     0   204     0     2     1     2     7
% %      3     0     0     2     0   167     3     1     3     0
% %      3     1     1     0     5     3   164     0     1     0
% %      0     3    10     1     3     0     0   178     0    10
% %      2     1     4     6     4     2     1     2   167     3
% %      1     0     0     4     7     2     0     1     1   178
% 
%
% % 12, 13
% [net, valErr] = p10a (X_train_full, Y_train_full,[100, 50], 0.7);
% valErr
% % valErr = 0.0232
% 
% [err, CONF] = p10b(X_test_full, Y_test_full, net);
% err
% % err = 0.0228
