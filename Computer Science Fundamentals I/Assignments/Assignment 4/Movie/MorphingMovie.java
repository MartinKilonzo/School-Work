public class MorphingMovie
{
  public static void main(String[] args)
  {
    //Declaring variables
    Picture pictureObj = new Picture(1,1);
    String file = FileChooser.pickAFile();
    FileChooser.setMediaPath(file);
    Picture startPicture = new Picture(file);
    Picture endPicture   = new Picture(FileChooser.pickAFile());
    
    int numStages = SimpleInput.getIntNumber("How many intermediate pictures would you like?");
    Picture[] frames = new Picture[numStages + 2];
        
    String fileVideo = SimpleInput.getString("Where would you like the files to be saved?") + "/Frames";
    FrameSequencer fadeTo = new FrameSequencer(fileVideo);
    FrameSequencer fadeToAndFrom = new FrameSequencer(fileVideo + "/ToAndFrom");
    
    //Playing Forward
    for (int k = 0; k < frames.length; k++)
    {
      frames[k] = pictureObj.produceMorphStagePicture(startPicture, endPicture, numStages, k);
      fadeTo.addFrame(frames[k]);
      fadeToAndFrom.addFrame(frames[k]);
    }
    fadeTo.play(10);
    
    //Playing forward and backward
    for (int i = frames.length - 2; i >= 0; i = i - 1)
    {
      fadeToAndFrom.addFrame(frames[i]);
    }
    fadeToAndFrom.play(10);
  }
}