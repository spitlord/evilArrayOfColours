package photoed;

public class FilterDual implements Runnable {



	 byte[] ar;
	 int num;




	FilterDual(byte[] ar, int num) {
		this.ar = ar;
		Thread t = new Thread(this, "FilterDual");
		t.start();
	}

	public static byte[] ranFil (byte[] a, int num) {
		double x;
		for (int ii = num; ii < a.length-1; ii+=4) {
			x = Math.random();
			if (x > 0.5) a[ii] = (byte) (-127+256*Math.random());
			else a[ii] = (byte) (127 - 256*Math.random());
		}
		return a;
	}




	@Override
	public void run() {
		ranFil(this.ar, num);

	}

}
