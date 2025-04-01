package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.SplineGenerator;
import org.firstinspires.ftc.teamcode.HelperClasses.PID;

import java.util.ArrayList;

@Autonomous(name = "Left", group = "Autonomous")

public class Left extends AutoOpMode {
	ArrayList<Pose2D> positions = new ArrayList<>();


	@Override
	public void init() {
		super.init();

		positions.add(new Pose2D(DistanceUnit.INCH,0,0,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,7.148936170212766,1.3617021276595744,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,13.27659574468085,2.5531914893617023,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,21.78723404255319,5.617021276595745,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,23.48936170212766,10.72340425531915,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,23.659574468085104,15.659574468085106,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,20.93617021276596,21.617021276595743,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,16.680851063829788,23.319148936170212,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,10.212765957446809,21.95744680851064,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,10.893617021276595,17.70212765957447,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,9.191489361702127,13.446808510638297,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,3.74468085106383,8,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,-0.1702127659574468,4.76595744680851,AngleUnit.DEGREES,45));
		positions.add(new Pose2D(DistanceUnit.INCH,0,0,AngleUnit.DEGREES,45));

		positions = SplineGenerator.generateSpline(
				positions,
				8,
				1
		);
	}

	@Override
	public void loop() {
		super.loop();

		odo.update();
		updateAutoTelemetry();

		for(Pose2D target : positions) {
			moveTo(target);
		}

		requestOpModeStop();
	}
}