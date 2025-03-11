package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HelperClasses.Camera;

@Autonomous(name = "Right NC", group = "Autonomous")
public class Right extends AutoOpMode {
	@Override
	public void loop() {
		super.loop();

		//Hang first specimen
		while(odo.centerE < 5000) {
			drivetrain.drive(0, .6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 1");
			updateTelemetry();
		}
		ElapsedTime e = new ElapsedTime();

		while((odo.rightE + odo.leftE) / 2 < 7450) {
			drivetrain.drive(.6,0 , 0);
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 2");
			updateTelemetry();
		}
		e.reset();
		chamberWheel.setTopTarget(1700);
		while(Math.abs(chamberWheel.getTopPosition() - 1700) > 20 && e.seconds() < 3) {
			drivetrain.drive(0,0 , 0);
			chamberWheel.updateTopMotor();
			chamberWheel.updateBottomMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 3");
			updateTelemetry();
		}
		e.reset();
		while((odo.rightE + odo.leftE) / 2 < 9950 && e.seconds() < 2) {
			drivetrain.drive(.5,0 , 0);
			chamberWheel.updateTopMotor();
			chamberWheel.updateBottomMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 4");
			updateTelemetry();
		}
		chamberWheel.setTopTarget(0);
		chamberWheel.setBottomTarget(350);
		e.reset();
		while(chamberWheel.getBottomPosition() < 350 && e.seconds() < 3) {
			chamberWheel.spin(1,0);
			drivetrain.drive(-.05,0,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 5");
			updateTelemetry();
		}
		chamberWheel.setTopTarget(0);
		chamberWheel.setBottomTarget(0);
		e.reset();
		while(chamberWheel.getBottomPosition() > 10 && e.seconds() < 2) {
			chamberWheel.setTopTarget(0);
			chamberWheel.setBottomTarget(0);
			chamberWheel.spin(1,0);
			drivetrain.drive(-.3,0,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 6");
			updateTelemetry();
		}

		while(odo.rightE + odo.leftE > 2000) {
			chamberWheel.setTopTarget(0);
			chamberWheel.setBottomTarget(0);
			drivetrain.drive(-.5, 0, 0);
			chamberWheel.spin(1,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 7");
			updateTelemetry();
		}

		//Push leftmost specimen into observation zone
		while(odo.centerE > -6500) {
			chamberWheel.setTopTarget(0);
			chamberWheel.setBottomTarget(0);
			drivetrain.drive(0, -.6, 0);
			chamberWheel.spin(1,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 8");
			updateTelemetry();
		}
		e.reset();
		while(e.seconds() < .8) {
			chamberWheel.setTopTarget(0);
			chamberWheel.setBottomTarget(0);
			drivetrain.drive(-.3, 0, 0);
			chamberWheel.spin(1,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 9");
			updateTelemetry();
		}
		while((odo.rightE + odo.leftE) / 2 < 14000) {
			chamberWheel.setTopTarget(0);
			chamberWheel.setBottomTarget(0);
			drivetrain.drive(.6,0 , 0);
			chamberWheel.spin(1,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 10");
			updateTelemetry();
		}
		while(odo.centerE > -9600) {
			drivetrain.drive(0, -.6, 0);
			chamberWheel.spin(0,0);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 11");
			updateTelemetry();
		}
		while((odo.rightE + odo.leftE) / 2 > 400) {
			drivetrain.drive(-.6,0 , 0);
			odo.updateOdoPosition();
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 13");
			updateTelemetry();
		}

		/*

		//Second specimen pickup
		while((odo.rightE + odo.leftE) / 2 < 2500) {
			drivetrain.drive(.6,0 , 0);
			odo.updateOdoPosition();
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 14");
			updateTelemetry();
		}
		double initialR = odo.rightE, initialL = odo.leftE;
		while(Math.abs(odo.rightE - initialR) < 10000 && Math.abs(odo.leftE - initialL) < 10000) {
			drivetrain.drive(0,0 ,.5);
			scissor.scissorMotor.setPower(-.2);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 15");
			updateTelemetry();
		}
		e.reset();
		while(odo.centerE < -8000 && e.seconds() < 1.5) {
			drivetrain.drive(0, .4, 0);
			odo.updateOdoPosition();
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 16");
			updateTelemetry();
		}
		while(odo.centerE > -14000) {
			drivetrain.drive(0, -.8, 0);
			odo.updateOdoPosition();
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 17");
			updateTelemetry();
		}
		odo.resetOdometry();

		drivetrain.drive(0, 0, 0);
		while((odo.rightE + odo.leftE) / 2 < 3000) {
			drivetrain.drive(.8,0 , 0);
			odo.updateOdoPosition();
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 18");
			updateTelemetry();
		}*/

		/*
		e.reset();
		while((odo.rightE + odo.leftE) / 2 < 5000 && e.seconds() < 2.5) {
			drivetrain.drive(.5,0 , 0);
			odo.updateOdoPosition();
			chamberWheel.spin(0,1);
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 19");
			updateTelemetry();
		}
		chamberWheel.setTopTarget(200);
		while(Math.abs(chamberWheel.getTopPosition() - 200) > 30){
			drivetrain.drive(.2,0 , 0);
			odo.updateOdoPosition();
			chamberWheel.updateBottomMotor();
			chamberWheel.updateTopMotor();
			telemetry.addLine("LOOP 20");
			updateTelemetry();
		}*/

		/*
		e.reset();
		while(odo.centerE > -14500 && e.seconds() < 2) {
			drivetrain.drive(0, -.6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 15");
			updateTelemetry();
		}
		while(odo.centerE < -9500) {
			drivetrain.drive(0, -.6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 16");
			updateTelemetry();
		}*/



		//Push center specimen into observation zone
		while((odo.rightE + odo.leftE) / 2 < 13000) {
			drivetrain.drive(.6,.17 , 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 13");
			updateTelemetry();
		}
		while(odo.centerE > -12000) {
			drivetrain.drive(0, -.6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 14");
			updateTelemetry();
		}
		e.reset();
		while((odo.rightE + odo.leftE) / 2 > 100 && e.seconds() < 3) {
			drivetrain.drive(-.6,0 , 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 15");
			updateTelemetry();
		}
		while(odo.centerE > -11500 && e.seconds() < 2) {
			drivetrain.drive(0, -.6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 16");
			updateTelemetry();
		}
		//Push rightmost specimen into observation zone
		while((odo.rightE + odo.leftE) / 2 < 13000) {
			drivetrain.drive(.6,.17 , 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 17");
			updateTelemetry();
		}
		e.reset();
		while(odo.centerE > -14500 && e.seconds() < 2) {
			drivetrain.drive(0, -.6, 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 18");
			updateTelemetry();
		}
		e.reset();
		while((odo.rightE + odo.leftE) / 2 > 100 && e.seconds() < 3) {
			drivetrain.drive(-.6,0 , 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 19");
			updateTelemetry();
		}
		while((odo.rightE + odo.leftE) / 2 < 7000) {
			drivetrain.drive(.6,0 , 0);
			odo.updateOdoPosition();
			telemetry.addLine("LOOP 20");
			updateTelemetry();
		}




		if(parkToggle) {
			while ((odo.rightE + odo.leftE) / 2 > 100) {
				drivetrain.drive(-.5, 0, 0);
				odo.updateOdoPosition();
				telemetry.addLine("PARK LOOP 1");
				updateTelemetry();
			}
			chamberWheel.slideMotorBottom.setPower(0);
			chamberWheel.slideMotorTop.setPower(0);
			drivetrain.drive(0, 0, 0);
			drivetrain.neutral();
			while(true) {
				odo.updateOdoPosition();
				telemetry.addLine("End Loop");
				updateTelemetry();
			}
		}
	}
}