package org.firstinspires.ftc.teamcode.HelperClasses;

@SuppressWarnings("unused")
public class FTCConstants {
	public enum TeamColor {
		red, blue, yellow, noColor
	}

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
}
