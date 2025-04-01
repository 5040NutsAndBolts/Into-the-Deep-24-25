package org.firstinspires.ftc.teamcode.Autos;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.HelperClasses.FTCConstants;
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

    boolean gonce = false;
    protected void moveTo (@NonNull Pose2D target) {
        PID rotationController = new PID(0.033 , 1.3e-6, 0);
        PID xController = new PID(.07, .85e-5, 0);
        PID yController = new PID(.08, 1.55e-5, 0);
        xController.setTarget(target.getX(DistanceUnit.INCH));
        yController.setTarget(target.getY(DistanceUnit.INCH));
        rotationController.setTarget(target.getHeading(AngleUnit.DEGREES));
        double rot = -FTCConstants.clamp(rotationController.autoControl(Math.toDegrees(odo.getPinpoint().getHeading())),-1,1);
        double x =FTCConstants.clamp(xController.autoControl(odo.getPinpoint().getPosX()/25.4),-1,1);
        double y = FTCConstants.clamp(yController.autoControl(odo.getPinpoint().getPosY()/25.4),-1,1);
        ElapsedTime e = new ElapsedTime();
        while (e.seconds() < 8 && !gonce) {
            odo.update();
            dashboard.addData("rC", rot);
            dashboard.addData("xC", x);
            dashboard.addData("yC", y);
            dashboard.addData("rE",(FTCConstants.clamp(Math.toDegrees(odo.getPinpoint().getHeading()) - rotationController.getTarget(),-1,1)));
            dashboard.addData("xE", FTCConstants.clamp(odo.getPinpoint().getPosX()/25.4 - xController.getTarget(),-1,1));
            dashboard.addData("yE", FTCConstants.clamp(odo.getPinpoint().getPosY()/25.4 - yController.getTarget(),-1,1));
            updateAutoTelemetry();
        }
        gonce = true;
        ElapsedTime a = new ElapsedTime();
        while(!poseWithin(odo.getPosition(), target, 2, 5)) {
            if(a.seconds() > .6)
                return;
            odo.update();
            rot = -FTCConstants.clamp(rotationController.autoControl(Math.toDegrees(odo.getPinpoint().getHeading())),-1,1);
            x =FTCConstants.clamp(xController.autoControl(odo.getPinpoint().getPosX()/25.4),-1,1);
            y = FTCConstants.clamp(yController.autoControl(odo.getPinpoint().getPosY()/25.4),-1,1);
            telemetry.addLine(rotationController.toString());
            telemetry.addLine(xController.toString());
            telemetry.addLine(yController.toString());
            dashboard.addData("rC", rot);
            dashboard.addData("xC", x);
            dashboard.addData("yC", y);
            dashboard.addData("rE", Math.toDegrees(odo.getPinpoint().getHeading()) - rotationController.getTarget());
            dashboard.addData("xE", odo.getPinpoint().getPosX()/25.4 - xController.getTarget());
            dashboard.addData("yE", odo.getPinpoint().getPosY()/25.4 - yController.getTarget());
            dashboard.addLine();
            dashboard.addLine();
            updateAutoTelemetry();
            drivetrain.fieldOrientedDrive(
                    x,
                    y,
                    rot,
                    odo
            );
        }
    }
    protected boolean poseWithin (@NonNull Pose2D current, @NonNull Pose2D target, double latTolerance, double rotTolerance) {
        return Math.abs(current.getX(DistanceUnit.INCH) - target.getX(DistanceUnit.INCH)) < latTolerance
                && Math.abs(current.getY(DistanceUnit.INCH) - target.getY(DistanceUnit.INCH)) < latTolerance
                && Math.abs(current.getHeading(AngleUnit.DEGREES) - target.getHeading(AngleUnit.DEGREES)) < rotTolerance;
    }
}