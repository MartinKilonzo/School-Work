function outIm = applyFilter(im,F)
  % filter = flip(flip(F, 1), 2);
  filter = F
  outIm = [];
  [u, v] = size(filter);
  u = floor(u / 2);
  v = floor(v / 2);
  image = padarray(im, [u, v], 0);
  for iRow = 1 + u:size(image, 1) - u
      row = [];
    for iCol = 1 + v:size(image, 2) - v
      grid = image(iRow - u:iRow + u, iCol - v:iCol + v);
      pixel = sum(sum(grid .* filter));
      row = [row pixel];
    end
    outIm = [outIm; row];
  end
  %outIm = transpose(reshape(outIm, size(im)));
end
