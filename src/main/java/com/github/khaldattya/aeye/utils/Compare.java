package com.github.KhaldAttya.Aeye.utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ComparisonResult;
import com.github.romankh3.image.comparison.model.ComparisonState;
import java.io.File;
import java.io.IOException;

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
        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setThreshold(10)
                .setRectangleLineWidth(2);

        ComparisonResult comparisonResult = imageComparison.compareImages();
        System.out.println("Result is: " + comparisonResult.getComparisonState());

        ImageComparisonUtil.saveImage(new File(result), comparisonResult.getResult());
        return comparisonResult.getComparisonState() == ComparisonState.MATCH;
    }
}
