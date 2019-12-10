package com.github.KhaldAttya.Aeye.utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import java.io.File;
import java.util.logging.Logger;

/**
 * Class of image comparison.
 */
public class Compare {

    private final static Logger LOGGER = Logger.getLogger(Compare.class.getName());

    /**
     * Image comparison function which highlights differences and save the results image.
     *
     * @param actual File path to the actual screen.
     * @param expected File path to the expected screen.
     * @param result File path to save the results to.
     * @return Returns boolean representing if the actual and expected screens are matching or not.
     */
    public static boolean compareImages(String actual, String expected, String result) {
        ImageComparisonResult comparisonResult = new ImageComparison(expected, actual)
                .setThreshold(10)
                .setRectangleLineWidth(2)
                .compareImages();
        LOGGER.info(() -> "Result is: " + comparisonResult.getImageComparisonState());

        ImageComparisonUtil.saveImage(new File(result), comparisonResult.getResult());
        return comparisonResult.getImageComparisonState() == ImageComparisonState.MATCH;
    }
}
