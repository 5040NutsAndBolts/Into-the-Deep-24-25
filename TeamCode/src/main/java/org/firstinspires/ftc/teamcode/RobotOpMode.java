package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HelperClasses.FTCConstants;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;

@Disabled
public class RobotOpMode extends OpMode {
    protected Drivetrain drivetrain;

    protected Telemetry dashboard;
    protected static FTCConstants.TeamColor pTeamColor = FTCConstants.TeamColor.noColor;

    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance().getTelemetry();
       drivetrain = new Drivetrain (hardwareMap);
    }

    @Override
    public void init_loop() {
        if (gamepad1.dpad_up)
            pTeamColor = FTCConstants.TeamColor.red;
        else if (gamepad1.dpad_down)
            pTeamColor = FTCConstants.TeamColor.blue;


        telemetry.addLine("TEAM COLOR: " +pTeamColor);
        dashboard.addLine("TEAM COLOR: " +pTeamColor);
        telemetry.update();
        dashboard.update();
    }

    //Leave empty
    @Override public void loop() {}
}