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
        // Constructors shouldn't have much functionality
        
        //  I propose that we  only keep Pic (int width, int height, int buffer) 
        //  And we introduce initialization functions for different cases such as 
        // 1. initialize from image file  
        //         |*| public Pic initImage(File imageFile) |*| 
        // 2. initialize empty image with height and width
        //         |*| public Pic initEmpty(height, width) |*|

		// This is okay with me.
		// I'm still not sure why it's better though.

       
        

        
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
        
        //  word this is only needed in cases of construtors
        //  e.g:
        //         Dog (String name, int legs) {
        //                  this.name = name;
        //                  this.legs = legs;
        //         }
        
        //  Now you can just use name and legs without word this in methods
        
        
        
        // public void amputateLeg() { if (legs > 0) legs = legs - 1; } // pretty
        // public void amputateLeg() { if (this.legs > 0) this.legs = this.legs - 1; } // ugly

        // public void amputateLeg() { //pretty
	//	if (this.legs > 0){
	//		this.legs = this.legs - 1;
	//	}
	// }

        // public void amputateLeg() { // prettier
	//	if (this.legs > 0) this.legs -= 1;
	// }

		// you want to not use "this"; i understand.
		// I like it because
		//	you don't have to keep track of where openMouth is coming from
		//		(which would be more confusing in case of a name like "index".
		//	you don't have to worry about whether or not your class variable has the same name as another identifier.
		//	it reminds you that calling "openMouth" may modify "this"
		//		If you're looking for when "this" was modified,
		//		you can just search for "this".
        
        
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
		// Adding "this" makes the code look explicit and clear.
        
        
        
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
	//	That's a good idea; feel free to rename.
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

		// 1stly, i'm just indenting with one tab like this:
		//      return this.get(      /// pretty
		//		channel,
		//		this.index(x,y)
		//	);
		// I'm not trying to align anything.
		
		// I understand using this for simpler arguments,
		// but i think for more complex arguments, it can look better.

	
     
        
        
        // Also, lets agree to comment on all the functions that we create.
        // It will help a lot for both of us to understand what we mean;
		// ya, i'll comment the ones i did so far.
        
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
        
        
               
        // in this case, do people need both methods to be public?
        // since one of them is helper
        // We should discuss conventions for filters
        // as in how they traverse the byte array;
		// we could get rid of the 1D access methods
		// actually, i think that's most intuitive (and best)
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
        
        // shall we have methods for set 
	

	//AIDENT METHODS (PRIVATE)
        
        // returns index of the pixel in the byte array 
	private int index(int x, int y){
		return x + this.width * y;
	}
        
        
        // better double_ XD this is such a bad name
        // RGBdoubleValue
		// The function just takes an arbitrary double and converts it to a byte
		// so adding rgb to the name is not meaningful.
		// I still think double_ is a good name.

	// converts double to unsigned byte
	// normalized so 0. goes to 0x00 and 1. goes to 0xff
	private static byte byteOf(double double_){
		if(double_ > 1){
			return (byte) 0xff;
		}else if(double_ < 0){
			return (byte) 0;
		}else{
			return (byte)(255*double_); // [-128, 127]!!  read next comment / subtract 128 to convert 
		}
	}
        
    
       
        // I think you neglected that byte array has negative values [-128, 127]
        // I tested the function and it gives 0.5 for -128, whereas it should give 0.0;
      
        
        // this is what it should be
        // private static double doubleOf(byte byte_){
        //       return (0xff&(byte_ + 128))/255.0;
	// }
	
		// The bytes are interpreted as unsigned,
		// so the current function is good,
		// and -128 actually shOUld correspond to .5.
		// This makes sense, there'd be no need for a negative value of color.

		// A byte is literally just a sequence of 8 bits.
		// It doesn't have any numerical value per se.
		// There are two common ways of interpreting it: signed and unsigned.
		// the default way for Java to turn a byte into a number inteprets them as signed,
		// but the image library makes an array of bytes
		// with the understanding that the values will be interpreted as signed.

		// I tried replacing doubleOf with your suggestion,
		// but it was not correct (not linear).



        // converts unsigned byte value into double value d [0, 1]
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
