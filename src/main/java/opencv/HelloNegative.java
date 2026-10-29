package opencv;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Core;

import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

public class HelloNegative {

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

        Mat negative = img.clone();

        //pixel processing
        int width = img.cols();
        int height = img.rows();
        System.out.println("Image width: " + width);
        System.out.println("Image height: " + height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //grab img1 pixel color
                BytePointer color = img.ptr(i, j);
                byte b = color.get(0);
                byte g = color.get(1);
                byte r = color.get(2);

                //compute the negative value
                BytePointer negativeColor = negative.ptr(i, j);
                negativeColor.put(0, (byte) (b & 0xFF ^ 0xFF));
                negativeColor.put(1, (byte) (g & 0xFF ^ 0xFF));
                negativeColor.put(2, (byte) (r & 0xFF ^ 0xFF));
            }
        }

        //display images
        imshow("Original image", img);
        imshow("Ngative image", negative);

        //key press
        int k;
        while ((k = waitKey()) != 27 && k != -1) { //exit on escape key
            System.out.println("Key pressed: " + k);
        }
    }
}
