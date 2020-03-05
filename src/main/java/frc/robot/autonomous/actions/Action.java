package frc.robot.autonomous.actions;

import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public interface Action {
    public double distElapsed();
    public double headingElapsed();
    public void initialize(double forward, double heading);
    public void initialize(Action prevAction);
    public void run(RobotState state, SensorVals sensors);
    public boolean finished();
    public String getName();
}