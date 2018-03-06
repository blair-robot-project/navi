package org.usfirst.frc.team449.robot.other;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * A waypoint to hit during a motion profile.
 */
public class Waypoint {

    /**
     * The X position to hit, in feet.
     */
    private double x;

    /**
     * The Y position to hit, in feet.
     */
    private double y;

    /**
     * The angle, in degrees, for the robot to be at when it arrives at (x, y).
     */
    private double theta;

    /**
     * Default constructor.
     *
     * @param x The X position to hit, in feet.
     * @param y The Y position to hit, in feet.
     * @param theta The angle, in degrees, for the robot to be at when it arrives at (x, y).
     */
    @JsonCreator
    public Waypoint(@JsonProperty(required = true) double x,
                    @JsonProperty(required = true) double y,
                    @JsonProperty(required = true) double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    /**
     * @return The X position to hit, in feet.
     */
    public double getX() {
        return x;
    }

    /**
     * @return The Y position to hit, in feet.
     */
    public double getY() {
        return y;
    }

    /**
     * @return The angle, in radians, for the robot to be at when it arrives at (x, y).
     */
    public double getThetaRadians() {
        return Math.toRadians(theta);
    }

    /**
     * @return The angle, in degrees, for the robot to be at when it arrives at (x, y).
     */
    public double getThetaDegrees() {
        return theta;
    }

    /**
     * @param x The X position to hit, in feet.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @param y The X position to hit, in feet.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @param theta The angle, in radians, for the robot to be at when it arrives at (x, y).
     */
    public void setThetaRadians(double theta) {
        this.theta = Math.toDegrees(theta);
    }

    /**
     * @param theta The angle, in degrees, for the robot to be at when it arrives at (x, y).
     */
    public void setThetaDegrees(double theta) {
        this.theta = theta;
    }
}
