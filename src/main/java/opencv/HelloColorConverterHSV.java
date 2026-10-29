package opencv;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.opencv.core.Core;

import static org.bytedeco.opencv.global.opencv_core.merge;
import static org.bytedeco.opencv.global.opencv_core.split;
import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2HSV;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_HSV2BGR;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

public class HelloColorConverterHSV {

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
        Mat hsvImg = new Mat();
        cvtColor(img, hsvImg, COLOR_BGR2HSV);

        //extract hsv channels
        MatVector hsvChannels = new MatVector(3);
        split(hsvImg, hsvChannels);

        //multiply saturation channel by 1.5
        Mat saturationChannel = new Mat();
        hsvChannels.get(1).convertTo(saturationChannel, -1, 1.5, 0);

        //apply modified saturation channel back to hsv image
        MatVector mergeChannels = new MatVector(3);
        mergeChannels.put(0, hsvChannels.get(0));
        mergeChannels.put(1, saturationChannel);
        mergeChannels.put(2, hsvChannels.get(2));
        merge(mergeChannels, hsvImg);

        //darken
        Mat darkenedImg = hsvImg.clone();

        Mat valueChannel = new Mat();
        hsvChannels.get(2).convertTo(valueChannel, -1, 0.25, 0);
        mergeChannels.put(0, hsvChannels.get(0));
        mergeChannels.put(1, hsvChannels.get(1));
        mergeChannels.put(2, valueChannel);
        merge(mergeChannels, darkenedImg);

        //sepia
        Mat sepiaImg = hsvImg.clone();

        Mat hueChannel = new Mat();
        hsvChannels.get(0).convertTo(hueChannel, -1, 1, 20); //shift hue by 20 degrees
        mergeChannels.put(0, hueChannel);
        mergeChannels.put(1, hsvChannels.get(1));
        mergeChannels.put(2, hsvChannels.get(2));
        merge(mergeChannels, sepiaImg);

        //convert back to BGR
        cvtColor(hsvImg, hsvImg, COLOR_HSV2BGR);
        cvtColor(darkenedImg, darkenedImg, COLOR_HSV2BGR);
        cvtColor(sepiaImg, sepiaImg, COLOR_HSV2BGR);

        //display images
        imshow("Original image", img);
        imshow("Saturated image", hsvImg);
        imshow("Darkened image", darkenedImg);
        imshow("Sepia image", sepiaImg);

        //key press
        int k;
        while ((k = waitKey()) != 27 && k != -1) { //exit on escape key
            System.out.println("Key pressed: " + k);
        }
    }
}
