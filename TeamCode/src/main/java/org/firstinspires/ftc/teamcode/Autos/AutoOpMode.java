package org.firstinspires.ftc.teamcode.Autos;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HelperClasses.Camera;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@Disabled
public class AutoOpMode extends RobotOpMode {
    private boolean lastParkButton = false;
    protected boolean parkToggle = true;
    private int autoDelaySeconds;
    private Camera cam;

    public void togglePark(boolean input) {
        if(lastParkButton != input && input)
                parkToggle = !parkToggle;
        lastParkButton = input;
    }

    private boolean lastDecr, lastIncr;
    public void adjustDelay (boolean decrease, boolean increase) {
        if(increase && decrease)
            return;
        if (decrease != lastDecr && decrease && autoDelaySeconds >= 0)
            autoDelaySeconds--;
        if (increase != lastIncr && increase && autoDelaySeconds <= 25)
            autoDelaySeconds++;
        lastDecr = decrease;
        lastIncr = increase;
    }

    @Override
    public void init() {
        super.init();
        cam = new Camera(hardwareMap);
    }

    @Override
    public void init_loop() {
        togglePark(gamepad1.dpad_left);
        odo.resetOdometry();
        adjustDelay(gamepad1.a, gamepad1.y);
        telemetry.addLine("CURRENT DELAY: " + autoDelaySeconds);
        telemetry.addLine("PARKTOGGLE: " + parkToggle);
        if (gamepad1.dpad_up)
            pTeamColor = TeamColor.red;
        else if (gamepad1.dpad_down)
            pTeamColor = TeamColor.blue;
        cam.color = pTeamColor;
        telemetry.addLine("TEAM COLOR: " + pTeamColor);
        telemetry.update();
    }

    protected void updateTelemetry() {
        telemetry.addLine("ODO:\n"+odo.toString());
        telemetry.addLine("WHEEL:\n"+ chamberWheel.toString());
        telemetry.addLine("DRIVETRAIN:\n"+drivetrain.toString());
        telemetry.addLine("PARK: " + parkToggle);
        telemetry.update();
    }

    @Override
    public void loop () {
        ElapsedTime delay = new ElapsedTime();
        while (delay.seconds() < autoDelaySeconds){
            telemetry.addLine("WAITING: " + delay.seconds() + "/" + autoDelaySeconds);
            telemetry.update();
        }
    }
}