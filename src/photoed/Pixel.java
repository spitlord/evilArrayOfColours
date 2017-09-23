package photoed;


public class Pixel {







	public static int bitGetA(int bit) {
		bit = bit >> 24 & 0xff;
		return bit;
	}

	public static int bitGetR(int bit) {
		bit  = bit >> 16 & 0xff;
		return bit;
	}

	public static int bitGetG(int bit) {
		bit  = bit >> 8 & 0xff;
		return bit;
	}

	public static int bitGetB(int bit) {
		bit  = bit & 0xff;
		return bit;
	}






	public static int bitSetA(int bit, int alpha) {

		bit = bit & 0x00FFFFFF;
		alpha = alpha << 24;
		bit = bit | alpha;
		return bit;
	}

	public static int bitSetR(int bit, int red) {

		bit = bit & 0xFF00FFFF;
		red = red << 16;
		bit = bit | red;
		return bit;
	}

	public static int bitSetG(int bit, int green) {
		bit = bit & 0xFFFF00FF;
		green = green << 8;
		bit = bit | green;
		return bit;
	}

	public static int bitSetB(int bit, int blue) {
		bit = bit &  0xFFFFFF00;
		bit = bit | blue;
		return bit;
	}


}
