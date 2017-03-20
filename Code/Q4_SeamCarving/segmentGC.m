

function [segm,e2]  = segmentGC(im,scribbleMask,lambda,numClusters,inftyCost)
  imgG = mean(im(:,:,1:3), 3);
  i = 1:numel(imgG);
  i = reshape(i, size(imgG));

  hNeigh = (imgG(:, 1:end-1) - imgG(:, 2:end)) .^ 2;
  vNeigh = (imgG(1:end-1, :) - imgG(2:end, :)) .^ 2;
  sigma2 = (sum(sum(hNeigh)) + sum(sum(vNeigh))) / (numel(hNeigh) + numel(vNeigh))
  sigma2 = 2 * sigma2;


  % Foreground and Background Data
  if numClusters <= 0
    dataB = [scribbleMask == 1] * inftyCost;
    dataB = reshape(dataB, 1, []);
    dataF = [scribbleMask == 2] * inftyCost;
    dataF = reshape(dataF, 1, []);

    cost = (imgG(:, 1:end-1) - imgG(:, 2:end)) .^ 2;
    cost = lambda * exp(-(cost / sigma2));
    wHoriz = reshape(cost, [], 1);

    j = i(:, 2:end-1)';
    j = reshape([j(:) j(:)]', 2 * size(j, 1), [])';
    I = [i(:, 1) j i(:, end)];
    W = reshape(I, [], 2);

    W = [W wHoriz];

    cost = (imgG(1:end-1, :) - imgG(2:end, :)) .^ 2;
    cost = lambda * exp(-(cost / sigma2));
    wVert = reshape(cost, [], 1);

    j = i(2:end-1, :);
    j = reshape([j(:) j(:)]', 2 * size(j, 1), [])';
    I = [i(1, :)' j i(end, :)'];

    W = [W; reshape(I, [], 2) wVert];

    W = [W; W(:, 2) W(:, 1) W(:, 3)]

    [labels, eng_start, eng_finish] = solveMinCut(dataB, dataF, W);

    segm = reshape(~labels, size(imgG, 1), []);
    % e2 =
    % Data Energies
    maxIntensity = max(max(imgG));
    minIntensity = min(min(imgG));

    dforeGround = maxIntensity - imgG;
    dbackGround = imgG - minIntensity;

    shNeigh = [segm(:, 1:end-1)~=segm(:, 2:end)];
    svNeigh = [segm(1:end-1, :)~=segm(2:end, :)];
    vNeigh = lambda * exp(-(vNeigh / sigma2)) .* svNeigh;
    hNeigh = lambda * exp(-(hNeigh / sigma2)) .* shNeigh;
    e2 = sum(sum(vNeigh)) + sum(sum(hNeigh));

  else
    'Kmeans clustering is not implemented'
  end
end
