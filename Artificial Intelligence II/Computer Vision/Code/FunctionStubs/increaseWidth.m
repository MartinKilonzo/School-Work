% increases width of the image by n pixels using seam carving

function [seam,im,c] = increaseWidth(im,E)
  [M, P] = seamV_DP(E);
  [seam, c] = bestSeamV(M, P);
  im = addSeamV(im, seam);
end
