package opencv;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Core;

import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

public class HelloGrayscale {

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

        Mat grayed = img.clone();

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

                //compute the grayscale value
                float redGray   = (r & 0xFF) * 0.2125f;
                float greenGray = (g & 0xFF) * 0.7154f;
                float blueGray  = (b & 0xFF) * 0.0721f;
                byte grayValue = (byte) (redGray + greenGray + blueGray);

                //write to the grayed image
                BytePointer colorGrayed = grayed.ptr(i, j);
                colorGrayed.put(0, grayValue);
                colorGrayed.put(1, grayValue);
                colorGrayed.put(2, grayValue);
            }
        }

        //display images
        imshow("Original image", img);
        imshow("Grayscale image", grayed);

        //key press
        int k;
        while ((k = waitKey()) != 27 && k != -1) { //exit on escape key
            System.out.println("Key pressed: " + k);
        }
    }
}
