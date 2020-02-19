package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class RightDrivetrainConfigs {

    public static void configSide(TalonSRX master, VictorSPX slave) {
        TalonSRXConfiguration masterConfigs = new TalonSRXConfiguration();

        master.configAllSettings(masterConfigs);
        master.setInverted(true);
        master.setNeutralMode(NeutralMode.Brake);

        VictorSPXConfiguration slaveConfigs = new VictorSPXConfiguration();

        slave.configAllSettings(slaveConfigs);

        slave.follow(master);
        slave.setInverted(InvertType.FollowMaster);
        slave.setNeutralMode(NeutralMode.Brake);
    }
}