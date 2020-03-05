package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;

public class RobotState{
    public enum PCState {
        WaitUp,
        WaitDown,
        Suck,
        Blow,
        Shoot,
    }
    public enum HangState {
        Deploy,
        Retract,
        Hang,
        Nothing,
    }
    public enum AutonState {
        NextAuto,
        PreviousAuto,
        AutoChanged,
        ButtonReleased,
    }
    public enum DriveTrainState {
        PercentOut,
        Position,
        MotionMagic,
        SlowDrive,
    }
    public class DTStruct {
        public DriveTrainState state;
        public double leftSide;
        public double rightSide;

        @Override
        public String toString() {
            return state.toString() + "  " + leftSide + " " + rightSide;
        }

        public void set(DriveTrainState state, double left, double right) {
            this.state = state;
            leftSide = left;
            rightSide = right;
        }
    };
    
    public DTStruct driveTrainState;
    public PCState powerCellState;
    public HangState hanger;
    public AutonState routine;

    public boolean clearSensors;
    
    private Joystick _driver;
    private Joystick _operator;
    public RobotState (Joystick driver, Joystick operator) {
        _driver = driver;
        _operator = operator;

        /* Initialize states */
        driveTrainState = new DTStruct();
        powerCellState = PCState.WaitUp;
        hanger = HangState.Nothing;
        routine = AutonState.AutoChanged;

        clearSensors = false;
    }
    public void getJoystickValues() {
        /* Drive base */
        double throt = -_driver.getRawAxis(1);
        double wheel = _driver.getRawAxis(4) * 0.5; /* Throttle is negated */
        driveTrainState.set(DriveTrainState.PercentOut, throt + wheel, throt - wheel);

        if(_driver.getRawButton(6)) {
            /* Right Bumper pressed, slow drive */
            throt *= 0.3; // Throttle down to 0.3
            wheel *= 0.6; // Wheel down to 0.6
            driveTrainState.set(DriveTrainState.SlowDrive, throt + wheel, throt - wheel);
        }
        
        /* If we press arm down, go down */
        if(_operator.getRawButton(4)) {
            System.out.println("Pressed Wait Down");
            powerCellState = PCState.WaitDown;
        /* If we press suck, go into suck */
        } else if (_operator.getRawButton(7)) {
            powerCellState = PCState.Suck;
        /* If we press blow, go into blow */
        } else if (_operator.getRawButton(6)) {
            powerCellState = PCState.Blow;
        /* If we press shoot, go into shoot */
        } else if (_operator.getRawButton(8)) {
            powerCellState = PCState.Shoot;
        /* If we press arm up, go into waitup */
        } else if (_operator.getRawButton(2)) {
            powerCellState = PCState.WaitUp;
        /* If we didn't press anything, go into wait based on last state */
        } else {
            /* If we were last sucking or waiting down, stay down */
            if(powerCellState == PCState.Suck || 
               powerCellState == PCState.WaitDown) {
                powerCellState = PCState.WaitDown;
            /* Otherwise keep the arm up */
            } else {
                powerCellState = PCState.WaitUp;
            }
        }

        /* If we press deploy, start to deploy */
        if(_driver.getRawButton(1)) {
            hanger = HangState.Deploy;
        /* If we press retract, go into retract */
        } else if(_driver.getRawButton(2)) {
            hanger = HangState.Retract;
        /* If we press hang, go into hang */
        } else if(_driver.getRawButton(3)) {
            hanger = HangState.Hang;
        /* Otherwise we do nothing */
        } else {
            hanger = HangState.Nothing;
        }

        if(routine != AutonState.AutoChanged) {
            if(_driver.getPOV() == 90) {
                routine = AutonState.NextAuto;
            } else if (_driver.getPOV() == 270) {
                routine = AutonState.PreviousAuto;
            }
        } else if (_driver.getPOV() == -1) {
            routine = AutonState.ButtonReleased;
        }

        if(_driver.getPOV() == 180) {
            clearSensors = true;
        } else {
            clearSensors = false;
        }
    }
} 