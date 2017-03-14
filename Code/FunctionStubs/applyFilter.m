function outIm = applyFilter(im,F)
  % filter = flip(flip(F, 1), 2);
  filter = F;
  outIm = [];
  k = floor(size(filter) / 2);
  image = padarray(im, k, 0);
  for iRow = 1 + k:size(image, 1) - k
      row = [];
    for iCol = 1 + k:size(image, 2) - k
      grid = image(iRow - k:iRow + k, iCol - k:iCol + k);
      pixel = sum(sum(grid .* filter));
      row = [row pixel];
    end
    outIm = [outIm; row];
  end
  %outIm = transpose(reshape(outIm, size(im)));
end
