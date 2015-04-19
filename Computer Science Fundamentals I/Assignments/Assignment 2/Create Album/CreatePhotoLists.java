/* 
 * Remmy Martin Kilonzo
 * 250750759
 * The methods included in this file are to be
 * used for the caption assignments and include
 * manipulations for pictures used for the assignments
 */
public class CreatePhotoLists
{
  public static void main(String[] args)
  {
     PhotoList album = new PhotoList(FileChooser.pickAFile());
     String saveDir = SimpleInput.getString("Where would you like the files to be saved?");
     album.sortPhotosByDate();
     album.listPhotos();
     album.showCaptionedPhotos();
     album.storePhotos(saveDir);     
  }
}