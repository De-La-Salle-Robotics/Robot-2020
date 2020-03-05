package frc.robot.autonomous;

import frc.robot.autonomous.routines.*;
import frc.robot.utils.*;
import frc.robot.utils.RobotState.AutonState;


public class AutonomousManager {
    private final int TOTAL_ROUTINES = 6;
    private void selectRoutine(int val) {
        _routineVal = val;
        switch(val) {
            case 0: _currentRoutine = new DriveForward(); break;
            case 1: _currentRoutine = new DriveForwardScore(); break;
            case 2: _currentRoutine = new ScoreAndLeave(); break;
            case 3: _currentRoutine = new CenterScore(); break;
            case 4: _currentRoutine = new FiveBall(); break;
            case 5: _currentRoutine = new FourBall(); break; 
        }
        _currentRoutine.initialize();
    }

    private IRoutine _currentRoutine;

    private int _routineVal;
    private boolean _startedRoutine;
    private boolean _finished;

    private void nextRoutine() {
        _routineVal++;
        if(_routineVal >= TOTAL_ROUTINES) _routineVal = 0;
    }
    private void previousRoutine() {
        _routineVal--;
        if(_routineVal < 0) _routineVal = TOTAL_ROUTINES - 1;
    }

    public AutonomousManager() {
        _startedRoutine = false;
        _finished = false;
    }

    public void resetAuto() {
        _startedRoutine = false;
        _finished = false;
    }

    public void updateRoutines(RobotState joysticks) {
        boolean updateRoutine = joysticks.routine == AutonState.NextAuto ||
                                joysticks.routine == AutonState.PreviousAuto ||
                                _currentRoutine == null;

        if(joysticks.routine == AutonState.NextAuto) {
            nextRoutine();
            joysticks.routine = AutonState.AutoChanged;
        } 
        if(joysticks.routine == AutonState.PreviousAuto) { 
            previousRoutine();
            joysticks.routine = AutonState.AutoChanged;
        }

        if(updateRoutine) {
            selectRoutine(_routineVal);
        }
    }

    public void runRoutine(SensorVals sensors, RobotState robot) {
        if(_finished == true) {
            /* Do nothing */
        }else if(_startedRoutine == false) {
            /* Start the routine */
            _currentRoutine.start(sensors);
            _startedRoutine = true;
        } else {
            /* Update the routine */
            _currentRoutine.onLoop(sensors, robot);
        }
        if(_currentRoutine.finished()) {
            _finished = true;
            _currentRoutine.end(robot);
        }
    }

    public String telemetry() {
        return _currentRoutine.telemetry();
    }

    public String getAutoName() {
        return _currentRoutine.getName();
    }
}