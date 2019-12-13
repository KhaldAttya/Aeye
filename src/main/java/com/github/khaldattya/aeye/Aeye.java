package com.github.KhaldAttya.Aeye;

import com.github.KhaldAttya.Aeye.utils.Compare;
import com.github.KhaldAttya.Aeye.utils.Screenshot;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import io.appium.java_client.AppiumDriver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;

/**
 * Main class to use Aeye library
 *
 * @author Khaled Attia
 * @author https://github.com/KhaldAttya
 */
public final class Aeye {
    static AppiumDriver<?> driver;
    static String baselineRepo;
    static String actualRepo;
    static String resultRepo;


    Aeye(AppiumDriver<?> driver,String baselineRepo){
        this.driver=driver;
        this.baselineRepo=baselineRepo;
    }
    /**
     * Taking mobile element screenshot eg. Button or section of screen.
     *
     * @param driver Appium driver instance should be initiated in your test.
     * @param locator By object of locator for the element needed to get screenshot of.
     * @param targetFilePath File path to save the screenshot to.
     * @throws IOException as it's handling file path of image.
     */
    public static void takeElementScreenshot(AppiumDriver<?> driver, By locator, String targetFilePath) throws IOException {
        Screenshot.takeElementScreenshot(driver, locator, targetFilePath);
    }

    /**
     * Cropping Element with dimensions in pt or dp from design screen.
     *
     * @param driver Appium driver instance should be initiated in your test.
     * @param x x coordinate of the element in pt or dp.
     * @param y y coordinate of the element in pt or dp.
     * @param width element width in pt or dp.
     * @param height element height in pt or dp.
     * @param designScreenPath path of screen image.
     * @param targetFilePath path to save the cropped element.
     * @throws IOException as it's handling file path of image.
     */
    public static void takeElementFromDesign(AppiumDriver<?> driver, int x, int y, int width, int height,
            String designScreenPath, String targetFilePath) throws IOException {
        Screenshot.takeElementFromDesign(driver, x, y, width, height, designScreenPath, targetFilePath);
    }

    /**
     * Taking screenshot of the whole app screen without status bar.
     *
     * @param driver Appium driver instance should be initiated in your test.
     * @param statusBar By object of locator for status bar.
     * @param targetFilePath file path to save screenshot to.
     * @throws IOException as it's handling file path of image.
     */
    public static void takeAppScreenshot(AppiumDriver<?> driver, By statusBar, String targetFilePath) throws IOException {
        Screenshot.takeAppScreenshot(driver, statusBar, targetFilePath);
    }

    /**
     * Image comparison function which highlights differences and save the results image.
     *
     * @param actual File path to the actual screen.
     * @param expected File path to the expected screen.
     * @param result File path to save the results to.
     * @return Returns boolean representing if the actual and expected screens are matching or not.
     */
    public static boolean compareImages(String actual, String expected, String result) {
        return Compare.compareImages(actual, expected, result);
    }

    public static boolean see(String baseline) throws IOException {
        File fullScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage originalImage = ImageIO.read(fullScreenshot);
        ImageComparisonResult comparisonResult = new ImageComparison(baselineRepo+File.separator+baseline, actualRepo+File.separator+baseline)
                .compareImages();

        ImageComparisonUtil.saveImage(new File(resultRepo+File.separator+baseline), comparisonResult.getResult());
        return comparisonResult.getImageComparisonState() == ImageComparisonState.MATCH;
    }
}
