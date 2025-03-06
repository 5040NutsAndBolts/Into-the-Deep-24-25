package org.firstinspires.ftc.teamcode.Mechanisms;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.teamcode.HelperClasses.ColourSensor;
import org.firstinspires.ftc.teamcode.RobotOpMode;
import org.firstinspires.ftc.teamcode.RobotOpMode.TeamColor;

public class SubmersibleFlywheelIntake {
	private final CRServo leftIntakeServo, rightIntakeServo;
	private final Servo tiltServo;
	private final ColourSensor colourSensor;
	private boolean autoSpitOverride;
	private boolean spitOut = false;

	public SubmersibleFlywheelIntake(@NonNull HardwareMap hardwareMap) {
		leftIntakeServo = hardwareMap.get(CRServo.class, "Left Scissor Intake Servo");
		rightIntakeServo = hardwareMap.get(CRServo.class, "Right Scissor Intake Servo");
		rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
		tiltServo = hardwareMap.get(Servo.class, "Scissor Tilt Servo");
		colourSensor = new ColourSensor(hardwareMap, "Scissor Colour Sensor");
	}

	public void setTeamColour (@NonNull TeamColor in) {
		RobotOpMode.pTeamColor = in;
	}

	public TeamColor getTeamColour () {
		return RobotOpMode.pTeamColor;
	}

	public void spin (boolean in, boolean out) {
		if(colourSensor.getBest() == (RobotOpMode.pTeamColor == TeamColor.red ? TeamColor.blue : TeamColor.red) && !autoSpitOverride) // If colour sensor sees the opposite teams' colour, spit out
			spitOut = true;

		if(!autoSpitOverride && spitOut){
			leftIntakeServo.setPower(-1);
			rightIntakeServo.setPower(-1);
		}
		else if(in && !out) {
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

	private boolean lastPressed = false;
	public void toggleAutoSpitOverride(boolean input) {
		if (lastPressed != input && input) autoSpitOverride = !autoSpitOverride;
		lastPressed = input;
	}

	public boolean willAutoSpit() {
		return autoSpitOverride;
	}


	@NonNull
	@Override
	public String toString() {
		return
			"Left Intake Servo Power: " + leftIntakeServo.getPower() + "\n" +
			"Right Intake Servo Power: " + rightIntakeServo.getPower() + "\n" +
			"Tilt Servo Position: " + tiltServo.getPosition() + "\n" +
			"Auto Spit Override: " + autoSpitOverride + "\n" +
			"Colour Sensor: " + colourSensor.toString();
	}
}