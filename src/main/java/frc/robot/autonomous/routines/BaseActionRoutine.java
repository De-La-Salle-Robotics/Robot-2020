package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.Action;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public abstract class BaseActionRoutine implements IRoutine {

    public abstract String getName();

    protected abstract Action[] getRoutines();

    public void end(RobotState robot) {
        /* Don't end in any state other than what driver wants */
    }

    public BaseActionRoutine() {
        routines = getRoutines();
    }
    
    private Action[] routines;

    private int _maxNum;
    private int _currentAction;

    private boolean _finished;

    private String _telemetry;

    public void initialize() {
        /* Initialize variables */
        _finished = false;
        _telemetry = "";
        _maxNum = routines.length;
        _currentAction = 0;
    }
    public void start(SensorVals sensors) {
        /* Reset variables to initial settings */
        _finished = false;
        routines[0].initialize(sensors.totalRobotDist, sensors.rawHeading);   
    }
    public void onLoop(SensorVals sensors, RobotState robot) {
        routines[_currentAction].run(robot, sensors);
        if(routines[_currentAction].finished()) {
            _currentAction++;
            if(_currentAction == _maxNum) {
                _currentAction--;
                _finished = true;
            } else {
                routines[_currentAction].initialize(routines[_currentAction - 1]);
            }
        }
        _telemetry = _currentAction + ": " + routines[_currentAction].getName();
    }
    public boolean finished() {
        return _finished;
    }

    public String telemetry() {
        return _telemetry;
    }
}