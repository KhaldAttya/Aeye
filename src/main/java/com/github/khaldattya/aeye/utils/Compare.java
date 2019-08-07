package com.github.KhaldAttya.Aeye.utils;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ComparisonResult;
import com.github.romankh3.image.comparison.model.ComparisonState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** class of image comparison.
 *
 */


public class Compare {

    /** Image comparison function which highlights differences and save the results image.
     * @param actual File path to the actual screen.
     * @param expected File path to the expected screen.
     * @param result File path to save the results to.
     * @return Returns boolean representing if the actual and expected screens are matching or not.
     * @throws IOException as it's handling file path of image.
     */

	public static boolean compareImages(String actual, String expected,String result) throws IOException {

				BufferedImage expectedImage = ImageIO.read(new File (expected));
				BufferedImage actualImage = ImageIO.read(new File (actual));

				
				File resultDestination = new File( result);
				ImageComparison imageComparison = new ImageComparison( expectedImage, actualImage, resultDestination );

				imageComparison.setThreshold(10);
				imageComparison.setRectangleLineWidth(2);
				imageComparison.setDestination(resultDestination);

				
				ComparisonResult comparisonResult = imageComparison.compareImages();
				BufferedImage resultImage = comparisonResult.getResult();
				System.out.println("Result is: "+comparisonResult.getComparisonState());
				
				
		        ImageIO.write(resultImage, "png", resultDestination);
		        return comparisonResult.getComparisonState()== ComparisonState.MATCH;
		       
		
	}
	
	
}
