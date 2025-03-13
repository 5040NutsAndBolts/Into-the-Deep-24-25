package org.firstinspires.ftc.teamcode.Autos;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.HelperClasses.Camera;
import org.firstinspires.ftc.teamcode.HelperClasses.Odometry.Odometry;
import org.firstinspires.ftc.teamcode.HelperClasses.PID;
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
        updateAutoTelemetry();
        odo.update();
        odo.reset();
    }

    protected void updateAutoTelemetry() {
        telemetry.addLine(odo.toString());
        telemetry.addLine(drivetrain.toString());
        dashboard.addLine(odo.toString());
        dashboard.addLine(drivetrain.toString());
        telemetry.update();
        dashboard.update();
    }

    @Override
    public void loop () {
        ElapsedTime delay = new ElapsedTime();
        while (delay.seconds() < autoDelaySeconds){
            telemetry.addLine("WAITING: " + delay.seconds() + "/" + autoDelaySeconds);
            telemetry.update();
        }
    }

    protected void moveTo (@NonNull Pose2D target) {
        PID rotationController = new PID(.05, 0, 0);
        PID xController = new PID(.25, 0.0017, 0);
        PID yController = new PID(.25, 0.0017, 0);
        rotationController.setTarget(target.getHeading(AngleUnit.DEGREES));
        xController.setTarget(target.getX(DistanceUnit.INCH));
        yController.setTarget(target.getY(DistanceUnit.INCH));

        while(!poseWithin(odo.getPosition(), target, .05, .5)) {
            odo.update();
            telemetry.addLine(rotationController.toString());
            telemetry.addLine(xController.toString());
            telemetry.addLine(yController.toString());
            updateAutoTelemetry();
            drivetrain.fieldOrientedDrive(
                    xController.autoControl(odo.getPosition().getX(DistanceUnit.INCH)),
                    yController.autoControl(odo.getPosition().getY(DistanceUnit.INCH)),
                    -rotationController.autoControl(odo.getPosition().getHeading(AngleUnit.DEGREES)),
                    odo
            );
        }
    }


    protected boolean within(double current, double target, double tolerance) {
        return Math.abs(current - target) < tolerance;
    }
    protected boolean poseWithin (@NonNull Pose2D current, @NonNull Pose2D target, double latTolerance, double rotTolerance) {
        return Math.abs(current.getX(DistanceUnit.INCH) - target.getX(DistanceUnit.INCH)) < latTolerance
                && Math.abs(current.getY(DistanceUnit.INCH) - target.getY(DistanceUnit.INCH)) < latTolerance
                && Math.abs(current.getHeading(AngleUnit.DEGREES) - target.getHeading(AngleUnit.DEGREES)) < rotTolerance;
    }
}