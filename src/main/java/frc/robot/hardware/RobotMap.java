package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.CANifier;

public class RobotMap {
    /* Drivetrain */
    public static TalonSRX leftMaster;
    public static VictorSPX leftSlave;
    public static TalonSRX rightMaster;
    public static VictorSPX rightSlave;

    /* Arm and Collector */
    public static DoubleSolenoid arm;
    public static BaseMotorController intake;
    /* Conveyor Belt */
    public static BaseMotorController belt;
    public static BaseMotorController shooter;
    /* Climber */
    public static VictorSPX climb;
    public static VictorSPX winch;

    public static Joystick driverJoystick;
    public static Joystick operatorJoystick;

    public static final CANifier can1 = new CANifier(0);

    public static void initialize() {
        leftMaster = new TalonSRX(1);
        leftSlave = new VictorSPX(1);
        rightMaster = new TalonSRX(2);
        rightSlave = new VictorSPX(2);

        arm = new DoubleSolenoid(0, 1);
        intake = new VictorSPX(6);

        belt = new VictorSPX(4);
        shooter = new VictorSPX(5);

        belt.setInverted(true);
        shooter.setInverted(true);

        climb = new VictorSPX(7);
        winch = new VictorSPX(3);

        driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(1);

        configControllers();
    }

    private static void configControllers() {
        LeftDrivetrainConfigs.configSide(leftMaster, leftSlave);
        RightDrivetrainConfigs.configSide(rightMaster, rightSlave);
        HangerConfigs.configHanger(winch, climb);
    }
}