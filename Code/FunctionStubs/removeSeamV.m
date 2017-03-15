function imOut = removeSeamV(im,seam)
  imOut = [];
  for iLayer=1:size(im, 3)
    layer = [];
    for iRow=1:size(im, 1)
      row = [];
      for iCol=1:size(im, 2)
        if isempty(find(seam(iRow, :) == iCol))
          row = [row im(iRow, iCol, iLayer)];
        end
      end
      layer = [layer; row];
    end
    imOut = cat(3, imOut, layer);
  end
end
