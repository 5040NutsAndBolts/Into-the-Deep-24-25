package org.firstinspires.ftc.teamcode.Teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HelperClasses.PID;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@TeleOp (name = "2 Driver", group = "Teleop")
public class TwoDriverTeleop extends RobotOpMode {
	@Override
	public void init() {
		super.init();
		chamberWheel.bottomController = new PID(.01,0.0000001,.00, chamberWheel::getBottomPosition, 0);
	}

	@Override
	public void loop() {
		drivetrain.drive (gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
		drivetrain.toggleSlowMode(gamepad1.x);

		chamberWheel.rawControl(gamepad2.left_stick_y);
		chamberWheel.spin(gamepad2.left_trigger,gamepad2.right_trigger);

		scissor.updateScissor(-gamepad2.right_stick_y);

		subWheel.spin(gamepad1.right_trigger, gamepad1.left_trigger);
		subWheel.tiltCarriage(gamepad2.right_bumper, gamepad2.left_bumper);
		subWheel.toggleAutoSpitOverride(gamepad2.a);

		telemetry.addLine("SLOWMODE: " + drivetrain.isSlow());
		telemetry.addLine("AUTO SPIT: " + subWheel.willAutoSpit());
		telemetry.update();
	}
}