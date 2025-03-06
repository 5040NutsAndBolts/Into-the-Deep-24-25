package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotOpMode;

@TeleOp(name = "1 Driver", group = "Teleop")
public class OneDriverTeleop extends RobotOpMode {
	@Override
	public void loop() {
		drivetrain.drive (gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
		drivetrain.toggleSlowMode(gamepad1.x || chamberWheel.getTopPosition() > 1000);
		auto180(gamepad1.y);

		chamberWheel.teleopControl(gamepad1.dpad_up ? 1 : gamepad1.dpad_down ? -1 : 0);
		chamberWheel.spin(gamepad1.right_bumper, gamepad1.left_bumper);

		scissor.updateScissor( gamepad1.dpad_right ? 1 : gamepad1.dpad_left ? -1 : 0);

		subWheel.spin(gamepad1.right_trigger, gamepad1.left_trigger);
		subWheel.tiltCarriage(gamepad1.a, gamepad1.b);
		subWheel.toggleAutoSpitOverride(gamepad2.x);

		/*if (pTeamColor == TeamColor.red)
			blinkin.turnRed();
		else if (pTeamColor == TeamColor.blue)
			blinkin.turnBlue();
		else if (pTeamColor == TeamColor.noColor) {
			blinkin.turnYellow();
		} else {
			blinkin.turnOrange();
		}*/

		telemetry.addLine("SLOWMODE: " + drivetrain.isSlow());
		telemetry.update();
	}
}
