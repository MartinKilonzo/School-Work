function [err, CONF] = p10b(X_test, Y_test, net)
[m, predictions] = max(net(transpose(X_test)));
[err, CONF] = p2(transpose(predictions), Y_test);