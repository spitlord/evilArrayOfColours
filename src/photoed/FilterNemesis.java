package photoed;


public class FilterNemesis implements Runnable {

	byte[] ar;

	FilterNemesis (byte[] ar) {
		this.ar = ar;
		Thread t = new Thread(this, "FilterNemesis");
		t.start();
	}


	public byte[] ranFil (byte[] a) {
		double x;
		for (int ii = 1; ii < a.length-1; ii+=2) {
			x = Math.random();
			if (x > 0.5) a[ii] = (byte) (-127+256*Math.random());
			else a[ii] = (byte) (127 - 256*Math.random());
		}
		return a;
	}

	@Override
	public void run() {
		ranFil(this.ar);
	}

}
