% reduces height of the image by one pixel using seam carving

function [seam,im,c] = increaseHeight(im,E)
  E = transpose(E);
  im = cat(3, transpose(im(:, :, 1)), transpose(im(:, :, 2)), transpose(im(:, :, 3)), transpose(im(:, :, 4)));
  [M, P] = seamV_DP(E);
  [seam, c] = bestSeamV(M, P);
  im = addSeamV(im, seam);
  im = cat(3, transpose(im(:, :, 1)), transpose(im(:, :, 2)), transpose(im(:, :, 3)), transpose(im(:, :, 4)));
end
