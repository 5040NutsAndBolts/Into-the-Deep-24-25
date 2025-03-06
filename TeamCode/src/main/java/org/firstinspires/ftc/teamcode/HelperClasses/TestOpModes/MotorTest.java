package org.firstinspires.ftc.teamcode.HelperClasses.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "motortest", group = "Teleop")
public class MotorTest extends OpMode {
	private DcMotor motor;

	@Override
	public void init() {
		motor = hardwareMap.get(DcMotor.class, "Motor");

	}

	@Override
	public void loop() {
		motor.setPower(gamepad1.dpad_up ? 1 : gamepad1.dpad_down ? -1 : 0);
		telemetry.addLine("pwr: "+motor.getPower());
	}
}
