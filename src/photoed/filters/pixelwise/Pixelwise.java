package photoed.filters.pixelwise;

import photoed.Pic;
import photoed.filters.Filter;


public class Pixelwise implements Filter {
	public PixelMap map;

	public Pixelwise(PixelMap map){
		this.map = map;
	}

	public Pic filter(Pic pic){
		Pic newPic = new Pic(pic.width, pic.height);
		for(int i=0;i<pic.size();i++){
			newPic.setRGB(i,this.map.map(
				pic.get(0,i),
				pic.get(1,i),
				pic.get(2,i)
			));
		}
		return newPic;
	}
}
