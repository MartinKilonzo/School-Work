
function eng = computeEngGrad(im,F)
  imG = sum(im, 3) / 3;
  eng = sqrt(applyFilter(imG, F) .^ 2 + applyFilter(imG, transpose(F)) .^ 2);
end
