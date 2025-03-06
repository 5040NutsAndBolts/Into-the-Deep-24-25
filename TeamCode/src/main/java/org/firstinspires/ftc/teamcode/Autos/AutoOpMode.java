package org.firstinspires.ftc.teamcode.Autos;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotOpMode;

@Disabled
public class AutoOpMode extends RobotOpMode {
    private boolean lastParkButton = false;
    protected boolean parkToggle = true;
    private int autoDelaySeconds;

    public void togglePark(boolean input) {
        if(lastParkButton != input && input)
                parkToggle = !parkToggle;
        lastParkButton = input;
    }

    private boolean lastDecr, lastIncr;
    public void adjustDelay (boolean decrease, boolean increase) {
        if (decrease != lastDecr && decrease)
            autoDelaySeconds--;
        if (increase != lastIncr && increase)
            autoDelaySeconds++;
        lastDecr = decrease;
        lastIncr = increase;
    }

    @Override
    public void init_loop() {
        togglePark(gamepad1.dpad_left);
        odo.resetOdometry();
        adjustDelay(gamepad1.dpad_down, gamepad1.dpad_up);
        telemetry.addLine("CURRENT DELAY: " + autoDelaySeconds);
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
    public void auto180(boolean input) {
        throw new UnsupportedOperationException("Not available for autonomous, may cause major issues");
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