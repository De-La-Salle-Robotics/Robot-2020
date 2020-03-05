package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.*;

public class CenterScore extends BaseActionRoutine {
    /* Distance, Heading, Time */
    @Override
    protected Action[] getRoutines() {
        return new Action[] {
            new MMDrive(-11000, -60 , 2.0), // Drive towards Goal
            new MMDrive(-16500, 60 , 3.0), // Line up to goal
            new Score(1), // Shoot Powercells
        };
    }
    @Override
    public String getName() { return "Center Score"; }
}