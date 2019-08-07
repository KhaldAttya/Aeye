import java.io.IOException;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import utils.Compare;
import utils.Screenshot;

public final class Aeye {
	
	
	public static void takeElementScreenshot(AppiumDriver<?> driver,By locator,String filePath) throws IOException {
		Screenshot.takeElementScreenshot(driver, locator, filePath);
	}
	
	public static void takeElementFromDesign(AppiumDriver<?> driver,int x, int y,int width,int height,String DesignScreenPath,String elementScreenPath) throws IOException {
		Screenshot.takeElementFromDesign(driver, x, y, width, height, DesignScreenPath,elementScreenPath);
	}
	
	public static void takeAppScreenshot(AppiumDriver<?> driver,By statusBar,String filePath) throws IOException {
		Screenshot.takeAppScreenshot(driver, statusBar, filePath);
	}
	
	public static boolean compareImages(String actual, String expected,String result) throws IOException {
		
		return Compare.compareImages(actual, expected, result);
       
}
	

}
