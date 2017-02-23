load('C:\Users\Martin\Dropbox\School\4th Year\CS 4442 - Artificial Intelligence II\Assignments\Assignment 1\toStudents\A1.mat')
load('C:\Users\Martin\Dropbox\School\4th Year\CS 4442 - Artificial Intelligence II\Assignments\Assignment 1\toStudents\A1_full.mat')
%% 1)
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% 
% p1(X, Y, 3, 4)
% 
% 
% % 2)
% C = [1; 2 ; 3; 3; 2; 1; 3; 2; 1];
% T = [1; 1 ; 3; 1; 2; 1; 3; 3; 1];
% 
% p2(C, T)
% 
% 
% 
%% 3a)
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% X_new = [4 5; 1 3];
% 
% p3(X, Y, X_new, 1)
% 
% 
%% 3b)
% kList = 1:2:7;
% for ik = 1 : size(kList, 2)
%     C = p3(X_train, Y_train, X_test, kList(ik));
%     [err, CONF] = p2(C, Y_test);
%     err
% end
% 
% C = p3(X_train, Y_train, X_test, 5);
% [err, CONF] = p2(C, Y_test);
% err, CONF
% 
% 
%% 4)
% w = [-2; 1; -3];
% X = [-1 -2; -1 -4; 3 2];
% 
% p4(w, X)
% 
% 
%% 5a)
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [1; 1; 2; 2; 2; 1; 1];
% 
% [w, e] = p5(X, Y, 100)
% 
%% 5b)
% [X_digit_train, Y_digit_train] = p1(X_train, Y_train, 5, 10);
% [X_digit_test, Y_digit_test] = p1(X_test, Y_test, 5, 10);
% 
% iterList = [100, 1000, 10000];
% 
% for iIterList = 1:size(iterList, 2)
%     iterNum = iterList(iIterList);
%     [w, trErr] = p5(X_digit_train, Y_digit_train, iterNum);
%     trErr
%     C = p4(w, X_digit_test);
%     [err, CONF] = p2(C, Y_digit_test);
%     err
% end
% 
% % Training Error
% % iter 100: err = 0.2922
% % iter 1000: err = 0.2078
% % iter 10000: err = 0.2311
% % 
% % Test Error
% % iter 100: err = 0.3698
% % iter 1000: err = 0.2287
% % iter 10000: err = 0.2190
% 
% 
% 
%% 6a)
% X = [2 3; 4 3; 3 5 ; 1 3; 5 6 ];
% Y = [1; 1 ; 1; 2;  2 ];
% iterNum = 3000;
% wInit = [ 1 ;1 ;1 ];
% alpha = 0.1;
% 
% [w, err] = p6(X, Y, iterNum, wInit, alpha)
% 
%% 6b)
% [X_digit_train, Y_digit_train] = p1(X_train, Y_train, 5, 10);
% [X_digit_test, Y_digit_test] = p1(X_test, Y_test, 5, 10);
% iterNum = 30;
% wInit = ones(size(Y_digit_train(1)));
% alpha = 0.1;
% 
% [w, trErr] = p6(X_digit_train, Y_digit_train, iterNum, wInit, alpha);
% trErr
% C = p4(w, X_digit_test);
% [err, CONF] = p2(C, Y_digit_test);
% err
% 
% % Training Error
% % iter 30: err = 0.0291
% 
% % Test Error
% % iter 30: err = 0.0608
% 
% 
% 
%% 7)
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; -9 8];
% W = [ 2 3 4; -1 -3 4; 5 6 -7];
% 
% p7(W, X)
% 
% 
% 
%% 8a)
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% iterNum = 1;
% alpha = 0.1;
% wInit = [ 1 2 -1; 1 2 -1;1 1 -2; -1 2 1];
% 
% p8(X, Y, iterNum, wInit, alpha)
% 
% 
%% 8b)
% iterNum = 100;
% wInit = randn(max(Y_train), size(X_train, 2) + 1);
% alpha = 0.01;
% 
% w = p8(X_train, Y_train, iterNum, wInit, alpha);
% C = p7(w, X_train);
% [trErr, CONF] = p2(C, Y_train);
% trErr
% C = p7(w, X_test);
% [err, CONF] = p2(C, Y_test);
% err, CONF
% 
% Training Error
% iter 100: err = 0.0654
% 
% Test Error
% iter 100: err = 0.1790
% 
% 
% 
%% 9a)
% X = [1 2; -1 4; 3 2; 1 7; 3 5; 4 6; 9 8];
% Y = [4; 1 ; 3; 2; 3; 3; 1];
% iterNum = 1;
% alpha = 0.1;
% wInit = [ 1 2 -1; 1 2 -1;1 1 -2; -1 2 1];
% 
% p9(X, Y, iterNum, wInit, alpha)
% 
% 
%% 9b)
% iterNum = 100;
% wInit = randn(max(Y_train), size(X_train, 2) + 1);
% alpha = 0.01;
% 
% w = p9(X_train, Y_train, iterNum, wInit, alpha);
% C = p7(w, X_train);
% [trErr, CONF] = p2(C, Y_train);
% trErr
% C = p7(w, X_test);
% [err, CONF] = p2(C, Y_test);
% err, CONF
% 
% % Training Error
% % iter 100: err = 0.0470
% % 
% % Test Error
% % iter 100: err = 0.1245
% 
% 
% 
%% 10a, b)
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
%% 10c)
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
%% 10d)
% HMaster = [50; 100; 150];
% HList = [];
% for i = 1:size(HMaster, 1)
%    for j = 1:size(HMaster, 1)
%        HList = [HList; HMaster(i) HMaster(j)];
%    end
% end
% 
% AList = transpose(0.1:0.1:0.9);
% 
% results = [];
% % Test single-hidden-layered NNs
% for ia = 1:size(AList, 1)
%     for ih = 1:size(HMaster, 1)
%         A = AList(ia);
%         H = HMaster(ih, :);
%         [net, valErr] = p10a (X_train, Y_train, H, A);
%         [err, CONF] = p10b(X_test, Y_test, net);
%         newResults = [A, 0, H, valErr, err]
%         results = [results; newResults];
%     end
% end
% 
% % Test multi-hidden-layered NNs
% for ia = 1:size(AList, 1)
%     for ih = 1:size(HList, 1)
%         A = AList(ia);
%         H = HList(ih, :);
%         [net, valErr] = p10a (X_train, Y_train, H, A);
%         [err, CONF] = p10b(X_test, Y_test, net);
%         newResults = [A, H, valErr, err]
%         results = [results; newResults];
%     end
% end
% 
% [m, mi] = min(results);
% results(mi(size(mi, 2) - 1), :)
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
%% 12, 13
% 
% iterNum = 1000;
% wInit = randn(max(Y_train), size(X_train, 2) + 1);
% 
% aList = 0.01:0.01:0.2;
% 
% results = [];
% 
% for ia = 1:size(aList, 2)
%     alpha = aList(ia);
%     w = p8(X_train, Y_train, iterNum, wInit, alpha);
%     C = p7(w, X_train);
%     [trErr, CONF] = p2(C, Y_train);
%     C = p7(w, X_test);
%     [err, CONF] = p2(C, Y_test);
%     newResults = [alpha trErr err]
%     results = [results; newResults];
% end
% results
% [m, mi] = min(results);
% results(mi(size(mi, 2) - 1), :)
% results(mi(size(mi, 2)), :)
% 
% X = [X_train_full; X_test_full];
% Y = [Y_train_full; Y_test_full];
% [net, valErr] = p10a (X, Y,[100, 50], 0.7);
% valErr
% % valErr = 0.0242
% 
% [err, CONF] = p10b(X, Y, net);
% err
% % err = 0.0167
% 
% p12(X_test)
