package org.firstinspires.ftc.teamcode.HelperClasses;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class
Blinkin {
    private final RevBlinkinLedDriver lights;

    public Blinkin(HardwareMap hardwareMap){
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
    }
    public void turnRed(){
        lights.resetDeviceConfigurationForOpMode();
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
    }
    public void turnBlue(){
        lights.resetDeviceConfigurationForOpMode();
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
    }
    public void turnYellow(){
        lights.resetDeviceConfigurationForOpMode();
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
    }
    public void turnOrange(){
        lights.resetDeviceConfigurationForOpMode();
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
    }
}

