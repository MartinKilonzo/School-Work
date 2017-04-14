function [totalCost,imOut] = intelligentRemove(imInput,v,h,W,mask,maskWeight)
  F = [-1, 0, 1];
  imOut = cat(3,imInput,mask);
  totalCost = 0;

  reducingHeight = h < 0;
  reducingWidth = v < 0;
  h = abs(h);
  v = abs(v);
  while h > 0 || v > 0
    %  Horizntontal actions take precendece over vertical
    if h > 0
      E = computeREng(imOut, F, W, maskWeight);
      if reducingHeight
        [seam, imOut, cost] = reduceHeight(imOut, E);
        totalCost = totalCost + cost;
      else
        [seam, imOut, cost] = increaseHeight(imOut, E);
        totalCost = totalCost + cost;
      end
      h = h - 1;
    end
    if v > 0
      E = computeREng(imOut, F, W, maskWeight);
      if reducingWidth
        [seam, imOut, cost] = reduceWidth(imOut, E);
        totalCost = totalCost + cost;
      else
        [seam, imOut, cost] = increaseWidth(imOut, E);
        totalCost = totalCost + cost;
      end
      v = v - 1;
    end
  end
  imOut = imOut(:,:,1:3);
end
