package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Roadrunner.drive.RoadrunnerMecanumDrive;

public class Drivetrain {
    private final RoadrunnerMecanumDrive drive;

    public Drivetrain(HardwareMap hardwareMap) {
        drive = new RoadrunnerMecanumDrive(hardwareMap);
    }

    public void setDrivePowers (double forward, double lateral, double rotation) {
        drive.setDrivePower(new Pose2d(forward, lateral, rotation));
    }
}