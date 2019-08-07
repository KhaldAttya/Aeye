import java.io.IOException;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import utils.Compare;
import utils.Screenshot;

public final class Aeye {
	
	
	public static void takeElementScreenshot(AppiumDriver<?> mobiledriver,By locator,String filePath) throws IOException {
		Screenshot.takeElementScreenshot(mobiledriver, locator, filePath);
	}
	
	public static void takeElementFromZeplin(AppiumDriver<?> mobiledriver,int x, int y,int width,int height,String zeplinScreenPath,String elementScreenPath) throws IOException {
		Screenshot.takeElementFromZeplin(mobiledriver, x, y, width, height, zeplinScreenPath,elementScreenPath);
	}
	
	public static void takeAppScreenshot(AppiumDriver<?> mobiledriver,By statusBar,String filePath) throws IOException {
		Screenshot.takeAppScreenshot(mobiledriver, statusBar, filePath);
	}
	
	public static boolean compareImages(String actual, String expected,String result) throws IOException {
		
		return Compare.compareImages(actual, expected, result);
       
}
	

}
