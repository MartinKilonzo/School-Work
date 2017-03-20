function imOut = removeSeamV(im,seam)
  imOut = [];
  for iLayer=1:size(im, 3)
    layer = [];
    for iRow=1:size(im, 1)
      row = im(iRow, :, iLayer);
      row = [row(1:seam(iRow) - 1) row(seam(iRow) + 1:end)];
      layer = [layer; row];
    end
    imOut = cat(3, imOut, layer);
  end
end
