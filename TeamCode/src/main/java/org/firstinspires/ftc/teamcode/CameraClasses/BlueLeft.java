package org.firstinspires.ftc.teamcode.CameraClasses;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Autos.AutoOpMode;
import org.firstinspires.ftc.teamcode.HelperClasses.Camera;
import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Odometry;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled
public class BlueLeft extends AutoOpMode
{

	public enum specPosition //finds the position of the
	{
		isFound,
		isNotFound
	}
	specPosition auto = specPosition.isNotFound;

	public boolean isStarted = false;


	public void loop()
	{

		Odometry robot = new Odometry(hardwareMap);
		isStarted = true;
		specPosition spec = specPosition.isNotFound;
		Camera webcam = new Camera(hardwareMap);

		Telemetry dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();


			telemetry.addData("BlueFinder X", Camera.screenPosition.x);
			telemetry.addData("BlueFinder Y", Camera.screenPosition.y);
			dashboardTelemetry.update();



			while(isStarted = true) {



				if (Camera.screenPosition.x < 397 && spec == specPosition.isNotFound)
							drivetrain.robotOrientedDrive(0,0.1,0);
				if (Camera.screenPosition.x > 403 && spec == specPosition.isNotFound)
					drivetrain.robotOrientedDrive(0,-0.1,0);


					while (Camera.screenPosition.x >= 397 && Camera.screenPosition.x <= 403) {
							spec = specPosition.isFound;
					}
					while (spec == specPosition.isFound){
						drivetrain.robotOrientedDrive(-0.3,0,0);
						if (Camera.screenPosition.x <= 395)
							drivetrain.robotOrientedDrive(0,0.1,0);
						if (Camera.screenPosition.x >= 405)
							drivetrain.robotOrientedDrive(0,-0.1,0);
					}




				}


				telemetry.addData("Auto", auto);

				telemetry.update();
				dashboardTelemetry.addData("auto", auto);
				dashboardTelemetry.update();
			}

	public void stop(){
		super.stop();
	}
}


