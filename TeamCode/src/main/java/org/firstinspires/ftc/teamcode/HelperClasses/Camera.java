package org.firstinspires.ftc.teamcode.HelperClasses;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.teamcode.RobotOpMode.TeamColor;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;

//most of this class is just setup that doesn't need to be changed
//the core.inRange line is what controls the important stuff
public class Camera extends OpenCvPipeline
{
    public static double score = 0;
    public static double height = 0;
    public static double width = 0;
    public TeamColor color;
    OpenCvWebcam webcam;

    // Coordinate position of the top left corner of the selected rectangle
    public static Point screenPosition = new Point(0,0);

    private final Mat
            rawImage,       // Raw image output from the camera
            workingMat,     // The image currently being worked on and being modified
            selectionMask,
            hierarchy;

    /**
     * Sets up all the variables to keep code clean
     */
    public Camera(@NonNull HardwareMap hardwareMap) {
        rawImage = new Mat();
        workingMat = new Mat();
        selectionMask = new Mat();
        hierarchy = new Mat();



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                //set this line to dimensions of webcam
                webcam.startStreaming(800, 600, OpenCvCameraRotation.UPRIGHT);
                //cheap logitechs are 320, 240
                //logitech brio is 1280, 720
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
        webcam.setPipeline(this);
    }

    public OpenCvWebcam getStream () {
        return webcam;
    }


    @Override
    public Mat processFrame(@NonNull Mat input) {
        // Copies the original input to other materials to be worked on so they aren't overriding each other
        input.copyTo(rawImage);
        input.copyTo(workingMat);
        input.copyTo(selectionMask);


        // Sets the best fitting rectangle to the one currently selected
        Rect bestRect = new Rect();

        // Numerical value for the "best fit" rectangle
        // MAX_VALUE to find the lesser difference
        double lowestScore = Double.MAX_VALUE;

        //converts the image from rgb to hsv
        Imgproc.cvtColor(rawImage,workingMat,Imgproc.COLOR_RGB2HSV);

        //controls the color range the camera is looking for in the hsv color space
        //the hue value is scaled by .5, the saturation and value are scaled by 2.55
        if(color == TeamColor.blue)
            Core.inRange(workingMat,new Scalar(112,180,150),new Scalar(125,255,255),workingMat);
        else if(color == TeamColor.red)
            Core.inRange(workingMat,new Scalar(0,150,100),new Scalar(255,255,255),workingMat);
        else throw new RuntimeException("Invalid / No Color");

        // Creates a list for all contoured objects the camera will find
        List<MatOfPoint> contoursList = new ArrayList<>();

        Imgproc.findContours(workingMat, contoursList, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(selectionMask, contoursList,-1, new Scalar(40,40,40),2);

        // Scores all the contours and selects the best of them
        for(MatOfPoint contour : contoursList){
            // Calculate the "score" of the selected contour
            double score = calculateScore(contour);

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(contour);

            // Draw the current found rectangle on the selections mask
            //     Drawn in blue
            Imgproc.rectangle(selectionMask, rect.tl(), rect.br(), new Scalar(0,0,255),2);

            // If the result is better then the previously tracked one,
            // and the top left coordinates are within the cropped area
            // set this rect as the new best
            if(score < lowestScore){
                lowestScore = score;
                bestRect = rect;
            }
        }

        // Draw the "best fit" rectangle on the selections mask and skystone only mask
        //     Drawn in red
        Imgproc.rectangle(selectionMask, bestRect.tl(), bestRect.br(), new Scalar(0,255,0),10);

        // Sets the position of the selected rectangle (relative to the screen resolution)
        screenPosition = new Point(bestRect.x, bestRect.y);

        score = bestRect.height * bestRect.width;
        height = bestRect.height;
        width = bestRect.width;

        return selectionMask;
    }


    private double calculateScore(Mat input) {
        // Validates input, returning the maximum value if invalid
        if(!(input instanceof MatOfPoint))
            return Double.MAX_VALUE;
        // Otherwise returns the calculated area of the contour
        return -Imgproc.contourArea(input);
    }
}
