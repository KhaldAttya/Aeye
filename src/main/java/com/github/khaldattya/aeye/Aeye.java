package com.github.khaldattya.aeye;
/**Main class to use Aeye library
 * @author Khaled Attia
 * @author https://github.com/KhaldAttya
 */

import java.io.IOException;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumDriver;
import com.github.khaldattya.aeye.utils.Compare;
import com.github.khaldattya.aeye.utils.Screenshot;

public final class Aeye {

	/**
	 *  Taking mobile element screenshot eg. Button or section of screen.
	 * @param driver Appium driver instance should be initiated in your test.
	 * @param locator By object of locator for the element needed to get screenshot of.
	 * @param filePath File path to save the screenshot to.
	 * @throws IOException
	 */
	public static void takeElementScreenshot(AppiumDriver<?> driver,By locator,String filePath) throws IOException {
		Screenshot.takeElementScreenshot(driver, locator, filePath);
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
	 * @throws IOException
	 */
	public static void takeElementFromDesign(AppiumDriver<?> driver,int x, int y,int width,int height,String designScreenPath,String elementScreenPath) throws IOException {
		Screenshot.takeElementFromDesign(driver, x, y, width, height, designScreenPath,elementScreenPath);
	}

	/** Taking screenshot of the whole app screen without status bar.
	 * @param driver Appium driver instance should be initiated in your test.
	 * @param statusBar By object of locator for status bar.
	 * @param filePath file path to save screenshot to.
	 * @throws IOException
	 */
	public static void takeAppScreenshot(AppiumDriver<?> driver,By statusBar,String filePath) throws IOException {
		Screenshot.takeAppScreenshot(driver, statusBar, filePath);
	}

	/** Image comparison function which highlights differences and save the results image.
	 * @param actual File path to the actual screen.
	 * @param expected File path to the expected screen.
	 * @param result File path to save the results to.
	 * @return Returns boolean representing if the actual and expected screens are matching or not.
	 * @throws IOException
	 */
	public static boolean compareImages(String actual, String expected,String result) throws IOException {
		
		return Compare.compareImages(actual, expected, result);
       
}
	

}
