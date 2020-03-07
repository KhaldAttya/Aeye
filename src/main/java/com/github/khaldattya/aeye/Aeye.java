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
import java.util.logging.Logger;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.coordinates.JqueryCoordsProvider;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

/**
 * Main class to use Aeye library
 *
 * @author Khaled Attia
 * @author https://github.com/KhaldAttya
 */
public class Aeye {

    static WebDriver driver;

    static String baselineRepo;
    static String actualRepo;
    static String resultRepo;

    static int threshold=5;
    static int rectangleLineWidth=1;
    static double pixelToleranceLevel = 0.1D;
    static double differenceRectangleFilling = 30.0;
    static boolean differenceRectangleFillingEnabled = false;
    static double excludedRectangleFilling = 30.0;
    static boolean excludedRectangleFillingEnabled = false;

    private ImageComparison imageComparison;
    private static final Logger LOGGER = Logger.getLogger(Screenshot.class.getName());

    public Aeye(){

    }
    public  Aeye(String reposPath,WebDriver driver){
        this.driver=driver;
        setPaths(reposPath);
        LOGGER.info(() -> "Baseline repo is setup at: " + reposPath + File.separator + "baseline");
    }


    /**
     * Setting up screens path
     *
     * @param reposPath Path containing 'baseline' folder for screens.
     */
    private static void setPaths(String reposPath){
        Aeye.baselineRepo=reposPath+File.separator+"baseline";
        Aeye.actualRepo=reposPath+File.separator+"actual";
        Aeye.resultRepo=reposPath+File.separator+"result";
    }


    /**
     * Setting threshold for image-comparison.
     * @param threshold image-comparison threshold
     */
    public static void setThreshold(int threshold){
        Aeye.threshold=threshold;
    }

    /**
     * Setting Setting differences highlighter width
     * @param rectangleLineWidth highlighter rectangle width
     */
    public void setRectangleLineWidth(int rectangleLineWidth){
       Aeye.rectangleLineWidth=rectangleLineWidth;
    }

    /**
     * Setting pixel tolerance level for image-comparison
     * @param pixelToleranceLevel pixel tolerance level for image-comparison
     */
    public void setPixelToleranceLevel(double pixelToleranceLevel){
        Aeye.pixelToleranceLevel=pixelToleranceLevel;
    }
    public void setDifferenceRectangleFilling(boolean enabled,double pixelToleranceLevel){
        Aeye.differenceRectangleFillingEnabled=enabled;
        Aeye.differenceRectangleFilling=pixelToleranceLevel;
    }
    public void setExcludedRectangleFilling(boolean enabled,double pixelToleranceLevel){
        Aeye.excludedRectangleFillingEnabled = enabled;
        Aeye.excludedRectangleFilling=pixelToleranceLevel;
    }

    private  Rectangle getElementRectangle(By element) {

        Point elementLocation =  driver.findElement(element).getLocation();
        int elementWidth =  driver.findElement(element).getSize().width;
        int elementHeight =  driver.findElement(element).getSize().height;
        int elementMinX = (int) elementLocation.x;
        int elementMinY = (int) elementLocation.y;
        int elementMaxX = elementMinX + elementWidth;
        int elementMaxY = elementMinY + elementHeight;

        System.out.println(elementMinX+"-"+elementMinY+"-"+elementMaxX+"-"+elementMaxY);

        return new Rectangle(elementMinX,elementMinY,elementMaxX,elementMaxY);
    }

    /**
     * Excluding areas from comparison scope.
     * @param element By element to be excluded
     * @return Aeye object.
     */
    public Aeye exclude(By element) {
        List<Rectangle> rects= Arrays.asList(getElementRectangle(element));
        imageComparison.setExcludedAreas(rects);
        return this;
    }

    /**
     * Excluding areas from comparison scope.
     * @param elements By elements to be exclude
     * @return Aeye object.
     */
    public Aeye exclude(By ... elements) {
        List<Rectangle> rects=Arrays.asList();
        for (By element:elements) {
            rects= Arrays.asList(getElementRectangle(element));
        }
        imageComparison.setExcludedAreas(rects);
        return this;
    }
    /**
     * Excluding areas from comparison scope.
     * @param rectangle instance of {@link Rectangle}
     * @return Aeye object.
     */
    public Aeye exclude(Rectangle rectangle) {
        List<Rectangle> rects=Arrays.asList(rectangle);
        imageComparison.setExcludedAreas(rects);
        return this;
    }
    /**
     * Excluding areas from comparison scope.
     * @param rectangles instances of {@link Rectangle}
     * @return Aeye object.
     */
    public Aeye exclude(Rectangle ... rectangles) {
        List<Rectangle> rects=Arrays.asList();
        for (Rectangle rectangle:rectangles){
            rects= Arrays.asList(rectangle); 
        }
        imageComparison.setExcludedAreas(rects);
        return this;
    }

    /**
     * Image comparison function which highlights differences and save the results image
     * @throws IOException AssertionError
     *
     */
    public  void compare() throws IOException,AssertionError {
        ImageComparisonResult comparisonResult = imageComparison.compareImages();
        if (comparisonResult.getImageComparisonState() != ImageComparisonState.MATCH) throw new AssertionError("Mismatching screens!");
    }
    /**
     * Image comparison function which highlights differences and save the results image.
     * @return boolean with comparison result ( soft Assertions to be implented in project)
     */
    public  boolean compare(boolean compareSoftly ) throws IOException {
        ImageComparisonResult comparisonResult = imageComparison.compareImages();
       return comparisonResult.getImageComparisonState() != ImageComparisonState.MATCH;
    }

    /**
     * See if the provided screen with provided screen name from baseline exists on actual view.
     * @param screenName screen file name found in baseline folder (actual and result file are exported with same name.)
     * @return
     * @throws IOException
     */
    public Aeye see(String screenName) throws IOException {
        Screenshot.takeFullScreenshot(driver,actualRepo+File.separator+screenName+".png");
        createImageComparisonInstance(screenName);
        return this;
    }

    /**
     * See if the provided element with provided name from baseline exists on actual view.
     * @param screenName element screenshot file name found in baseline folder (actual and result file are exported with same name.)
     * @return
     * @throws IOException
     */
    public Aeye see(String screenName,By element) throws IOException {
        Screenshot.takeElementScreenshot(driver,element,actualRepo+File.separator+screenName + ".png");
        createImageComparisonInstance(screenName);
        return this;
    }

    private void createImageComparisonInstance(String screenName) throws IOException {
        BufferedImage actual = ImageIO.read(new File(actualRepo + File.separator + screenName + ".png"));
        BufferedImage baseline = null;
        try {
            baseline = ImageIO.read(new File(baselineRepo + File.separator + screenName + ".png"));
        }
        catch (IIOException e)
        {
            LOGGER.info(() -> "Cannot find baseline screen at: " + baselineRepo + File.separator + screenName + ".png");
            Throw IIOException;
        }
        File result = new File(resultRepo + File.separator + screenName + ".png");
        imageComparison = new ImageComparison(actual, baseline, result);
        imageComparison.setThreshold(threshold);
        imageComparison.setRectangleLineWidth(rectangleLineWidth);
        imageComparison.setPixelToleranceLevel(pixelToleranceLevel);
        imageComparison.setDifferenceRectangleFilling(differenceRectangleFillingEnabled,differenceRectangleFilling);
        imageComparison.setExcludedRectangleFilling(excludedRectangleFillingEnabled,excludedRectangleFilling);
    }

}
