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
% i = find(CONF == max(CONF(:)));
% 'The most commonly confused digits were:'
% max(0, floor(i / 10))
% max(0, mod(i, 10) - 1)
% 
% % iter 100: err/loss = 4.3811
% % Most commonly confused digits were 7 and 4


p9(X_train, Y_train, iterNum, wInit, alpha)