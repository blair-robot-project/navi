package org.usfirst.frc.team449.robot.subsystem.interfaces.intake.intakeTwoSides;

import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.subsystem.interfaces.intake.SubsystemIntake;

public interface SubsystemIntakeTwoSides extends SubsystemIntake{

    void setLeftMode(@NotNull IntakeMode mode);

    void setRightMode(@NotNull IntakeMode mode);

    @Override
    default void setMode(@NotNull IntakeMode mode){
            setLeftMode(mode);
            setRightMode(mode);
        }
}
