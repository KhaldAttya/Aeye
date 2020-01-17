package com.github.KhaldAttya.Aeye;

import com.github.KhaldAttya.Aeye.utils.Screenshot;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.github.romankh3.image.comparison.model.Rectangle;
import io.appium.java_client.AppiumDriver;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
    static int threshold=5;
    static int rectangleLineWidth=1;
    static double pixelToleranceLevel = 0.1D;



    private ImageComparison imageComparison;

    public static void setPaths(String reposPath){
        Aeye.baselineRepo=reposPath+File.separator+"baseline";
        Aeye.actualRepo=reposPath+File.separator+"actual";
        Aeye.resultRepo=reposPath+File.separator+"result";
    }
    public static void setDriver(AppiumDriver<?> driver){
        Aeye.driver=driver;
    }

    public static void setThreshold(int threshold){
        Aeye.threshold=threshold;
    }

    public void setRectangleLineWidth(int rectangleLineWidth){
       Aeye.rectangleLineWidth=rectangleLineWidth;
    }
    public void setPixelToleranceLevel(int pixelToleranceLevel){
        Aeye.pixelToleranceLevel=pixelToleranceLevel;
    }

     static Rectangle getElementRectangle(By element) {

        Point elementLocation =  driver.findElement(element).getLocation();
        int elementWidth =  driver.findElement(element).getSize().width;
        int elementHeight =  driver.findElement(element).getSize().height;

        int elementMinX = (int) elementLocation.x;
        int elementMinY = (int) elementLocation.y;
        int elementMaxX = elementMinX + elementWidth;
        int elementMaxY = elementMinY + elementHeight;

        return new Rectangle(elementMinX,elementMinY,elementMaxX,elementMaxY);
    }

    public Aeye exclude(By element) {
        List<Rectangle> rects= Arrays.asList(getElementRectangle(element));
        imageComparison.setExcludedAreas(rects);
        return this;
    }
    public Aeye exclude(By ... elements) {
        List<Rectangle> rects=Arrays.asList();
        for (By element:elements) {
            rects= Arrays.asList(getElementRectangle(element));
        }
        imageComparison.setExcludedAreas(rects);
        return this;
    }

    public Aeye exclude(Rectangle rectangle) {
        List<Rectangle> rects=Arrays.asList(rectangle);
        imageComparison.setExcludedAreas(rects);
        return this;
    }
    public Aeye exclude(Rectangle ... rectangles) {
        List<Rectangle> rects=Arrays.asList();
        for (Rectangle rectangle:rectangles){
            rects= Arrays.asList(rectangle); 
        }
        imageComparison.setExcludedAreas(rects);
        return this;
    }
    /**
     * Image comparison function which highlights differences and save the results image.
     *
     */
    public  void compare() throws IOException {
        ImageComparisonResult comparisonResult = imageComparison.compareImages();
        if (comparisonResult.getImageComparisonState() != ImageComparisonState.MATCH) throw new AssertionError();
    }

    public Aeye see(String screenName) throws IOException {
        Screenshot.takeFullScreenshot(driver,actualRepo+File.separator+screenName+".png");
        createImageComparisonInstance(screenName);
        return this;
    }

    public Aeye see(String screenName,By element) throws IOException {
        Screenshot.takeElementScreenshot(driver,element,actualRepo+File.separator+screenName);
        createImageComparisonInstance(screenName);
        return this;
    }

    void createImageComparisonInstance(String screenName) throws IOException {
        BufferedImage actual = ImageIO.read(new File(actualRepo + File.separator + screenName + ".png"));
        BufferedImage baseline = ImageIO.read(new File(baselineRepo + File.separator + screenName + ".png"));
        File result = new File(resultRepo + File.separator + screenName + ".png");
        imageComparison = new ImageComparison(actual, baseline, result);
        imageComparison.setThreshold(threshold);
        imageComparison.setRectangleLineWidth(rectangleLineWidth);
        imageComparison.setPixelToleranceLevel(pixelToleranceLevel);
    }

}
