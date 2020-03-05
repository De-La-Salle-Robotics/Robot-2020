package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.*;

public class FiveBall extends BaseActionRoutine {
    /* Distance, Heading, Time */
    @Override
    protected Action[] getRoutines() {
        return new Action[] {
            new MMDriveCollect(28000, 0, 4.0),
            new MMDrive(-32000, 36, 4.0),
            new MMDrive(-20000, -36, 3.0),
            new Score(4.0),
        };
    }

    public String getName() { return "Five Ball"; }
}