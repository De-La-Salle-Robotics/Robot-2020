package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class LeftDrivetrainConfigs {

    public static void configSide(TalonSRX master, VictorSPX slave) {
        TalonSRXConfiguration masterConfigs = new TalonSRXConfiguration();

        masterConfigs.openloopRamp = 0.1;

        masterConfigs.peakCurrentLimit = 40;
        masterConfigs.peakCurrentDuration = 0;
        masterConfigs.continuousCurrentLimit = 20;

        master.configAllSettings(masterConfigs);
        master.setInverted(false);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);
        master.enableCurrentLimit(true);

        VictorSPXConfiguration slaveConfigs = new VictorSPXConfiguration();

        slave.configAllSettings(slaveConfigs);

        slave.follow(master);
        slave.setInverted(InvertType.FollowMaster);
        slave.setNeutralMode(NeutralMode.Brake);
    }
}