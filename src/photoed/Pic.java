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
				new byte[width*height]
			);
		}
		public Pic(int width, int height, byte[] buffer){
			this.width  = width;
			this.height = height;
			this.buffer = buffer;
		}
	
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
