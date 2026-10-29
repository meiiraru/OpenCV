package opencv;

import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Core;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

public class HelloColorConverterGrayscale {

    public static void main(String... args) {
        //show OpenCV version
        System.out.println("OpenCV Version " + Core.VERSION);

        //read image
        String imagePath = "./src/main/resources/baboon.png";
        Mat img = imread(imagePath);

        if (img.empty()) {
            System.out.println("Could not read the image: " + imagePath);
            return;
        }

        //cvt color to convert to grayscale
        Mat grayImg = new Mat();
        cvtColor(img, grayImg, COLOR_BGR2GRAY);

        //display images
        imshow("Original image", img);
        imshow("Grayscale image", grayImg);

        //export to file
        Path outputPath = Path.of("./exported/baboon_gray.png");
        try {
            Files.createDirectories(outputPath.getParent());
            if (!imwrite(outputPath.toString(), grayImg))
                throw new Exception("imwrite could not write the image to file");
            System.out.println("Grayscale image saved to: " + outputPath);
        } catch (Exception e) {
            System.out.println("Could not save the image: " + outputPath);
            e.printStackTrace();
        }

        //key press
        int k;
        while ((k = waitKey()) != 27 && k != -1) { //exit on escape key
            System.out.println("Key pressed: " + k);
        }
    }
}
