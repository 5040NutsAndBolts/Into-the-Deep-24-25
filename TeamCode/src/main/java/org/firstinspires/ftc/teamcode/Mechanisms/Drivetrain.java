package org.firstinspires.ftc.teamcode.Mechanisms;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
public class Drivetrain {
    public final DcMotorEx frontLeft,frontRight,backLeft,backRight;
    public double speed = 1;

    public Drivetrain(HardwareMap hardwareMap) {
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
    public void drive(double forward, double sideways, double rotation) {
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