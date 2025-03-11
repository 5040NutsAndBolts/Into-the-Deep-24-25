package org.firstinspires.ftc.teamcode.CameraClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.HelperClasses.Camera;
import org.firstinspires.ftc.teamcode.HelperClasses.TestOpModes.FTCConstants;
import org.firstinspires.ftc.teamcode.RobotOpMode;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import com.acmerobotics.dashboard.FtcDashboard;

@TeleOp(name = "Vision Test", group = "Teleop")
public class VisionTest extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        //camera settup
        Camera webcam = new Camera(hardwareMap);
        webcam.color = FTCConstants.TeamColor.blue;//400c 50h
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        waitForStart();
        while(opModeIsActive())
        {
            dashboard.startCameraStream(webcam.getStream(),  10);
            telemetry.addData("X Position", Camera.screenPosition.x);
            telemetry.addData("Y Position", Camera.screenPosition.y);
            telemetry.addLine();
            telemetry.addData("Area", Camera.score);
            telemetry.addData("Width", Camera.width);
            telemetry.addData("Height", Camera.height);
            telemetry.update();

            dashboardTelemetry.addData("X Position", Camera.screenPosition.x);
            dashboardTelemetry.addData("Y Position", Camera.screenPosition.y);
            dashboardTelemetry.addLine();
            dashboardTelemetry.addData("Area", Camera.score);
            dashboardTelemetry.addData("Width", Camera.width);
            dashboardTelemetry.addData("Height", Camera.height);
            dashboardTelemetry.update();
        }
    }
}