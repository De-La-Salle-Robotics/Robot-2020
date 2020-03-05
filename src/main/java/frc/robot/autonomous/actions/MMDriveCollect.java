package frc.robot.autonomous.actions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;
import frc.robot.utils.RobotState.DriveTrainState;
import frc.robot.utils.RobotState.PCState;

public class MMDriveCollect implements Action {
    private double _forward;
    private double _heading;
    private double _maxTime;
    private double _startDist;
    private double _startHead;

    private Timer _timer;

    public MMDriveCollect(double forward, double heading, double time) {
        _forward = forward;
        _heading = heading * (8192.0 / 360.0);
        _maxTime = time;

        _timer = new Timer();
    }

    public String getName() { return "MM Drive & Collect"; }

    public double distElapsed() {
        return _startDist + _forward;
    }
    public double headingElapsed() {
        return _startHead + _heading;
    }

    public void initialize(double startForward, double startHeading) {
        _startDist = startForward;
        _startHead = startHeading;

        _timer.start();
    }

    public void initialize(Action prevDrive) {
        initialize(prevDrive.distElapsed(), prevDrive.headingElapsed());
    }

    public void run(RobotState state, SensorVals sensors) {
        state.driveTrainState.set(DriveTrainState.MotionMagic, _startHead + _heading, _startDist + _forward);
        state.powerCellState = PCState.Suck;
    }

    public boolean finished() {
        return _timer.get() >= _maxTime;
    }
}