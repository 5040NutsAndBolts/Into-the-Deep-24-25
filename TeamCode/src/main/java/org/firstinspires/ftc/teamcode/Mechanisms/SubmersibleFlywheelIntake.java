package org.firstinspires.ftc.teamcode.Mechanisms;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.*;

public class SubmersibleFlywheelIntake {
	private final CRServo leftIntakeServo, rightIntakeServo;
	private final Servo tiltServo;

	public SubmersibleFlywheelIntake(@NonNull HardwareMap hardwareMap) {
		leftIntakeServo = hardwareMap.get(CRServo.class, "Left Scissor Intake Servo");
		rightIntakeServo = hardwareMap.get(CRServo.class, "Right Scissor Intake Servo");
		rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
		tiltServo = hardwareMap.get(Servo.class, "Scissor Tilt Servo");
	}

	public void spin (boolean in, boolean out) {
		if(in && !out) {
			leftIntakeServo.setPower(1);
			rightIntakeServo.setPower(1);
		}
		else if(out && !in){
			leftIntakeServo.setPower(-1);
			rightIntakeServo.setPower(-1);
		}else {
			leftIntakeServo.setPower(0);
			rightIntakeServo.setPower(0);
		}
	}

	public void spin (double in, double out)  {
		spin(in > .1, out > .1);
	}
	public void tiltCarriage (boolean up, boolean down) {
		if(up && !down)
			tiltServo.setPosition(1);
		else if(!up && down)
			tiltServo.setPosition(0);
	}


	@NonNull
	@Override
	public String toString() {
		return
			"Left Intake Servo Power: " + leftIntakeServo.getPower() + "\n" +
			"Right Intake Servo Power: " + rightIntakeServo.getPower() + "\n" +
			"Tilt Servo Position: " + tiltServo.getPosition();
	}
}