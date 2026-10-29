package opencv;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Core;

import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

public class HelloChannels {

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

        Mat imgR = img.clone();
        Mat imgG = img.clone();
        Mat imgB = img.clone();

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

                //write to imgR
                BytePointer colorR = imgR.ptr(i, j);
                colorR.put(0, r);
                colorR.put(1, r);
                colorR.put(2, r);

                //write to imgG
                BytePointer colorG = imgG.ptr(i, j);
                colorG.put(0, g);
                colorG.put(1, g);
                colorG.put(2, g);

                //write to imgB
                BytePointer colorB = imgB.ptr(i, j);
                colorB.put(0, b);
                colorB.put(1, b);
                colorB.put(2, b);
            }
        }

        //display images
        imshow("Red", imgR);
        imshow("Green", imgG);
        imshow("Blue", imgB);

        //key press
        int k;
        while ((k = waitKey()) != 27 && k != -1) { //exit on escape key
            System.out.println("Key pressed: " + k);
        }
    }
}
