import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.AppiumDriver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public  class Screenshot {

	public static void takeElementScreenshot(AppiumDriver<?> mobiledriver,By locator,String path) throws IOException {
		
		File fullScreenshot = ((TakesScreenshot)mobiledriver).getScreenshotAs(OutputType.FILE);
		


		BufferedImage originalImage = ImageIO.read(fullScreenshot);

		if(mobiledriver.manage().window().getSize().getWidth()
				!=originalImage.getWidth() | mobiledriver.manage().window().getSize().getHeight()
						!=originalImage.getHeight()) {
			originalImage = resize(originalImage,mobiledriver.manage().window().getSize().getWidth(),
					mobiledriver.manage().window().getSize().getHeight());
			
		}
		
			int x=mobiledriver.findElement(locator).getRect().getX();
			int y=mobiledriver.findElement(locator).getRect().getY();
			int w=mobiledriver.findElement(locator).getRect().getWidth();
			int h=mobiledriver.findElement(locator).getRect().getHeight(); 
			
		

		BufferedImage SubImage = originalImage.getSubimage(x, y, w, h);

		System.out.println("Element dimension: "+SubImage.getWidth()+"x"+SubImage.getHeight());

		ImageIO.write(SubImage, "png", new File (path));
		

		System.out.println("Element screenshot was taken successfully: "+path);
		
	}
	
	
	private static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
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
