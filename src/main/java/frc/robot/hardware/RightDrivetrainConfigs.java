package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU;

public class RightDrivetrainConfigs {

    public static void configSide(TalonSRX master, VictorSPX slave, TalonSRX leftMaster, PigeonIMU pigeon) {
        TalonSRXConfiguration masterConfigs = new TalonSRXConfiguration();

        masterConfigs.slot0.kP = 2;
        masterConfigs.slot0.kD = 5;
        masterConfigs.slot0.kF = 0.37749077490774907749077490774908;
        masterConfigs.slot1.kP = 3;
        masterConfigs.slot1.kD = 50;

        masterConfigs.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorSum;
        masterConfigs.remoteFilter1.remoteSensorDeviceID = leftMaster.getDeviceID();
        masterConfigs.remoteFilter1.remoteSensorSource = RemoteSensorSource.TalonSRX_SelectedSensor;
        masterConfigs.sum0Term = FeedbackDevice.RemoteSensor1;
        masterConfigs.sum1Term = FeedbackDevice.QuadEncoder;
        masterConfigs.primaryPID.selectedFeedbackCoefficient = 0.5;

        masterConfigs.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
        masterConfigs.remoteFilter0.remoteSensorDeviceID = pigeon.getDeviceID();
        masterConfigs.remoteFilter0.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;

        masterConfigs.peakCurrentLimit = 40;
        masterConfigs.peakCurrentDuration = 0;
        masterConfigs.continuousCurrentLimit = 20;

        masterConfigs.openloopRamp = 0.1;

        masterConfigs.motionCruiseVelocity = 1800;
        masterConfigs.motionAcceleration = 3000;

        master.configAllSettings(masterConfigs);
        master.setInverted(true);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);
        master.selectProfileSlot(1, 1);
        master.enableCurrentLimit(true);

        VictorSPXConfiguration slaveConfigs = new VictorSPXConfiguration();

        slave.configAllSettings(slaveConfigs);

        slave.follow(master);
        slave.setInverted(InvertType.FollowMaster);
        slave.setNeutralMode(NeutralMode.Brake);
    }
}