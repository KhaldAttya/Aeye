package com.github.khaldattya.aeye.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.AppiumDriver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public  class Screenshot {

	public static void takeElementScreenshot(AppiumDriver<?> driver,By locator,String path) throws IOException {
		
		File fullScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		


		BufferedImage originalImage = ImageIO.read(fullScreenshot);

		if(driver.manage().window().getSize().getWidth()
				!=originalImage.getWidth() | driver.manage().window().getSize().getHeight()
						!=originalImage.getHeight()) {
			originalImage = Resize.resize(originalImage,driver.manage().window().getSize().getWidth(),
					driver.manage().window().getSize().getHeight());
			
		}
		
			int x=driver.findElement(locator).getRect().getX();
			int y=driver.findElement(locator).getRect().getY();
			int w=driver.findElement(locator).getRect().getWidth();
			int h=driver.findElement(locator).getRect().getHeight();
			
			if(y+h>originalImage.getHeight()) {
		           originalImage = Resize.resize(originalImage,originalImage.getWidth(),y+h);
		       }
		       if(x+w>originalImage.getWidth()) {
		           originalImage = Resize.resize(originalImage,x+w,originalImage.getHeight());
		       }
	

		BufferedImage SubImage = originalImage.getSubimage(x, y, w, h);

		System.out.println("Element dimension: "+SubImage.getWidth()+"x"+SubImage.getHeight());

		ImageIO.write(SubImage, "png", new File (path));
		

		System.out.println("Element screenshot was taken successfully: "+path);
		
		
	}
	
	
public static void takeAppScreenshot(AppiumDriver<?> mobiledriver,By statusBar,String path) throws IOException {
		
		File fullScreenshot = ((TakesScreenshot)mobiledriver).getScreenshotAs(OutputType.FILE);
		


		BufferedImage originalImage = ImageIO.read(fullScreenshot);

		if(mobiledriver.manage().window().getSize().getWidth()
				!=originalImage.getWidth() | mobiledriver.manage().window().getSize().getHeight()
						!=originalImage.getHeight()) {
			originalImage = Resize.resize(originalImage,mobiledriver.manage().window().getSize().getWidth(),
					mobiledriver.manage().window().getSize().getHeight());
			
		}
		
			
			int w=mobiledriver.findElement(statusBar).getRect().getWidth();
			int h=mobiledriver.findElement(statusBar).getRect().getHeight(); 
			
		
			
		BufferedImage SubImage = originalImage.getSubimage(0, h, w, (originalImage.getHeight()-h));

		System.out.println("Screen dimension: "+SubImage.getWidth()+"x"+SubImage.getHeight());

		ImageIO.write(SubImage, "png", new File (path));
		

		System.out.println("Element screenshot was taken successfully: "+path);
		
	}
	
	

public static void takeElementFromDesign(AppiumDriver<?> mobiledriver,int x, int y,int width,int height,String zeplinScreenPath,String elementScreenPath) throws IOException {
	

	BufferedImage originalImage = ImageIO.read(new File(zeplinScreenPath));
	
	
	BufferedImage SubImage = originalImage.getSubimage(x*2, y*2, width*2, height*2);

		/*
		 * if(mobiledriver.manage().window().getSize().getWidth()
		 * !=originalImage.getWidth() |
		 * mobiledriver.manage().window().getSize().getHeight()
		 * !=originalImage.getHeight()) { originalImage =
		 * Resize.resize(originalImage,mobiledriver.manage().window().getSize().getWidth
		 * (), mobiledriver.manage().window().getSize().getHeight());
		 * 
		 * }
		 */
	

	System.out.println("Element dimension: "+SubImage.getWidth()+"x"+SubImage.getHeight());

	ImageIO.write(SubImage, "png", new File (elementScreenPath));
	

	System.out.println("Element screenshot was taken successfully: "+elementScreenPath);
	
	
}

	
	
}
