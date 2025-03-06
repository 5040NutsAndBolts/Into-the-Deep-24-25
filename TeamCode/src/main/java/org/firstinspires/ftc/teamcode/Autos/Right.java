package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Right NC", group = "Autonomous")
public class Right extends AutoOpMode {
	@Override
	public void loop() {
		super.loop();

		while(odo.centerE < 5000) {
			drivetrain.drive(0, .6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 1");
			updateTelemetry();
		}
		ElapsedTime e = new ElapsedTime();
		chamberWheel.setTopTarget(1700);
		while((odo.rightE + odo.leftE) / 2 < 9300 && e.seconds() < 4) {
			drivetrain.drive(.5,0 , 0);
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 2");
			updateTelemetry();
		}
		e.reset();
		while(chamberWheel.getTopPosition() < 1550 && e.seconds() < 1) {
			drivetrain.drive(0,0 , 0);
			chamberWheel.updateTopMotor();
			chamberWheel.updateBottomMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 3");
			updateTelemetry();
		}
		chamberWheel.setTopTarget(0);
		chamberWheel.setBottomTarget(300);
		e.reset();
		while(chamberWheel.getBottomPosition() < 300 & e.seconds() < 2) {
			chamberWheel.spin(.5,0);
			drivetrain.drive(-.05,0,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 4");
			updateTelemetry();
		}
		chamberWheel.setBottomTarget(0);
		e.reset();
		while(chamberWheel.getBottomPosition() > 120 && e.seconds() < 2) {
			chamberWheel.spin(1,0);
			drivetrain.drive(-.3,0,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 5");
			updateTelemetry();
		}
		chamberWheel.setBottomTarget(0);
		chamberWheel.spin(0,0);


		if(parkToggle) {
			while ((odo.rightE + odo.leftE) / 2 > 100) {
				drivetrain.drive(-.5, 0, 0);
				chamberWheel.updateTopMotor();
				chamberWheel.updateBottomMotor();
				odo.updateOdoPosition();
				telemetry.addLine("PARK LOOP 1");
				updateTelemetry();
			}
			while(odo.centerE > -14000) {
				drivetrain.drive(-.20, -.6, 0);
				chamberWheel.updateTopMotor();
				chamberWheel.updateBottomMotor();
				odo.updateOdoPosition();
				telemetry.addLine("PARK LOOP 2");
				updateTelemetry();
			}
		}
		requestOpModeStop();
	}
}
