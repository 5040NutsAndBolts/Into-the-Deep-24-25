 package org.firstinspires.ftc.teamcode.Mechanisms;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.HelperClasses.LimitSwitch;
import org.firstinspires.ftc.teamcode.HelperClasses.PID;

public class ChamberFlywheelDeposit {
	public final CRServo leftServo, rightServo;
	public final DcMotor slideMotorTop, slideMotorBottom;
	private int slideMotorTopOffset, slideMotorBottomOffset;
	private final PID topController;
	public PID bottomController;
	public final LimitSwitch limitSwitch;

	public ChamberFlywheelDeposit(HardwareMap hardwareMap) {
		leftServo = hardwareMap.get(CRServo.class, "Left Wheel Servo");
		leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
		rightServo = hardwareMap.get(CRServo.class, "Right Wheel Servo");

		slideMotorTop = hardwareMap.get(DcMotorEx.class, "Top Wheel Slide Motor");
		slideMotorBottom = hardwareMap.get(DcMotorEx.class, "Bottom Wheel Slide Motor");
		slideMotorTop.setDirection(DcMotorSimple.Direction.REVERSE);
		slideMotorTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideMotorBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

		slideMotorTopOffset = slideMotorTop.getCurrentPosition();
		slideMotorBottomOffset = slideMotorBottom.getCurrentPosition();

		limitSwitch = new LimitSwitch(hardwareMap, "Wheel Limit Switch");

		topController = new PID(.028,0.000006,.00, this::getTopPosition, 1);
		bottomController = new PID(.028,0,.00, this::getBottomPosition, 1);
	}

	//Spin controls for triggers
	public void spin(double in, double out) {
		int power = in-out > 0.05 ? 1 : in-out < -.05 ? -1 : 0;
		leftServo.setPower(power);
		rightServo.setPower(power);
	}

	//Spin controls for buttons
	public void spin(boolean in, boolean out) {
		if(in && ! out) {
			leftServo.setPower(1);
			rightServo.setPower(1);
		}else if (out && !in) {
			leftServo.setPower(-1);
			rightServo.setPower(-1);
		}else {
			leftServo.setPower(0);
			rightServo.setPower(0);
		}
	}

	public void updateSlides() {
		double topPower = topController.autoControl();
		double bottomPower = bottomController.autoControl();
		if(limitSwitch.isPressed()) {
			resetPosition();
			if(topPower < 0)
				slideMotorTop.setPower(0);
		}
		slideMotorTop.setPower(topPower);
		bottomController.setTarget(bottomPower);
	}

	public void teleopControl (double in) {
		double power = topController.teleOpControl(in);
		if(limitSwitch.isPressed()) {
			resetPosition();
			if(power < 0)
				slideMotorTop.setPower(0);
		}
		if(topController.getTarget() > 1800) topController.setTarget(1800);
		slideMotorBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		slideMotorBottom.setPower(0);
		slideMotorTop.setPower(power);
	}

	public void updateTopMotor() {
		if(limitSwitch.isPressed())
			resetPosition();
		slideMotorTop.setPower(topController.autoControl());
	}

	public void updateBottomMotor() {
		if(limitSwitch.isPressed())
			resetPosition();
		slideMotorBottom.setPower(bottomController.autoControl());
	}

	public void setTopTarget(int target) {
		if(limitSwitch.isPressed())
			resetPosition();

		topController.setTarget(target);
	}
	public void setBottomTarget (int target) {
		if(limitSwitch.isPressed())
			resetPosition();

		bottomController.setTarget(target);
	}



	public double getTopPosition () {
		return slideMotorTop.getCurrentPosition() - slideMotorTopOffset;
	}
	public double getBottomPosition () {
		return slideMotorBottom.getCurrentPosition() - slideMotorBottomOffset;
	}
	private void resetPosition() {
		slideMotorTopOffset = slideMotorTop.getCurrentPosition();
		slideMotorBottomOffset = slideMotorBottom.getCurrentPosition();
	}

	public void rawBottomPower(double in) {
		slideMotorBottom.setPower(in);
	}

	@NonNull
	@Override public String toString() {
		return "Top Controller: " + topController.toString() + "\n" +
				"Bottom Controller: " + bottomController.toString() + "\n" +
				"Slide Motor Top Power: " + slideMotorTop.getPower() + "\n" +
				"Slide Motor Bottom Power: " + slideMotorBottom.getPower() + "\n" +
				"Slide Motor Top Position: " + getTopPosition() + "\n" +
				"Slide Motor Bottom Position: " + getBottomPosition() + "\n" +
				"Limit Switch: " + limitSwitch.isPressed();
	}

	public void rawControl(double in) {
		slideMotorTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		if(in > 0 && limitSwitch.isPressed()) return;
		else
			slideMotorTop.setPower(in);
	}
}


