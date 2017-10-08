package photoed;

import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import java.awt.image.Raster;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.DataBuffer;





public class Pic {
	//Pic class contains images and allows it's manipulation

	public byte[] buffer;
	public int width;
	public int height;
	public int size(){ return width*height; }

	//CONSTRUCTORS
	public Pic(String filename) throws IOException {
		this(ImageIO.read(new File(filename)));
	}
	public Pic(BufferedImage bufferedImage){
		this(
			bufferedImage.getWidth(), 
			bufferedImage.getHeight(),
			( (DataBufferByte)
				bufferedImage
				.getRaster()
				.getDataBuffer()
			).getData()
		);
	}
	public Pic(int width, int height){
		this(
			width,
			height,
			new byte[width*height*3]
		);
	}
	public Pic(int width, int height, byte[] buffer){
		this.width  = width;
		this.height = height;
		this.buffer = buffer;
	}
	
	//WRITEOUT
	public void write(File file, String format) throws IOException {
		ImageIO.write(
			createRGBImage(
				this.buffer,
				this.width,
				this.height
			),
			format,
			file
		);
	}
	public void write(String filename) throws IOException {
		this.write(
			new File(filename),
			filename.split("\\.(?=[^\\.]+$)")[1]
		);
	}

	//PER-CHANNEL GET/SET
	public byte get(int channel, int index){
		return this.buffer[channel+index*3];
	}
	public byte get(int channel, int x, int y){
		return this.get(
			channel,
			this.index(x,y)
		);
	}
	public void set(int channel, int index, byte value){
		this.buffer[ channel + index*3 ] = value;
	}
	public void set(int channel, int x, int y, byte value){
		this.set(
			channel,
			this.index(x,y),
			value
		);
	}

	// RGB GET/SET
	public byte[] getRGB(int index){
		return (Arrays.copyOfRange(
			this.buffer,
			index*3,
			index*3 + 3
		));
	}
	public byte[] getRGB(int x, int y){
		return this.getRGB(this.index(x,y));
	}
	public void setRGB(int index, byte[] color){
		System.arraycopy(
			color,
			0,
			this.buffer,
			index*3,
			3
		);
	}
	public void setRGB(int x, int y, byte[] color){
		this.setRGB(
			this.index(x,y),
			color
		);
	}
	

	//AIDENT METHODS (PRIVATE)
	private int index(int x, int y){
		return x + this.width * y;
	}
	private static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
		return new BufferedImage(
			new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[]{8, 8, 8},
				false,
				false,
				Transparency.OPAQUE,
				DataBuffer.TYPE_BYTE
			),
			Raster.createInterleavedRaster(
				new DataBufferByte(bytes, bytes.length),
				width,
				height,
				width * 3,
				3,
				new int[]{2, 1, 0},
				null
			),
			false,
			null
		);
	}
}