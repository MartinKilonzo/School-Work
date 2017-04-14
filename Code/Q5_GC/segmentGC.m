%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Performs foreground/background segmentation based on a graph cut
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% INPUT: 
%        im: input image  in double format 
%        scribbleMask: 
%               scribbleMask(i,j) = 2 means pixel(i,j) is a foreground seed
%               scribbleMask(i,j) = 1 means pixel(i,j) is a background seed
%               scribbleMask(i,j) = 0 means pixel(i,j) is not a seed
%        lambda: parameter for graph cuts
%        numClusters: parameter for kmeans
%        inftCost: parameter for infinity cost constraints
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% OUTPUT:   segm is the segmentation mask of the  same size as input image im
%           segm(i,j) = 1 means pixel (i,j) is the foreground
%           segm(i,j) = 0 means pixel (i,j) is the background
%
%           eng_finish: the energy of the computed segmentation
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

function [segm,eng_finish]  = segmentGC(im,scribbleMask,lambda,numClusters,inftyCost)

[row,col,d] = size(im);
segm = ones(row,col);  % return the whole image as the foreground 
