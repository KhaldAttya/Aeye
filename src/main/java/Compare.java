import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

import comparison.ImageComparison;
import utils.ComparisonResult;



public final class Compare {

	public static void compareImages(String actual, String expected,String result) throws IOException {
		

				BufferedImage expectedImage = ImageIO.read(new File (expected));
				BufferedImage actualImage = ImageIO.read(new File (actual));

				// where to save the result (leave null if you want to see the result in the UI)
				File resultDestination = new File( result);
				ImageComparison imageComparison = new ImageComparison( expectedImage, actualImage, resultDestination );

				imageComparison.setThreshold(10);
				imageComparison.setRectangleLineWidth(5);
				imageComparison.setDestination(resultDestination);

				//After configuring the ImageComparison object, can be executed compare() method:
				ComparisonResult comparisonResult = imageComparison.compareImages();
				BufferedImage resultImage = comparisonResult.getResult();
				System.out.println("Result is: "+comparisonResult.getComparisonState());
				
				
				
				 File dir = resultDestination.getParentFile();
		        boolean dirExists = dir == null || dir.isDirectory() || dir.mkdirs();
		        if (!dirExists) {
		            throw new FileNotFoundException("Unable to create directory " + dir);
		        }
		        ImageIO.write(resultImage, "png", resultDestination);

		
	}
	
	
}
