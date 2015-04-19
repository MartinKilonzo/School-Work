/**
 * Main class used to segment images into Regions
 * organized in Quad Trees 
 * homogenious regions
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 4
 */
package Main;
import Dependencies.*;

public class Main2 {

public static void main(String[] args) {

QueueADT<Region> regionQueue;

if(!(args.length==9))
{
System.out.println("Incorrect number of arguments:" + args.length + ", 9 needed");
System.out.println("java Main <input image> <Red Threshold> <Green Threshold> <Blue Threshold> <maxLevl> <tree traversal method>");
System.out.println("1 - inorder 1 traversal");
System.out.println("2 - inorder 2 traversal");
System.out.println("3 - inorder 3 traversal");
System.out.println("4 - preorder traversal");
System.out.println("5 - postorder traversal");
System.out.println("Example: java Main lena.jpg  60.0 60.0 60.0 20.0 20.0 20.0 6 4");
System.out.println("Example: java Main mando.jpg  60.0 60.0 60.0 20.0 20.0 20.0 6 4");
System.exit(0);
}

double scaleFactor=1.0;
double mRedT=Double.parseDouble(args[1]);
double mGreenT=Double.parseDouble(args[2]);
double mBlueT=Double.parseDouble(args[3]);
double sRedT=Double.parseDouble(args[4]);
double sGreenT=Double.parseDouble(args[5]);
double sBlueT=Double.parseDouble(args[6]);
int maxLevel=Integer.parseInt(args[7]);
int traversalOrder=Integer.parseInt(args[8]);

System.out.println("Color Thresholds:");
System.out.format("mRedT=%9.3f mGreenT=%9.3f mBlueT=%9.3f\n",mRedT,mGreenT,mBlueT);
System.out.format("sRedT=%9.3f sGreenT=%9.3f sBlueT=%9.3f\n",sRedT,sGreenT,sBlueT);
String mRedStg=Double.toString(mRedT);
String mGreenStg=Double.toString(mGreenT);
String mBlueStg=Double.toString(mBlueT);
String sRedStg=Double.toString(sRedT);
String sGreenStg=Double.toString(sGreenT);
String sBlueStg=Double.toString(sBlueT);
System.out.println("mRedStg=" + mRedStg + " mGreenStg=" + mGreenStg + 
                   " mBlueStg=" + mBlueStg);
System.out.println("sRedStg=" + sRedStg + " sGreenStg=" + sGreenStg + 
                   " sBlueStg=" + sBlueStg);

// Display the original image
MyPicture2 pic0 = new MyPicture2(args[0]);
pic0.setOriginUpperLeft();
pic0.show();

// Read the image that we will segment using the quadtree
System.out.println("File:" + args[0]);
MyPicture2 pic = new MyPicture2(args[0]);
// (0,0) is in the upperleft part of the window
pic.setOriginUpperLeft();
String segmented_filename="segmented-level-" + args[7] + 
   "-" + mRedStg + "-" + mGreenStg + "-" + mBlueStg + 
   "-" + sRedStg + "-" + sGreenStg + "-" + sBlueStg + "-" + args[0];
String painted_filename="painted-level-" + args[7] + 
   "-" + mRedStg + "-" + mGreenStg + "-" + mBlueStg + 
   "-" + sRedStg + "-" + sGreenStg + "-" + sBlueStg + "-" + args[0];
String sidewaysMerge1_filename="sideways1-merging-" + args[7] + 
   "-" + mRedStg + "-" + mGreenStg + "-" + mBlueStg +
   "-" + sRedStg + "-" + sGreenStg + "-" + sBlueStg + 
   "-traversal-order-" + args[8] + "-" + args[0];

int sideLength=pic.width();
System.out.println("sideLength=" + sideLength);
int n=log2(sideLength);
System.out.println("Maximum number of levels n=" + n);
int level=0; // root
System.out.println("maxLevel=" + maxLevel);
System.out.println("traversalOrder=" + traversalOrder);

double[] parameters = new double[6];
parameters=pic.simpleStatistics(0,0,sideLength);
pic.prettyPrintStatistics(0,0,sideLength,parameters);

// The entire image is the root node of the quadtree
QuadNode root = new QuadNode(pic,0,0,sideLength,level,parameters);
System.out.println("\nroot node generated for entire image\n");
System.out.println("root.getSideLength():" + root.getSideLength());
System.out.flush();

// Construct a full QuadTree2, specifying the pic MyPicture2 object, 
//the root QuadNode and the maximum number of levels in the tree, maxLevel
QuadTree2 tree = new QuadTree2(pic,root,maxLevel,
       mRedT,mGreenT,mBlueT,sRedT,sGreenT,sBlueT);
// Make an empty queue of QuadNode nodes
QueueADT<QuadNode> queue = new LinkedQueue<QuadNode>();
// Preorder traverse the tree (root, left subtree, next left subtree,
// third left subtree and fourth right subtree: if a node is a leaf
// node enqueue it onto the queue
queue=tree.preorder(root,queue);
System.out.println("\nQueue of leaf node generated via preorder()\n");

// Draw the segmentation represented by the leaf nodes in the queue
tree.drawSegmentation(pic,queue);
System.out.format("\nSegmentation printed on top of original image and saved to %s\n",
                     segmented_filename);

pic.save(segmented_filename);

// We just paint over pic2 - this is another instance of MyPicture2 
// with the same jpg file
MyPicture2 pic2 = new MyPicture2(args[0]);
pic2.setOriginLowerLeft();
// We paint the leaf node segments in the queue onto this image
tree.paintSquares(pic2,queue);
System.out.format("\nPainted image computed and saved to %s\n",painted_filename);
pic2.save(painted_filename);

// Do sidesways merging 
System.out.println("Starting Sideways Merging");
MyPicture2 pic3 = new MyPicture2(args[0]);
pic3.setOriginLowerLeft();

System.out.println("Starting treeTraversal");
regionQueue=tree.treeTraversal2(root,traversalOrder);
System.out.println("\n\nStarting sidewaysMerge1");

//regionQueue=tree.sidewaysMerge1(regionQueue,mRedT,mGreenT,mBlueT,
//                                            sRedT,sGreenT,sBlueT,false);
regionQueue=tree.sidewaysMerge2(regionQueue,sRedT,sGreenT,sBlueT,false);

System.out.println("Finished sidewaysMerge1"); 
System.out.println("\nStarting to draw each region");
for(int i=0;i<regionQueue.size();i++)
  {
  //System.out.println("Drawing region:" + i);
  Region region=regionQueue.dequeue();
  regionQueue.enqueue(region);
  region.drawRegion(pic3,false);
  }
System.out.println("sideways_filename=" + sidewaysMerge1_filename);
pic3.show();
pic3.save(sidewaysMerge1_filename);

} // main

static int log2(int n)
{
return (int) (Math.log(n) / Math.log(2));
}
}

