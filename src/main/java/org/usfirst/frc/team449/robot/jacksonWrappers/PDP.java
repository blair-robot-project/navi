package org.usfirst.frc.team449.robot.jacksonWrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.components.RunningLinRegComponent;
import org.usfirst.frc.team449.robot.generalInterfaces.loggable.Loggable;
import org.usfirst.frc.team449.robot.generalInterfaces.updatable.Updatable;

/**
 * An object representing the {@link PowerDistributionPanel} that logs power, current, and resistance.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class PDP implements Loggable, Updatable {

    /**
     * The WPILib PDP this is a wrapper on.
     */
    private final PowerDistributionPanel PDP;

    /**
     * The component for doing linear regression to find the resistance.
     */
    private final RunningLinRegComponent voltagePerCurrentLinReg;

    /**
     * The cached values from the PDP object this wraps.
     */
    private double voltage, totalCurrent;

    /**
     * Default constructor.
     *
     * @param canID                   CAN ID of the PDP. Defaults to 0.
     * @param voltagePerCurrentLinReg The component for doing linear regression to find the resistance.
     */
    @JsonCreator
    public PDP(int canID,
               @NotNull @JsonProperty(required = true) RunningLinRegComponent voltagePerCurrentLinReg) {
        this.PDP = new PowerDistributionPanel(canID);
        this.voltagePerCurrentLinReg = voltagePerCurrentLinReg;
    }

    /**
     * Get the resistance of the wires leading to the PDP.
     *
     * @return Resistance in ohms.
     */
    public double getResistance() {
        return -voltagePerCurrentLinReg.getSlope();
    }

    /**
     * Get the voltage at the PDP when there's no load on the battery.
     *
     * @return Voltage in volts when there's 0 amps of current draw
     */
    public double getUnloadedVoltage() {
        return voltagePerCurrentLinReg.getIntercept();
    }

    /**
     * Query the input voltage of the PDP.
     *
     * @return The voltage of the PDP in volts
     */
    public double getVoltage() {
        return voltage;
    }

    /**
     * Query the current of all monitored PDP channels (0-15).
     *
     * @return The current of all the channels in Amperes
     */
    public double getTotalCurrent() {
        return totalCurrent;
    }

    /**
     * Get the headers for the data this subsystem logs every loop.
     *
     * @return An N-length array of String labels for data, where N is the length of the Object[] returned by getData().
     */
    @NotNull
    @Override
    public String[] getHeader() {
        return new String[]{
                "current",
                "voltage",
                "resistance",
                "unloaded_voltage"
        };
    }

    /**
     * Get the data this subsystem logs every loop.
     *
     * @return An N-length array of Objects, where N is the number of labels given by getHeader.
     */
    @NotNull
    @Override
    public Object[] getData() {
        return new Object[]{
                getTotalCurrent(),
                getVoltage(),
                getResistance(),
                getUnloadedVoltage()
        };
    }

    /**
     * Get the name of this object.
     *
     * @return A string that will identify this object in the log file.
     */
    @NotNull
    @Override
    public String getLogName() {
        return "PDP";
    }

    /**
     * Updates all cached values with current ones.
     */
    @Override
    public void update() {
        this.totalCurrent = PDP.getTotalCurrent();
        this.voltage = PDP.getVoltage();
        //Calculate running linear regression
        voltagePerCurrentLinReg.addPoint(totalCurrent, voltage);
    }
}
