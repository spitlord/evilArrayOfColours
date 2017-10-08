package photoed;


import photoed.filters.*;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.color.ColorSpace;

public class Main {

	public static void main (String[] args) throws IOException {
		double start_time = System.nanoTime();
		String inputfilename = args[0];

		(new Pixelwise(
			(byte[] rgb) -> (new byte[]{
				rgb[2],
				rgb[1],
				rgb[0]
				})
		))
			.filter(new Pic(inputfilename))
			.write("output.png"); 

		printRuntime(start_time);
	}













	// the path to the image on your machine
	private static String IMAGE_PATH = "/Users/XDXD/Downloads/IMG_2528-1.JPG";
	private static String OUTPUT_PATH = "/Users/XDXD/Desktop/genFil/";
	private static String OUTPUT_NAME = "evilArrayOfColours";

	// to avoid collisions when outputting new images in the same folder
	private static boolean  MAKE_UP_NAME = true;
	private static int      MAKE_UP_NAME_CONSTANT = 15;

	// byte arrays in which images are stored
	private static byte[] ORIGINAL_IMAGE;
	private static byte[] COPY_OF_ORIGINAL;
	private static byte[] FILTERED_IMAGE;

	// creates a copy during initialization
	// it is stored in COPY_OF_ORIGINAL
	private static boolean  CREATE_COPY = false;



	// dimentions of the original image
	// will be initialized automatically
	private static int IMAGE_WIDTH;
	private static int IMAGE_HEIGHT;

	// Runtime tools
	private static long START_TIME;



    /* INITILIZING IMAGE TOOLS  */

	// returns the image in the form of numbers
	// in the end, colours are just numbers

	public static byte[] extractBytes (BufferedImage img) throws IOException {

		 // get DataBufferBytes from Raster
		 WritableRaster raster = img.getRaster();
		 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

		 return (data.getData());
	}

	private static byte[] initializeImage(String path, boolean createCopy) {
		byte[] byteArray;
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(IMAGE_PATH));

			IMAGE_WIDTH  = bufferedImage.getWidth();
			IMAGE_HEIGHT = bufferedImage.getHeight();

			byteArray = extractBytes(bufferedImage);
			if (createCopy) {COPY_OF_ORIGINAL = createCopy(byteArray);}
			return byteArray;
		} catch (IOException e) {
			System.out.println("Could not initialize the image the bytes.");
			return null;
		}
	}


	// if only one image to be processed multiple times
	// then it makes sence to avoid opening it every loop
	// this can be done by creating a copy of byte array

	private static byte[] createCopy(byte[] image) {
		byte[] copy = Arrays.copyOf(image, image.length);
		return copy;
	}

	 /* OUTPUTTING IMAGE TOOLS  */

	// creates a string of numbers to add to the name
	// in order to avoid collisions

	private static String makeUpName(int howManyCharacters) {
		String suffix = "_";
		for (int ii = 0; ii < howManyCharacters; ii++) {
			suffix += String.valueOf(((int) (Math.random()*10)));
		}
		return suffix;
		}

	private static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
	    DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
	    ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width, height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
	}

	private static void outputImage(byte[] filteredImage, String path, String name, int number, boolean makeUpName) {
		OutputStream outputStream = null;
		BufferedImage resultingImage;
	    try {
	    	if (makeUpName) {
	    		outputStream = new BufferedOutputStream(
					new FileOutputStream(OUTPUT_PATH + OUTPUT_NAME + makeUpName(MAKE_UP_NAME_CONSTANT) + ".jpg")
					);
			} else {
				outputStream = new BufferedOutputStream(
					new FileOutputStream(OUTPUT_PATH + OUTPUT_NAME + ".jpg")
					);
			}
			resultingImage = createRGBImage(filteredImage, IMAGE_WIDTH, IMAGE_HEIGHT);
			ImageIO.write(resultingImage, "jpg", outputStream);

		} catch (Exception e) {
			System.out.println("Failed to output the image.");
		}

	}


	// prints how long it took the program to complete

	private static void printRuntime(double start_time) {
		System.out.println( "The program completed in " + ((System.nanoTime() - start_time)/1000000000.0) + " seconds.");
	}







}
