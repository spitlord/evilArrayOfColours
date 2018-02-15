package photoed;


import photoed.filters.Pixelwise;
import photoed.filters.Linear;

import java.io.IOException;

public class Main {
	//applies the filter to the first command line argument
	//and saves the resultant image as the second command line argument
	public static void main (String[] args) throws IOException {
		double start_time = System.nanoTime();
		String inputFilename = args[0];
		String outputFilename = args[1];

		new Linear(
			new double[][]{
				{	0.7	,1	,1	},
				{	1	,.5	,0.7	},
				{	0	,0	,1	}
			}, new double[][]{
				{	0,	0,	1	},
				{	0,	0,	1	},
				{	1,	1.5,	2	}
			}
		) .apply(new Pic(inputFilename)) .write(outputFilename); 

		printRuntime(start_time);
	}
	

	// returns a randam string of length howManyCharacters
	private static String makeUpName(int howManyCharacters) {
		String suffix = "_";
		for (int ii = 0; ii < howManyCharacters; ii++) {
			suffix += String.valueOf(((int) (Math.random()*10)));
		}
		return suffix;
		}



	// prints how long it took the program to complete
	private static void printRuntime(double start_time) {
		System.out.println(
			  "The program completed in "
			+ ((System.nanoTime() - start_time)/1000000000.0)
			+ " seconds."
		);
	}
}
