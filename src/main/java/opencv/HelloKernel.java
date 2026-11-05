package opencv;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.opencv.core.Core;

import static org.bytedeco.opencv.global.opencv_core.CV_32F;
import static org.bytedeco.opencv.global.opencv_core.divide;
import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.filter2D;

public class HelloKernel {

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

        //create a 3x3 kernel
        Mat kernel = new Mat(5, 5, CV_32F);
        kernel.put(new Scalar(1.0));
        kernel = divide(kernel, 25.0).asMat();

        //apply kernel to image
        filter2D(img, img, -1, kernel);

        //display images
        imshow("image", img);

        //key press
        int k;
        while ((k = waitKey()) != 27 && k != -1) { //exit on escape key
            System.out.println("Key pressed: " + k);
        }
    }
}
