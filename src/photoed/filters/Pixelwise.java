package photoed.filters;

import photoed.Pic;

import java.util.function.Function;


public class Pixelwise implements Function<Pic,Pic> {
	//defines a filter that acts on each pixel the same way,
	//without regard to context
	
	// The map defines how the filter responds to each pixel.
	private Function<double[],double[]> map;

	public Pixelwise(Function<double[],double[]> map){
		this.map = map;
	}

	//apply applies the filter's map to each pixel
	//and returns the result.
	public Pic apply(Pic pic){
		Pic newPic = new Pic(pic.width, pic.height);
		for(int i=0;i<pic.size();i++){
			newPic.setRGB(i,this.map.apply( new double[]{
				pic.get(0,i),
				pic.get(1,i),
				pic.get(2,i)}
			));
		}
		return newPic;
	}
}
