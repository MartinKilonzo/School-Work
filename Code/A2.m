
%% P1
% a)
im = [101   244   231   126   249;
      151   249   219     9    64;
      88    93    21   112   155;
      114    55    55   120   205;
      84   154    24   252    63];

F = [-1 0 1 ; -3 0 3; -1 0 1];

outIm = applyFilter(im,F)

% outIm =
%
%          981         458        -594        -101        -387
%         1084         267        -819        -313        -265
%          583        -192        -118         397        -465
%          412        -304         312         623        -724
%          517        -239         359         267        -876

s = sum(abs(outIm(:)))

% s =
%
%        11657

% b)
im = double(imread('swan.png'));

F = [-1 0 1 ; -3 0 3; -1 0 1];

outIm = applyFilter(im, F);

s = sum(abs(outIm(:)))

% s =
%
%      2937527

imwrite(uint8(stretch(outIm)), './swanFiltered.png');



%% P2
