package photoed;


import photoed.filters.Filter;
import photoed.filters.pixelwise.Pixelwise;
import photoed.filters.pixelwise.Linear;

import java.util.stream.IntStream;

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
		String inputfilename = "/Users/XDXD/Downloads/St-Petersburg-Metro-Map.jpg";
                
                

		new Linear(
			new double[][]{
				{	0.7	,1	,1	},
				{	1	,.5	,0.7	},
				{	0	,0	,1	}
			}, new double[][]{
				{	1	,1	,1	},
				{	-1	,-1	,-1	},
				{	-.5	,-.4	,1	}
			}
		) .filter(new Pic(inputfilename)) .write("output.png"); 

		printRuntime(start_time);
	}
	

	private static String makeUpName(int howManyCharacters) {
		String suffix = "_";
		for (int ii = 0; ii < howManyCharacters; ii++) {
			suffix += String.valueOf(((int) (Math.random()*10)));
		}
		return suffix;
		}



	// prints how long it took the program to complete

	private static void printRuntime(double start_time) {
		System.out.println( "The program completed in " + ((System.nanoTime() - start_time)/1000000000.0) + " seconds.");
	}







}
