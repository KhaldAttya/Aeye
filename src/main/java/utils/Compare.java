package utils;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ComparisonResult;
import com.github.romankh3.image.comparison.model.ComparisonState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public final class Compare {

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
