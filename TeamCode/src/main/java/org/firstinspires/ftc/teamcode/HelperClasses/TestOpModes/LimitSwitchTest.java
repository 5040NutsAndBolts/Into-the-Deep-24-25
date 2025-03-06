package org.firstinspires.ftc.teamcode.HelperClasses.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HelperClasses.LimitSwitch;

@TeleOp(name = "limittest", group = "Teleop")
public class LimitSwitchTest extends OpMode {
	private LimitSwitch ls;

	@Override
	public void init() {
		ls = new LimitSwitch(hardwareMap, "Min Scissor Switch");
	}

	@Override
	public void loop() {
		telemetry.addLine(ls.toString());
		telemetry.update();
	}
}
