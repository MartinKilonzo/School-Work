
function eng = computeREng(im,F,W,maskWeight)
  eng = computeEngGrad(im(:,:,1:3), F) + computeEngColor(im(:,:,1:3), W) + maskWeight * im(:,:,4);
  eng = -eng;
end
