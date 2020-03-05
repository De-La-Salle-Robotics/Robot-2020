package frc.robot;

import frc.robot.autonomous.AutonomousManager;
import frc.robot.dashboard.MainDashboard;
import frc.robot.hardware.RobotMap;
import frc.robot.subsystems.*;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals; 

public class FortKnox {
    public enum FortKnoxState {
        Disabled,
        Teleoperated,
        Autonomous,
        Test,
    };
    private FortKnoxState _currentState;

    private Drivetrain _drivetrain;
    private Belt _belt;
    private Shooter _shooter;
    private Intake _intake;
    private Climb _climb;
    private Arm _arm;
    private Winch _winch;

    private AutonomousManager _autoManager;

    private MainDashboard _dashboard;

    private RobotState _robot;
    private SensorVals _sensors;
    public FortKnox() {
        RobotMap.initialize();

        _drivetrain = new Drivetrain(RobotMap.leftMaster, RobotMap.rightMaster, RobotMap.leftSlave, RobotMap.rightSlave, RobotMap.pidgey);
        _belt = new Belt(RobotMap.belt);
        _shooter = new Shooter(RobotMap.shooter);
        _intake = new Intake(RobotMap.intake);
        _climb = new Climb(RobotMap.climb);
        _arm = new Arm(RobotMap.arm);
        _winch = new Winch(RobotMap.winch);
    
        _robot = new RobotState(RobotMap.driverJoystick, RobotMap.operatorJoystick);
        _sensors = new SensorVals(RobotMap.leftMaster, RobotMap.rightMaster, RobotMap.pidgey);
        
        _autoManager = new AutonomousManager();

        _dashboard = new MainDashboard(_sensors, _robot, _autoManager);

        _currentState = FortKnoxState.Disabled;
        _dashboard.start();
    }
    public void periodicTasks(){
        _robot.getJoystickValues();
        _sensors.getSensorValues();

        switch(_currentState)
        {
            case Autonomous:
                /* Run autonomous stuff here */
                /* _robot state will be overwritten by auto */
                _autoManager.runRoutine(_sensors, _robot);
                /* Fall-through */
            case Teleoperated:
            case Disabled:
                /* Listen to joysticks and run our mechanisms */
                _belt.beltControl(_robot);
                _intake.intakeControl(_robot);
                _climb.climbControl(_robot);
                _shooter.shooterControl(_robot);
                _drivetrain.operate(_robot);
                _arm.armControl(_robot);
                _winch.HookControl(_robot);
                _autoManager.updateRoutines(_robot);
                break;
            case Test:
                /* Test does its own stuff */
                break;
        }
        if(_currentState == FortKnoxState.Disabled) {
            _autoManager.resetAuto();
        }
    }

    public void changeState(FortKnoxState newState) {
        _currentState = newState;
    }
}

