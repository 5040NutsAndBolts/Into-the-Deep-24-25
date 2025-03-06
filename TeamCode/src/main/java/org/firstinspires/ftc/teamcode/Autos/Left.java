package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Left NC", group = "Autonomous")
public class Left extends AutoOpMode {
	@Override
	public void loop() {
		super.loop();
		if(parkToggle) {
			while(odo.centerE > -20000) {
				drivetrain.drive(0, -.8, 0);
				odo.updateOdoPosition();
				telemetry.addLine("PARK");
				updateTelemetry();
			}
			requestOpModeStop();
		}
	}
}