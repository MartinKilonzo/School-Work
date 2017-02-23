load('/toStudents/A1.mat');
load('/toStudents/A1_full.mat');
% % HMaster = transpose(50:10:250);
% HList = [];
% for i = 1:size(HMaster, 1)
%    for j = 1:size(HMaster, 1)
%        HList = [HList; HMaster(i) HMaster(j)];
%    end
% end
% 
% AList = transpose(0.6:0.01:0.8);
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
% results
% [m, mi] = min(results);
% results(mi(size(mi, 2) - 1), :)
% results(mi(size(mi, 2)), :)
A = 0.7;
H = [250, 10];
X = [X_train_full; X_test_full];
Y = [Y_train_full; Y_test_full];
[net, valErr] = p10a(X, Y, H, A);
[err, CONF] = p10b(X, Y, net);
valErr, err

%% P3
% kList = 1:1:10;
% results = [];
% for ik = 1 : size(kList, 2)
%     k = kList(ik);
%     C = p3(X_train, Y_train, X_test, k);
%     [err, CONF] = p2(C, Y_test);
%     newResults = [k, err]
%     results = [results; newResults];
% end
% C = p3(X_train, Y_train, X_test, 3);
% [err, CONF] = p2(C, Y_test);
% err

%% P9
% iterNum = 100;
% wInit = ones(max(Y_train), size(X_train, 2) + 1);
% 
% aList = 0.01:0.01:0.2;
% results = [];
% for ik = 1 : size(aList, 2)
%     alpha = aList(ik);
%     w = p9(X_train, Y_train, iterNum, wInit, alpha);
%     C = p7(w, X_train);
%     [trErr, CONF] = p2(C, Y_train);
%     C = p7(w, X_test);
%     [err, CONF] = p2(C, Y_test);
%     newResults = [alpha, trErr, err]
%     results = [results; newResults];
% end
% results
% [m, mi] = min(results);
% results(mi(size(mi, 2) - 1), :)
% results(mi(size(mi, 2)), :)
% 
% iterNum = 100;
% X = [X_train_full; X_test_full];
% Y = [Y_train_full; Y_test_full];
% wInit = randn(max(Y), size(X, 2) + 1);
% alpha = 0.01;
% 
% w = p9(X, Y, iterNum, wInit, alpha);
% C = p7(w, X);
% [trErr, CONF] = p2(C, Y);
% trErr
% % Training Error:
% % Iter 100: err = 0.0749
% C = p7(w, X);
% [err, CONF] = p2(C, Y);
% err, CONF
% % Test Error:
% % Iter 100: err = 0.0784

%% NN



