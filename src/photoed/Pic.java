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
        // It is bad practice to have multiple constructors (look up telescoping constructors)
        // Instead, I propose that we introduce initialization functions for different cases
        

        
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
                                // word "this" in here is redundant

				this.buffer,
				this.width,
				this.height
			),
			format,
			file
		);
	}
        
        // word this is only needed in cases of construtors
        // e.g:
        //  Dog (String name, int legs) {
        //         this.name = name;
        //         this.legs = legs;
        //  }
        
        //  Now you can just use name and legs without word this in methods
        
        
        
        // public void amputateLeg() { if (legs > 0) legs = legs - 1; } // pretty
        // public void amputateLeg() { if (this.legs > 0) this.legs = this.legs - 1; } // ugly
        
        
        //
        //   private void  openMouth() { ... }
        //   private void  makeSound() { ... }
       
        //   public void bark() { 
        //         this.openMouth();    // redundant
        //         this.makeSound();    // redundant
        //   }
        //
        
        //   public void bark() { 
        //        openMouth();          // still refering to this
        //        makeSound();          // 
        //   }
        
       
        //  Adding "this" makes the code look cumbersome and complicated.
        
        
        
       
	public void write(String filename) throws IOException {
            
           
            
		this.write(
			new File(filename),
			filename.split("\\.(?=[^\\.]+$)")[1]
		);
	}

	//PER-CHANNEL GET/SET
        // I feel like words "get" and "set" should not be resereved
        // We should rename these functions for more clarity.
        // Everything should have an explicit name.
        // Put as much meaning in every name.
        // Also for methods with few arguments. It's kinda confusing to write
        // each argument in a new line.
        // I think it  should only be done if there are too many arguments.
        // Every line is for a its own idea. One idea shouldn't occupy more space
        // than it needs.
        //                    return this.get(
	//		                          channel,
	//		                          this.index(x,y)   /// ugly
	//	                             )
        //
        //      return this.get(channel, this.index(x,y));          /// pretty
     
        
        
        // Also, lets agree to comment on all the functions that we create.
        // It will help a lot for both of us to understand what we mean;
        
	public double get(int channel, int index){
		return doubleOf(this.buffer[channel+index*3]);
	}
	public double get(int channel, int x, int y){
		return this.get(
			channel,
			this.index(x,y)
		);
	}
	public void set(int channel, int index, double value){
		this.buffer[ channel + index*3 ] = byteOf(value);
	}
	public void set(int channel, int x, int y, double value){
		this.set(
			channel,
			this.index(x,y),
			value
		);
	}

	// RGB GET/SET
        // 

        
	public double[] getRGB(int index){
		return new double[]{
			get(0,index),
			get(1,index),
			get(2,index)
		};
	}
	public double[] getRGB(int x, int y){
		return this.getRGB(this.index(x,y));
	}
	public void setRGB(int index, double[] color){
		for(int i=0; i<3; i++){
			set( i, index, color[i] );
		}
	}
	public void setRGB(int x, int y, double[] color){
		this.setRGB(
			this.index(x,y),
			color
		);
	}
	

	//AIDENT METHODS (PRIVATE)
	private int index(int x, int y){
		return x + this.width * y;
	}
        
        
        // double_ XD this is such a bad name
	private static byte byteOf(double double_){
		if(double_ > 1){
			return (byte) 0xff;
		}else if(double_ < 0){
			return (byte) 0;
		}else{
			return (byte)(255*double_);
		}
	}
	private static double doubleOf(byte byte_){
		return (double)(0xff&(int)byte_)/255;
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
