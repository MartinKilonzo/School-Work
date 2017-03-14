

im = double(imread('swan.png'));

F = [-1 0 1 ; -3 0 3; -1 0 1];

outIm = applyFilter(im, F);

s = sum(abs(outIm(:)))

imwrite(uint8(stretch(outIm)), './swanFiltered.png');
