package org.firstinspires.ftc.teamcode.Mechanisms;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Odometry;

public class Drivetrain {
    public final DcMotorEx frontLeft,frontRight,backLeft,backRight;
    public double speed = 1;

    public Drivetrain(@NonNull HardwareMap hardwareMap) {
        //Drive Motor Initialization
        frontLeft = hardwareMap.get(DcMotorEx.class, "Front Left");
        frontRight = hardwareMap.get(DcMotorEx.class, "Front Right");
        backLeft = hardwareMap.get(DcMotorEx.class, "Back Left");
        backRight = hardwareMap.get(DcMotorEx.class, "Back Right");

        //Needed for how the motors are mounted
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void robotOrientedDrive(double forward, double sideways, double rotation) {
        //Multiplied by speed variable, only changes when in slowmode
        forward *= speed;
        sideways *= speed;
        rotation *= speed;

        //adds all the inputs together to get the number to scale it by
        double scale = Math.abs(rotation) + Math.abs(forward) + Math.abs(sideways);

        //Scales the inputs between 0-1 for the setPower() method
        if (scale > 1) {
            forward /= scale;
            rotation /= scale;
            sideways /= scale;
        }

        //Zeroes out and opposing or angular force from the Mecanum wheels
        frontLeft.setPower(forward - rotation - sideways);
        backLeft.setPower(forward - rotation + sideways);
        frontRight.setPower(forward + rotation + sideways);
        backRight.setPower(forward + rotation - sideways);
    }

    //Battery/motor saver
    public void neutral() {
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void fieldOrientedDrive(double x, double y, double rotation, @NonNull Odometry odo) {
        double P = Math.hypot(y, x);
        double robotAngle = Math.atan2(x, y);

        // Get the current field heading from the GoBILDA Pinpoint
        double fieldHeading = odo.getPinpoint().getHeading();

        // Adjusted motor power calculations using the correct heading reference
        double v5 = P * Math.sin(robotAngle - fieldHeading) + P * Math.cos(robotAngle - fieldHeading) - rotation;
        double v6 = P * Math.sin(robotAngle - fieldHeading) - P * Math.cos(robotAngle - fieldHeading) + rotation;
        double v7 = P * Math.sin(robotAngle - fieldHeading) - P * Math.cos(robotAngle - fieldHeading) - rotation;
        double v8 = P * Math.sin(robotAngle - fieldHeading) + P * Math.cos(robotAngle - fieldHeading) + rotation;

        double scale = Math.max(Math.abs(v5), Math.max(Math.abs(v6), Math.max(Math.abs(v7), Math.abs(v8))));
        v5 /= scale;
        v6 /= scale;
        v7 /= scale;
        v8 /= scale;

        frontLeft.setPower(v5);
        frontRight.setPower(v6);
        backLeft.setPower(v7);
        backRight.setPower(v8);
    }

    //Silly button logic stuff
    boolean lastButton = false;
    public void toggleSlowMode(boolean input) {
        if(lastButton != input && input)  //If button state has changed and it is pressed
            speed = speed ==.5 ? 1 : .5;
        lastButton = input;
    }
    public boolean isSlow() {return speed == .5;}

    @NonNull
    @Override
    public String toString() {
        return
                "Front Left: " + frontLeft.getPower() + "\n" +
                "Front Right: " + frontRight.getPower() + "\n" +
                "Back Left: " + backLeft.getPower() + "\n" +
                "Back Right: " + backRight.getPower();
    }
}