package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.HelperClasses.PID;

@Autonomous(name = "Left", group = "Autonomous")
public class Left extends AutoOpMode {
	@Override
	public void loop() {
		super.loop();

		odo.update();
		updateAutoTelemetry();
		moveTo(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES,45) );
	}
}