/* 
 * Remmy Martin Kilonzo
 * 250750759
 * The methods included in this file are to be
 * used for the caption assignments and include
 * manipulations for pictures used for the assignments
 */

import java.util.*;
import java.io.*;
import java.util.StringTokenizer;

public class PhotoList
{
  ///* Attributes *///
  private PhotoInfo[] photoArray;
  private int numPhoto = 0;
  private String fileName;
  
  ///* Constructors *///
  public PhotoList(String fileName)
  {
    //Declaring Variables
    String filePath = SimpleInput.getString("Enter the path to the \"myPhotos\" folder");
    FileChooser.setMediaPath(filePath);
    SimpleReader photoListReader = new SimpleReader(fileName);
    String[] lineArray = photoListReader.readFile();
    photoArray = new PhotoInfo[photoListReader.getFileLength()];
    StringTokenizer tokenizer;
    String identifier, category, caption, file;
    int day, month, year;
    
    for (numPhoto = 0; numPhoto < photoListReader.getFileLength(); numPhoto++)
    {
      tokenizer = new StringTokenizer(lineArray[numPhoto]);
      identifier = tokenizer.nextToken();
      
      day = Integer.parseInt(tokenizer.nextToken());
      month = Integer.parseInt(tokenizer.nextToken());;
      year = Integer.parseInt(tokenizer.nextToken());;
      category = tokenizer.nextToken();
      caption = tokenizer.nextToken();
      file = filePath + "\\" + tokenizer.nextToken();
      
      photoArray[numPhoto] = new PhotoInfo(identifier, day, month, year, category, caption, file);
    }
  }
  
  ///* Methods *///
  /* To show all the info of the photoArray */
  public void listPhotos()
  {
    for (numPhoto = 0; numPhoto < photoArray.length; numPhoto++)
    {
      System.out.println(photoArray[numPhoto].toString());
    }
  }
  
  public void showPhotos()
  {
    for (numPhoto = 0; numPhoto < photoArray.length; numPhoto++)
    {
      Picture photoObj = new Picture(photoArray[numPhoto].getPath());
      photoObj.explore();
    }
  }
  
  /*To show all the photos wirh captions stored in the photoArray */
  public void showCaptionedPhotos()
  {
    for (numPhoto = 0; numPhoto < photoArray.length; numPhoto++)
    {
      Picture photoObj = new Picture(photoArray[numPhoto].caption("Arial", 14));
      photoObj.explore();
    }
  }
  /* To store all the photos in the photoArray in a specified directory */
  public void storePhotos(String directory)
  {
    char end = directory.charAt(directory.length() - 1);
    if (end != '/' || end != '\\')
    {
      directory = directory + '/';
      File directoryFile = new File(directory);
      if (!directoryFile.exists())
      {
        directoryFile.mkdirs();
      }
    }
    
    for (numPhoto = 0; numPhoto < photoArray.length; numPhoto++)
    {
      Picture photoObj = new Picture(photoArray[numPhoto].caption("Arial", 14));
      String fileName = photoArray[numPhoto].getIdentifier() + "_" + photoArray[numPhoto].getCategory();
      photoObj.write(directory + fileName.replaceAll(" ", "_") + ".jpg");
    }
  } 
  
  /* To organize pictures by date */
  public void sortPhotosByDate()
  {
    PhotoInfo holder; //To hold a PhotoInfo item to be rearranged
    int year1, year2, month1, month2, day1, day2;
    Boolean swapped = true;    
    
    while (swapped)
    {
      swapped = false; //If this is not overwritten, the array is in chronological order
      //Scan the array for items to be reorganized
      for (numPhoto = 1; numPhoto < photoArray.length; numPhoto++)
      {
        year1 = photoArray[numPhoto - 1].getYear();
        year2 = photoArray[numPhoto].getYear();
        month1 = photoArray[numPhoto - 1].getIntMonth();
        month2 = photoArray[numPhoto].getIntMonth();
        day1 = photoArray[numPhoto - 1].getDay();
        day2 = photoArray[numPhoto].getDay();
        
        //Check if the item needs to be reorganized by year
        if (year1 > year2)
        {
          holder = photoArray[numPhoto - 1];
          photoArray[numPhoto - 1] = photoArray[numPhoto];
          photoArray[numPhoto] = holder;
          swapped = true;
        }
        if (year1 == year2)
        {
          //Check if the item needs to be reorganized by month
          if (month1 > month2)
          {
            holder = photoArray[numPhoto - 1];
            photoArray[numPhoto - 1] = photoArray[numPhoto];
            photoArray[numPhoto] = holder;
            swapped = true;
          }
          if (month1 == month2)
          {
            //Check if the item needs to be reorganized by day
            if (day1 > day2)
            {
              holder = photoArray[numPhoto - 1];
              photoArray[numPhoto - 1] = photoArray[numPhoto];
              photoArray[numPhoto] = holder;
              swapped = true;
            }
          }
        }
      }
    }
  }
    
  public static void main(String[] args)
  {
    PhotoList test = new PhotoList(FileChooser.pickAFile());
    test.listPhotos();
    System.out.println();
    test.showPhotos();
    test.sortPhotosByDate();
  }
}