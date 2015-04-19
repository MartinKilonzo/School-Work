public class NotePad
{
  public static void main(String[] args)
  {
    /* This Method replaces each pixel in an image with a picture of the same colour as the picture
     * to make a picture mosaic (a picture made up of smaller pictures) */
    public void makeMosaic(int numPictures, int scale)
    {
      Picture canvas = new Picture(scale * this.getHeight(), scale * this.getWidth());
      
      //To choose the pictures that make up the new picture:
      Pixel pixelObj = null;
      Picture tile = null;
      int counter = 0;
      Picture[] tileArray = new Picture[numPictures];
      for (int i = 0; i < numPictures; i ++)
      {
        tileArray[i] = new Picture(FileChooser.pickAFile());    
      }
      
      //To recolour each picture to replace existing pixels with the re-coloured picture
      for (int x = 0; x < this.getWidth(); x++)
      {
        //for (int y = 0, i = 0; y < this.getHeight(); y++, i = i + (int)(10*(Math.random() )) )
        for (int y = 0, i = 0; y < this.getHeight(); y++, counter++)
        {
          pixelObj = this.getPixel(x,y);
          //tileArray[i].colourAll(pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
          //To Complete: Adding the recoloured pictures to a canvas at proper locations
          tile = tileArray[i].getPictureWithHeight(scale);
          tile.colourAll(pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
          canvas.copyTo(tile, tile.getWidth(), tile.getHeight(), x * tile.getWidth(), y * tile.getHeight() );
          
          /*if (tileArray[i].getHeight() > tileArray[i].getWidth())
           {
           tile = tileArray[i].getPictureWithHeight(scale);
           tile.colourAll(pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
           canvas.copyTo(tile, tile.getWidth(), tile.getHeight(), x * tile.getWidth(), y * tile.getHeight() );
           }
           if (tileArray[i].getHeight() < tileArray[i].getWidth())
           {
           tile = tileArray[i].getPictureWithHeight(scale);
           tile.colourAll(pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
           canvas.copyTo(tile, tile.getWidth(), tile.getHeight(), x * tile.getWidth(), y * tile.getHeight() );
           } */
          
          while (counter  > 20)
          {
            canvas.explore();
            counter = 0;
          }
        }
      }
      
      canvas.explore();
    }
  }
}

 //This method recolours an entire picture a specific colour
 public void colourAll(int red, int green, int blue)
 {
   Pixel pixelObj = null;
   int factor = 0;
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       /*intensity = (int)((0.299 * pixelObj.getRed() + 0.587 * pixelObj.getGreen() + 0.114 * pixelObj.getBlue()) / 3);
       pixelObj.setRed(intensity);
       pixelObj.setGreen(intensity);
       pixelObj.setBlue(intensity); */
       factor = red + green + blue + 1;
       pixelObj.setRed((int)((red * red) / factor));
       pixelObj.setGreen(green);
       pixelObj.setBlue(blue);
     }
   }
 }

