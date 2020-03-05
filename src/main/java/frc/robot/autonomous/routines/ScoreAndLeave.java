package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.*;

public class ScoreAndLeave extends BaseActionRoutine {
    /* Distance, Heading, Time */
    @Override
    protected Action[] getRoutines() {
        return new Action[] {
            new MMDrive(-22000, 0, 3.0),        // Drive to goal
            new Score(1.0),                     // Shoot in goal
            new MMDrive(20000, 45, 2.5),        // Drive toward trench
            new MMDrive(18000, -47, 2.5),       // Line up to trench
            new MMDriveCollect(68000, 0, 6.0),  // Drive through trench & collect
        };
    }

    public String getName() { return "Score and Leave"; }
}