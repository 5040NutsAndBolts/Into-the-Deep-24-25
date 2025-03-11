package org.firstinspires.ftc.teamcode.HelperClasses.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Odometry;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;

@TeleOp(name = "odotest", group = "Teleop")
public class OdoTest extends OpMode {
	private Odometry odo;
	protected Telemetry dashboard;

	@Override
	public void init() {
		odo = new Odometry(hardwareMap);
		dashboard = FtcDashboard.getInstance().getTelemetry();
		odo.reset();
	}

	@Override
	public void loop() {
		odo.update();
		telemetry.addLine(odo.toString());
		telemetry.update();
		dashboard.addLine(odo.toString());
		dashboard.update();
	}
}