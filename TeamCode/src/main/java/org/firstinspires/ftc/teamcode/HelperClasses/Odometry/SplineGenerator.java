package org.firstinspires.ftc.teamcode.HelperClasses.Odometry;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Jack Harlow: Prog. Lead Into the Deep (24-25)
 * HEADING CALCULATIONS STILL NEED WORK!!
 */
public class SplineGenerator {

	/**
	 *  Reflects the centroid of the triangle across its legs
	 * @param p1 first point
	 * @param p2 second point
	 * @param centroidX centroid's x coordinate
	 * @param centroidY centroid's y coordinate
	 * @return Reflected point with respective heading
	 */
	@Nullable
	private static Pose2D findReflectorsOfCentroid (@NonNull Pose2D p1, Pose2D p2, double centroidX, double centroidY) {
		if(p1.equals(p2)) // If the points are the same, return null so that we can later remove them
			return null;
		else if(p1.getX(DistanceUnit.INCH) == p2.getX(DistanceUnit.INCH))
			if(p1.getY(DistanceUnit.INCH) == p2.getY(DistanceUnit.INCH)) //If the X and Y are the same, only change the heading
				return new Pose2D(DistanceUnit.INCH, p1.getX(DistanceUnit.INCH), p1.getY(DistanceUnit.INCH), AngleUnit.DEGREES,(p1.getHeading(AngleUnit.RADIANS) + p2.getHeading(AngleUnit.RADIANS)) / 2);
			else //If they X is the same, find the Y midpoint and change the heading
				return new Pose2D(DistanceUnit.INCH, p1.getX(DistanceUnit.INCH), (p1.getY(DistanceUnit.INCH) + p2.getY(DistanceUnit.INCH) / 2), AngleUnit.DEGREES,(p1.getHeading(AngleUnit.RADIANS) + p2.getHeading(AngleUnit.RADIANS)) / 2);
		else if (p1.getY(DistanceUnit.INCH) == p2.getY(DistanceUnit.INCH)) //If only Y is change (if X is same as Y it is handled in above case) change X and heading
				return new Pose2D(DistanceUnit.INCH, (p1.getX(DistanceUnit.INCH) + p2.getX(DistanceUnit.INCH) / 2), p1.getY(DistanceUnit.INCH), AngleUnit.DEGREES,(p1.getHeading(AngleUnit.RADIANS) + p2.getHeading(AngleUnit.RADIANS)) / 2);
		else if (p1.getHeading(AngleUnit.RADIANS) == p2.getHeading(AngleUnit.RADIANS))//If heading is the same, just find midpoint of X and Y
			return new Pose2D(DistanceUnit.INCH, (p1.getX(DistanceUnit.INCH) + p2.getX(DistanceUnit.INCH) / 2), (p1.getY(DistanceUnit.INCH) + p2.getY(DistanceUnit.INCH) / 2), AngleUnit.DEGREES,p1.getHeading(AngleUnit.RADIANS));
		else { //Calculate actual centroid reflecting
			double slope = Math.abs(p1.getY(DistanceUnit.INCH) - p2.getY(DistanceUnit.INCH))/Math.abs(p1.getX(DistanceUnit.INCH) - p2.getX(DistanceUnit.INCH));
			double yIntercept = p1.getY(DistanceUnit.INCH) - (slope * p1.getX(DistanceUnit.INCH));
			double xF = (centroidX + slope * (centroidY - yIntercept)) / (1 + slope * slope);
			double yF = slope * xF + yIntercept;

			double xR = 2 * xF - centroidX;
			double yR = 2 * yF - centroidY;

			double endHeading = (p1.getHeading(AngleUnit.DEGREES) + p2.getHeading(AngleUnit.DEGREES)) / 2;

			return new Pose2D(DistanceUnit.INCH, xR, yR, AngleUnit.DEGREES, endHeading);
		}
	}

	/**
	 * General spline calculation method
	 * Finds centroid of triangle and maps to new ArrayList (toret)
	 * @param poses rough positions given by user
	 * @return calculated spline path
	 */
	@NonNull
	private static ArrayList<Pose2D> calculateSplines(@NonNull ArrayList<Pose2D> poses) {
		ArrayList<Pose2D> toret = new ArrayList<>();
		toret.add(poses.get(0));

		for(int i = 1; i < poses.size(); i++) {
			toret.removeIf(Objects::isNull);

			Pose2D first = poses.get(i-1);
			Pose2D second = poses.get(i);
			Pose2D third = poses.get(i+1);

			double centroidX = (first.getX(DistanceUnit.INCH) + second.getX(DistanceUnit.INCH) + third.getX(DistanceUnit.INCH)) / 3;
			double centroidY = (first.getY(DistanceUnit.INCH) + second.getY(DistanceUnit.INCH) + third.getY(DistanceUnit.INCH)) / 3;

			toret.add(findReflectorsOfCentroid(first, second, centroidX, centroidY));
			toret.add(findReflectorsOfCentroid(second, third, centroidX, centroidY));

			if(i == poses.size() - 2) {
				toret.add(findReflectorsOfCentroid(poses.get(i), poses.get(i+1), centroidX, centroidY));
				toret.add(poses.get(i+1));
				break;
			}
		}

		return toret;
	}

	/**
	 * "pulls" the point of a triangle closer to its hypotenuse, thus "smoothening" the path
	 * @param poses list of calculated splines
	 * @return smoothened spline path
	 */
	@NonNull
	private static ArrayList<Pose2D> smoothen (@NonNull ArrayList<Pose2D> poses) {
		ArrayList<Pose2D> toret = new ArrayList<>();
		toret.add(poses.get(0));
		for(int i = 1; i < poses.size(); i++) {
			if(i + 1 >= poses.size()) {
				toret.add(poses.get(i));
				break;
			}
			Pose2D first = poses.get(i - 1);
			Pose2D second = poses.get(i);
			Pose2D third = poses.get(i + 1);

			toret.add(new Pose2D(
					DistanceUnit.INCH,
					(first.getX(DistanceUnit.INCH) + second.getX(DistanceUnit.INCH) + third.getX(DistanceUnit.INCH)) / 3,
					(first.getY(DistanceUnit.INCH) + second.getY(DistanceUnit.INCH) + third.getY(DistanceUnit.INCH)) / 3,
					AngleUnit.DEGREES,
					(first.getHeading(AngleUnit.DEGREES) + second.getHeading(AngleUnit.DEGREES)) / 2
			));
		}
		return toret;
	}

	/**
	 * User-accessible method for generating a spline path
	 * @param poses List of rough positions given by user
	 * @param dFactor How many times to re-calculate the splines
	 * @param sFactor How many times to smoothen the splines
	 * @return huzz
	 */
	public static ArrayList<Pose2D> generateSpline(ArrayList<Pose2D> poses, int dFactor, int sFactor) {
		ArrayList<Pose2D> toret = new ArrayList<>(poses);
		for(int i = 0; i < dFactor; i++)
			toret = smoothen(poses);
		
		for(int i = 0; i < sFactor; i++)
			toret = calculateSplines(poses);
		return toret;
	}
}
