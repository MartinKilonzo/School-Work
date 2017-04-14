im = zeros(5,5,3);
im(:,:,1) =   [101   244   231   126   249;
                151   249   219     9    64;
                88    93    21   112   155;
                114    55    55   120   205;
                84   154    24   252    63];


im(:,:,2) =   [115   228   195    68   102;
                92    74   216    64   221;
                218   134   123    35   213;
                229    23   192   111   147;
                164   218    78   231   146];


im(:,:,3) =  [  91   201   137    85   182;
                225   102    91   122    60;
                85    46   139   162   241;
                101   252    31   100    69;
                158   198   196    26   239];

F = [ 1 3 0 -3 -1];
eng = computeEngGrad(im,F)

% eng =
% 
%    1.0e+03 *
% 
%     1.0482    0.6229    0.6974    0.3918    0.7198
%     0.6436    0.3150    0.3294    0.3519    0.4289
%     0.3674    0.1294    0.3535    0.3169    0.4061
%     0.4456    0.1650    0.1605    0.1390    0.5056
%     0.8820    0.4255    0.3753    0.4358    0.8715

s = sum(eng(:))

% s =
% 
%    1.1528e+04

