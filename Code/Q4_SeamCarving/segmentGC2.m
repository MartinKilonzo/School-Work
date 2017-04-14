

function [segm,e2]  = segmentGC(im,scribbleMask,lambda,numClusters,inftyCost)
  segm = [];
  imgG = mean(im(:,:,1:3), 3);
  i = 1:numel(imgG);
  i = reshape(i, size(imgG));

  % Data Energies
  maxIntensity = max(max(imgG));
  minIntensity = min(min(imgG));

  dforeGround = maxIntensity - imgG;
  dbackGround = imgG - minIntensity;

  s = dforeGround >= dbackGround;
  d = dforeGround .* s + dbackGround .* ~s;

  wHoriz = (imgG(:, 1:end-1) - imgG(:, 2:end)) .^ 2;
  wHoriz = reshape(wHoriz, [], 1);

  j = i(:, 2:end-1)';
  j = reshape([j(:) j(:)]', 2 * size(j, 1), [])';
  I = [i(:, 1) j i(:, end)];
  W = reshape(I, [], 2);

  W = [W wHoriz];

  wVert = (imgG(1:end-1, :) - imgG(2:end, :)) .^ 2;
  wVert = reshape(wVert', [], 1);

  j = i(2:end-1, :);
  j = reshape([j(:) j(:)]', 2 * size(j, 1), [])';
  I = [i(1, :)' j i(end, :)'];

  W = [W; reshape(I, [], 2) wVert];

  W = [W; W(:, 2) W(:, 1) W(:, 3)]

  [labels, eng_start, eng_finish] = solveMinCut(reshape(dbackGround, 1, []), reshape(dforeGround, 1, []), W)

  % Smooth Energies
  % t =  [imgG(:, 1:end-1)==imgG(:, 2:end) transpose(imgG(1:end-1, :)==imgG(2:end, :))]
  sNeigh = [s(:, 1:end-1)~=s(:, 2:end) transpose(s(1:end-1, :)~=s(2:end, :))];
  neigh = [(imgG(:, 1:end-1) - imgG(:, 2:end)) .^ 2 transpose((imgG(1:end-1, :) - imgG(2:end, :)) .^ 2)];
  sigma2 = sum(sum(neigh)) / numel(neigh);
  neigh = neigh .* sNeigh;
  wpq = lambda * exp(-(neigh / sigma2));
  sum(sum(wpq));
  e2 = sum(sum(d)) + sum(sum(wpq));
end
