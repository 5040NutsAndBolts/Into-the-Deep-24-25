package org.firstinspires.ftc.teamcode.HelperClasses;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;

public class Odometry {
    private final DcMotorEx leftOdom, rightOdom, centerOdom;
    public int leftE, rightE, centerE;
    public int leftO, rightO, centerO;

    public Odometry(HardwareMap hardwareMap){
        leftOdom = hardwareMap.get(DcMotorEx.class, "Front Left");
        rightOdom = hardwareMap.get(DcMotorEx.class, "Front Right");
        centerOdom = hardwareMap.get(DcMotorEx.class, "Back Left");
        leftO = leftOdom.getCurrentPosition();
        rightO = rightOdom.getCurrentPosition();
        centerO = centerOdom.getCurrentPosition();
    }

    public void updateOdoPosition() {
        leftE = leftOdom.getCurrentPosition() - leftO;
        rightE = rightOdom.getCurrentPosition() - rightO;
        centerE = centerOdom.getCurrentPosition() - centerO;
    }

    public void resetOdometry() {
        leftO = leftOdom.getCurrentPosition();
        rightO = rightOdom.getCurrentPosition();
        centerO = centerOdom.getCurrentPosition();
    }

    @NonNull
    @Override
    public String toString() {
        return "le: " + leftE + "\nce: " + centerE + "\nre: " + rightE;
    }

}