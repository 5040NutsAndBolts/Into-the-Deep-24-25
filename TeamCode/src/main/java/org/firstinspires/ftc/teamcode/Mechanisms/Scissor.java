package org.firstinspires.ftc.teamcode.Mechanisms;
import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.HelperClasses.LimitSwitch;
import org.firstinspires.ftc.teamcode.HelperClasses.PID;

public class Scissor {
	public final DcMotor scissorMotor;
	private final LimitSwitch minimumSwitch;//, maximumSwitch;
	private int scissorMotorOffset;
	private final PID scissorController;

	public Scissor (@NonNull HardwareMap hardwareMap) {
		scissorMotor = hardwareMap.get(DcMotor.class, "Scissor Motor");
		minimumSwitch = new LimitSwitch(hardwareMap, "Min Scissor Switch");
		//maximumSwitch = new LimitSwitch(hardwareMap, "Max Scissor Switch");
		scissorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		scissorMotorOffset = scissorMotor.getCurrentPosition();
		scissorController = new PID(.03, 0.00003, 0, this::getPosition, 1);
	}

	public void updateScissor (double in) {
		if(Math.abs(in) < .03)
			in = 0;

		double power = scissorController.teleOpControl(in);

		if (minimumSwitch.isPressed()) {
			resetPosition();
			scissorController.setTarget(0);
		}
		if(scissorController.getTarget() > 360) {
			scissorController.setTarget(360);
			power = -.1;
		}
		scissorMotor.setPower(power);
	}

	private void resetPosition () {
		scissorMotorOffset = scissorMotor.getCurrentPosition();
	}

	public double getPosition () {
		return scissorMotor.getCurrentPosition() - scissorMotorOffset;
	}

	public void setTarget (double target) {
		scissorController.setTarget(target);
	}

	@NonNull
	@Override public String toString () {
		return
				"Get Scissor Position: " + getPosition() + "\n" +
				"Scissor Position Offset: " + scissorMotorOffset + "\n" +
				"Scissor Power: " + scissorMotor.getPower() + "\n" +
				"Min Switch: " + minimumSwitch.isPressed() + "\n" +
				scissorController.toString();
	}
}
