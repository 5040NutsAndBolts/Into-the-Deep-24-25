
package org.firstinspires.ftc.teamcode.HelperClasses;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotOpMode.TeamColor;


public class ColourSensor {
    private final ColorSensor colorSensor;

    public ColourSensor(HardwareMap hardwareMap, String name) {
        colorSensor = hardwareMap.get(ColorSensor.class,  name);
    }
    public double getRed(){
       return colorSensor.red();
    }
    public double getBlue(){
       return colorSensor.blue();
    }
    public double getYellow() {
        if (colorSensor.green() < 50 || colorSensor.red() < 50) //If either is very low, it's likely not yellow
            return 0;
        return (colorSensor.green() + colorSensor.red()) / 2.0;
    }
    public TeamColor getBest () {
         if (getBlue() > getRed() && getBlue() > 200)
            return TeamColor.blue;
        else if (getRed() > getBlue() && getRed() > 200 && colorSensor.green() < 120)
            return TeamColor.red;
        else return TeamColor.noColor;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "R: "+getRed() + "\n" +
                "G: "+colorSensor.green() + "\n" +
                "B: "+getBlue()+ "\n" +
                "Y: "+getYellow()+ "\n" +
                "Best Fit: " + getBest();
    }
}