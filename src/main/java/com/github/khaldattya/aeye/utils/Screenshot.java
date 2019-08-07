package com.github.KhaldAttya.Aeye.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.AppiumDriver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Screenshot class containing screenshot different functions.
 *
 */
public  class Screenshot {

	/**
	 *  Taking mobile element screenshot eg. Button or section of screen.
	 * @param driver Appium driver instance should be initiated in your test.
	 * @param locator By object of locator for the element needed to get screenshot of.
	 * @param filePath File path to save the screenshot to.
	 * @throws IOException as it's handling file path of image.
	 */
	public static void takeElementScreenshot(AppiumDriver<?> driver,By locator,String filePath) throws IOException {
		
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

		ImageIO.write(SubImage, "png", new File (filePath));
		

		System.out.println("Element screenshot was taken successfully: "+filePath);
		
		
	}

	/** Taking screenshot of the whole app screen without status bar.
	 * @param driver Appium driver instance should be initiated in your test.
	 * @param statusBar By object of locator for status bar.
	 * @param filePath file path to save screenshot to.
	 * @throws IOException as it's handling file path of image.
	 */
public static void takeAppScreenshot(AppiumDriver<?> driver,By statusBar,String filePath) throws IOException {
		
		File fullScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		


		BufferedImage originalImage = ImageIO.read(fullScreenshot);

		if(driver.manage().window().getSize().getWidth()
				!=originalImage.getWidth() | driver.manage().window().getSize().getHeight()
						!=originalImage.getHeight()) {
			originalImage = Resize.resize(originalImage,driver.manage().window().getSize().getWidth(),
					driver.manage().window().getSize().getHeight());
			
		}
		
			
			int w=driver.findElement(statusBar).getRect().getWidth();
			int h=driver.findElement(statusBar).getRect().getHeight();
			
		
			
		BufferedImage SubImage = originalImage.getSubimage(0, h, w, (originalImage.getHeight()-h));

		System.out.println("Screen dimension: "+SubImage.getWidth()+"x"+SubImage.getHeight());

		ImageIO.write(SubImage, "png", new File (filePath));
		

		System.out.println("Element screenshot was taken successfully: "+filePath);
		
	}

	/**
	 * Cropping Element with dimensions in pt or dp from design screen.
	 * @param driver Appium driver instance should be initiated in your test.
	 * @param x x coordinate of the element in pt or dp.
	 * @param y y coordinate of the element in pt or dp.
	 * @param width element width in pt or dp.
	 * @param height element height in pt or dp.
	 * @param designScreenPath path of screen image.
	 * @param elementScreenPath path to save the cropped element.
	 * @throws IOException as it's handling file path of image.
	 */

public static void takeElementFromDesign(AppiumDriver<?> driver,int x, int y,int width,int height,String designScreenPath,String elementScreenPath) throws IOException {
	

	BufferedImage originalImage = ImageIO.read(new File(designScreenPath));
	
	
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
