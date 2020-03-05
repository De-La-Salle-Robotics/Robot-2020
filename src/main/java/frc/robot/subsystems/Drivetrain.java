package frc.robot.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.utils.RobotState;

public class Drivetrain {
    private TalonSRX _leftMaster;
    private TalonSRX _rightMaster;
    private VictorSPX _leftSlave;
    private VictorSPX _rightSlave;
    private PigeonIMU _pidgey;

    public Drivetrain(TalonSRX leftMaster, TalonSRX rightMaster, VictorSPX leftSlave, VictorSPX rightSlave, PigeonIMU pigeon) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;
        _leftSlave = leftSlave;
        _rightSlave = rightSlave;
        _pidgey = pigeon;
    }

    public void operate(RobotState joysticks) {
        /* Check if we should reset sensors */
        if(joysticks.clearSensors) {
            _leftMaster.getSensorCollection().setQuadraturePosition(0, 0);
            _rightMaster.getSensorCollection().setQuadraturePosition(0, 0);
            _pidgey.setYaw(0);
        }

        switch(joysticks.driveTrainState.state) {
            case SlowDrive: // Same as percent output now
            case PercentOut:
                _leftMaster.set(ControlMode.PercentOutput, joysticks.driveTrainState.leftSide);
                _rightMaster.set(ControlMode.PercentOutput, joysticks.driveTrainState.rightSide);

                // if(_leftMaster.getLastError() == ErrorCode.OK) {
                //     _leftSlave.follow(_leftMaster);
                // } else {
                //     _leftSlave.set(ControlMode.PercentOutput, joysticks.driveTrainState.leftSide * 0.5); // Give ourselves half power to protect ourselves
                // }
                // if(_rightMaster.getLastError() == ErrorCode.OK) {
                //     _rightSlave.follow(_rightMaster);
                // } else {
                //     _rightSlave.set(ControlMode.PercentOutput, joysticks.driveTrainState.rightSide * 0.5); // Give ourselves half power to protect ourselves
                // }

                break;
            case Position:
                break;
            case MotionMagic: // Only in auton
                _leftMaster.follow(_rightMaster, FollowerType.AuxOutput1);
                _rightMaster.set(ControlMode.MotionMagic, joysticks.driveTrainState.rightSide,
                                DemandType.AuxPID, joysticks.driveTrainState.leftSide);
                break;
        }
    }
}