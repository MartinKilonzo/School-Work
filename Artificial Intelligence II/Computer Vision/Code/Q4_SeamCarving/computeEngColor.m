
function eng = computeEngColor(im,W)
  eng = W(1) .* im(:,:,1) + W(2) .* im(:,:,2) + W(3) .* im(:,:,3);
end
