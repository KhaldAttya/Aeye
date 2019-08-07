package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Resize {
	
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		Image imgtmp = img;
		
		
		BufferedImage newImage = toBufferedImage( imgtmp.getScaledInstance(newW, newH, Image.SCALE_SMOOTH));
		return newImage;
		}
	
	private static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    
	    return bimage;
	}

}
