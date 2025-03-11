package org.firstinspires.ftc.teamcode.Autos;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HelperClasses.Camera;
import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Odometry;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@Disabled
public class AutoOpMode extends RobotOpMode {
    private boolean lastParkButton = false;
    protected boolean parkToggle = false    ;
    private int autoDelaySeconds;
    protected Odometry odo;

    public void togglePark(boolean input) {
        if(lastParkButton != input && input)
                parkToggle = !parkToggle;
        lastParkButton = input;
    }

    private boolean lastDecr, lastIncr;
    public void adjustDelay (boolean decrease, boolean increase) {
        if(increase && decrease)
            return;
        if (decrease != lastDecr && decrease && autoDelaySeconds > 0)
            autoDelaySeconds--;
        if (increase != lastIncr && increase && autoDelaySeconds < 25)
            autoDelaySeconds++;
        lastDecr = decrease;
        lastIncr = increase;
    }

    @Override
    public void init() {
        super.init();
        odo = new Odometry(hardwareMap);
    }

    @Override
    public void init_loop() {
        togglePark(gamepad1.dpad_left);
        adjustDelay(gamepad1.a, gamepad1.y);
        odo.reset();
        addTelemetry("PARK: " + parkToggle);
        addTelemetry("DELAY: " + autoDelaySeconds);
        updateTelemetry();
    }

    protected void updateAutoTelemetry() {
        addTelemetry("ODO:\n"+odo.toString());
        addTelemetry("PARK: " + parkToggle);
        updateTelemetry();
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