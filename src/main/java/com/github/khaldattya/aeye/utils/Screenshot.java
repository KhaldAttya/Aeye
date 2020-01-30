package com.github.KhaldAttya.Aeye.utils;

import com.github.romankh3.image.comparison.ImageComparisonUtil;
import io.appium.java_client.AppiumDriver;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Screenshot class containing screenshot different functions.
 */
public class Screenshot {

    private static final Logger LOGGER = Logger.getLogger(Screenshot.class.getName());

    /**
     * Taking mobile element screenshot eg. Button or section of screen.
     *
     * @param driver Appium driver instance should be initiated in your test.
     * @param filePath File path to save the screenshot to.
     * @throws IOException as it's handling file path of image.
     */

    public static void takeFullScreenshot(AppiumDriver<?> driver, String filePath) throws IOException {

        File fullScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage originalImage = ImageIO.read(fullScreenshot);

        if (driver.manage().window().getSize().getWidth()
                != originalImage.getWidth() | driver.manage().window().getSize().getHeight()
                != originalImage.getHeight()) {
            originalImage = ImageComparisonUtil.resize(originalImage, driver.manage().window().getSize().getWidth(),
                    driver.manage().window().getSize().getHeight());

        }

        BufferedImage finalOriginalImage = originalImage;
        LOGGER.info(() -> "Element dimension: " + finalOriginalImage.getWidth() + "x" + finalOriginalImage.getHeight());

        ImageIO.write(originalImage, "png", new File(filePath));

        LOGGER.info(() -> "Element screenshot was taken successfully: " + filePath);
    }

    /**
     * Taking mobile element screenshot eg. Button or section of screen.
     *
     * @param driver Appium driver instance should be initiated in your test.
     * @param locator By object of locator for the element needed to get screenshot of.
     * @param filePath File path to save the screenshot to.
     * @throws IOException as it's handling file path of image.
     */
    public static void takeElementScreenshot(AppiumDriver<?> driver, By locator, String filePath) throws IOException {

        File fullScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage originalImage = ImageIO.read(fullScreenshot);

        if (driver.manage().window().getSize().getWidth()
                != originalImage.getWidth() | driver.manage().window().getSize().getHeight()
                != originalImage.getHeight()) {
            originalImage = ImageComparisonUtil.resize(originalImage, driver.manage().window().getSize().getWidth(),
                    driver.manage().window().getSize().getHeight());

        }

        int x = driver.findElement(locator).getRect().getX();
        int y = driver.findElement(locator).getRect().getY();
        int w = driver.findElement(locator).getRect().getWidth();
        int h = driver.findElement(locator).getRect().getHeight();

        if (y + h > originalImage.getHeight()) {
            originalImage = ImageComparisonUtil.resize(originalImage, originalImage.getWidth(), y + h);
        }
        if (x + w > originalImage.getWidth()) {
            originalImage = ImageComparisonUtil.resize(originalImage, x + w, originalImage.getHeight());
        }

        BufferedImage SubImage = originalImage.getSubimage(x, y, w, h);

        LOGGER.info(() -> "Element dimension: " + SubImage.getWidth() + "x" + SubImage.getHeight());

        ImageIO.write(SubImage, "png", new File(filePath));

        LOGGER.info(() -> "Element screenshot was taken successfully: " + filePath);
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
     * @param elementScreenPath path to save the cropped element.
     * @throws IOException as it's handling file path of image.
     */
    public static void takeElementFromDesign(AppiumDriver<?> driver, int x, int y, int width, int height,
            String designScreenPath, String elementScreenPath) throws IOException {

        BufferedImage originalImage = ImageIO.read(new File(designScreenPath));

        BufferedImage SubImage = originalImage.getSubimage(x * 2, y * 2, width * 2, height * 2);

        LOGGER.info(() -> "Element dimension: " + SubImage.getWidth() + "x" + SubImage.getHeight());

        ImageIO.write(SubImage, "png", new File(elementScreenPath));

        LOGGER.info(() -> "Element screenshot was taken successfully: " + elementScreenPath);
    }
}
