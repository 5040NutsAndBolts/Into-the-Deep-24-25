package org.firstinspires.ftc.teamcode.HelperClasses.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Odometry;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;

@TeleOp(name = "Field Oriented Drive Test", group = "Teleop")
public class FieldOrientedTest extends OpMode {
	private Drivetrain drivetrain;
	private Odometry odo;
	private Telemetry dash;
	@Override
	public void init() {
		drivetrain = new Drivetrain(hardwareMap);
		odo = new Odometry(hardwareMap);
		dash = FtcDashboard.getInstance().getTelemetry();
	}

	@Override
	public void loop() {
		odo.update();
		drivetrain.fieldOrientedDrive(
				gamepad1.left_stick_y,//movex
				gamepad1.left_stick_x,//movey
				-gamepad1.right_stick_x, odo);
		if(gamepad1.a)
			odo.reset();
		telemetry.addLine(drivetrain.toString() + "\n" + odo.toString() + "\n\n\nlsy:" + gamepad1.left_stick_y + "\nlsx:" + (gamepad1.left_stick_x) + "\nrsx:"+ gamepad1.right_stick_x
		);
		telemetry.update();
		dash.addLine(drivetrain.toString() + "\n" + odo.toString());
		dash.addLine(drivetrain.toString() + "\n" + odo.toString() + "\n\n\nlsx:" + gamepad1.left_stick_x + "\nlsy:" + gamepad1.left_stick_y + "\nrsx:"+ gamepad1.right_stick_x
		);
		dash.update();
	}
}
