package photoed.filters;

import photoed.Pic;
import photoed.filters.Filter;


public class Pixelwise implements Filter {
	public PixelMapGenerator generator;

	public Pixelwise(PixelMap map){
		this.generator = (String[] args) -> map;
	}
	public Pixelwise(PixelMapGenerator generator){
		this.generator = generator;
	}

	public Pic filter(Pic pic){
		return this.filter(pic, new String[0] );
	}
	public Pic filter(Pic pic, String[] args){
		PixelMap map = this.generator.generate(args);
		Pic newPic = new Pic(pic.width, pic.height);
		for(int i=0;i<pic.size();i++){
			newPic.setRGB(i,map.map(pic.getRGB(i)));
		}
		return newPic;
	}
}
