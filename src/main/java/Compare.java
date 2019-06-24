import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ComparisonResult;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class for creating compare images.
 */
public final class Compare {

    public static void compareImages(String actual, String expected, String result) throws IOException {

        BufferedImage expectedImage = ImageIO.read(new File(expected));
        BufferedImage actualImage = ImageIO.read(new File(actual));

        // where to save the result (leave null if you want to see the result in the UI)
        File resultDestination = new File(result);
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, resultDestination);

        imageComparison.setThreshold(10);
        imageComparison.setRectangleLineWidth(5);
        imageComparison.setDestination(resultDestination);

        //After configuring the ImageComparison object, can be executed compare() method:
        //Destination was added, that's why result image would be added.
        ComparisonResult comparisonResult = imageComparison.compareImages();
        System.out.println("Result is: " + comparisonResult.getComparisonState());
    }


}
