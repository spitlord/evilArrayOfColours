package photoed;

import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;

public class Image {
	//Image class contains images and allows it's manipulation

	public byte[] buffer;
	public int width;
	public int height;

	public Image(int width, int height, byte[] buffer){
		this.width  = width;
		this.height = height;
		this.buffer = buffer;
	}
	public Image(BufferedImage bufferedImage){
		this(
			bufferedImage.getWidth(), 
			bufferedImage.getHeight(),
			(
				(DataBufferByte)
				bufferedImage.getRaster().getDataBuffer()
			).getData()
		);
	}
	public Image(String filename) throws IOException {
		this(ImageIO.read(new File(filename)));
	}

	public byte getChannel(int channel, int index){
		return this.buffer[channel+index*3];
	}
	public byte getChannel(int channel, int x, int y){
		return this.getChannel(
			channel,
			this.index(x,y)
		);
	}

	public void setChannel(int channel, int index, byte value){
		this.buffer[ channel + index*3 ] = value;
	}
	public void setChannel(int channel, int x, int y, byte value){
		this.setChannel(
			channel,
			this.index(x,y),
			value
		);
	}

	public byte[] get(int index){
		int startIndex = index;
		return (Arrays.copyOfRange(
			this.buffer,
			startIndex,
			startIndex + 3
		));
	}
	public byte[] get(int x, int y){
		return this.get(this.index(x,y));
	}
	public void set(int index, byte[] color){
		System.arraycopy(
			color,
			0,
			this.buffer,
			index,
			3
		);
	}


	public void set(int x, int y, byte[] color){
		this.set(
			this.index(x,y),
			color
		);
	}
	

	private int index(int x, int y){
		return x + this.width * y;
	}

}
