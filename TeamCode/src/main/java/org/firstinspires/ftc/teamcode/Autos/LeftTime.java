package org.firstinspires.ftc.teamcode.Autos;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "Left Time", group = "Autonomous")
public class LeftTime extends AutoOpMode {
	@Override
	public void loop() {
		ElapsedTime timer = new ElapsedTime();
		while(timer.seconds() < 6) {
			telemetry.addLine("strafing");
			telemetry.addLine("time: " + timer.seconds());
			telemetry.update();
			drivetrain.drive(0,.5,0);
			if(timer.seconds() >= 6) {
				timer.reset();
				while(timer.seconds() < .5) drivetrain.drive(.5, 0, 0);
				terminateOpModeNow();
			}
		}
	}
}