import java.awt.image.BufferedImage;

public class Filter extends Thread {

	// 1: filter with BufferedImage.getRGB and BufferedImage.setRGB

	public static byte[] fil1 (byte[] a) {
		for (int ii = 0; ii < a.length-1; ii++) {
			a[ii] =  (a[ii+1]);
		}
		return a;
	}


	public static byte[] fil2 (byte[] a) {
		for (int ii = 0; ii < a.length-1; ii++) {
			a[ii] =  (a[a.length-1-ii]);
		}
		return a;
	}

	public static byte[] fil3 (byte[] a) {
			for (int ii = 0; ii < a.length-1; ii++) {
			 a[ii+1] = (byte) (a[ii]*a[ii]);
		}
		return a;
	}





}


