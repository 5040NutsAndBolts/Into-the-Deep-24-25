package org.firstinspires.ftc.teamcode.HelperClasses.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.ChamberFlywheelDeposit;

@TeleOp(group = "Teleop", name = "WheelTest")
public class WheelTest extends OpMode {
	ChamberFlywheelDeposit wheel;
	@Override
	public void init() {
		wheel = new ChamberFlywheelDeposit(hardwareMap);
	}

	@Override
	public void loop() {
		//wheel.update(gamepad1.left_stick_y);
		while(Math.abs(wheel.getTopPosition() - 1600) > 15) {
			wheel.setTopTarget(1900);
			wheel.updateSlides();
			telemetry.addLine(wheel.toString());
			telemetry.update();
		}
		ElapsedTime e = new ElapsedTime();
		while(wheel.getTopPosition() > 15) {
			wheel.setTopTarget(0);
			wheel.updateSlides();
			telemetry.addLine(wheel.toString());
			telemetry.update();
		}
	}
}
