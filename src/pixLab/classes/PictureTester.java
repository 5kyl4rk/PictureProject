package pixLab.classes;
/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */

import java.awt.Color;

public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("kirbo.png");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  public static void testZeroRed()
  {
	  Picture beach = new Picture("kirbo.png");
	  beach.explore();
	  beach.zeroRed();
	  beach.explore();
  }
  
  public static void testZeroGreen()
  {
	  Picture beach = new Picture("beach.jpg");
	  beach.explore();
	  beach.zeroGreen();
	  beach.explore();
  }
  
  public static void testGlitch()
  {
	  Picture image = new Picture("synthDuck.png");
	  image.explore();
	  image.glitch(false);
	  image.explore();
  }
  
  public static void testShiftLeftRight()
  {
	  Picture image = new Picture("eye.png");
	  image.explore();
	  image.shiftLeftRight(-120);
	  image.explore();
  }
  
  public static void testShiftUpDown()
  {
	  Picture image = new Picture("eye.png");
	  image.explore();
	  image.shiftUpDown(-50);
	  image.explore();
  }
  public static void testChromakey()
  {
	  Picture source = new Picture("kirbo.png");
	  Picture background = new Picture("megalomania.png");
	  
	  source.explore();
	  background.explore();
	  source.chromakey(background, Color.WHITE);
	  source.explore();
  }
  
  public static void testSteganography()
  {
	  Picture source = new Picture("kitten2.jpg");
	  Picture message = new Picture("shrugBlack.png");
	  
	  source.explore();
	  message.explore();
	  source.hidePicture(message);
	  source.explore();
	  source.revealPicture();
	  source.explore();
  }
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture image = new Picture("synthDuck.png");
    image.explore();
    image.mirrorVertical();
    image.explore();
  }
  
  public static void testMirrorHorizontal()
  {
	  Picture image = new Picture("snowman.jpg");
	  image.explore();
	  image.mirrorHorizontal();
	  image.explore();
  }
  
  /** Method to test reverseMirrorVertical */
  public static void testReverseMirrorVertical()
  {
	  Picture image = new Picture("synthDuck.png");
	  image.explore();
	  image.reverseMirrorVertical();
	  image.explore();
  }
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  public static void testMirrorGull()
  {
	  Picture gull = new Picture("seagull.jpg");
	  gull.explore();
	  gull.mirrorGull();
	  gull.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //testZeroBlue();
    //testZeroRed();
    //testZeroGreen();
    testGlitch();
    testSteganography();
    //testShiftLeftRight();
    //testShiftUpDown();
    //testChromakey();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorHorizontal();
    //testReverseMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}