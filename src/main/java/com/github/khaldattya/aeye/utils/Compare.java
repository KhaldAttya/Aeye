package com.github.khaldattya.aeye.utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ComparisonResult;
import com.github.romankh3.image.comparison.model.ComparisonState;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class of image comparison.
 */
public class Compare {

    /**
     * Image comparison function which highlights differences and save the results image.
     *
     * @param actual File path to the actual screen.
     * @param expected File path to the expected screen.
     * @param result File path to save the results to.
     * @return Returns boolean representing if the actual and expected screens are matching or not.
     * @throws IOException as it's handling file path of image.
     */

    public static boolean compareImages(String actual, String expected, String result) throws IOException {
        File resultDestination = new File(result);
        ImageComparison imageComparison = new ImageComparison(expected, actual);

        imageComparison.setThreshold(10);
        imageComparison.setRectangleLineWidth(2);

        ComparisonResult comparisonResult = imageComparison.compareImages();
        BufferedImage resultImage = comparisonResult.getResult();
        System.out.println("Result is: " + comparisonResult.getComparisonState());

        ImageComparisonUtil.saveImage(resultDestination, resultImage);
        return comparisonResult.getComparisonState() == ComparisonState.MATCH;
    }


}
