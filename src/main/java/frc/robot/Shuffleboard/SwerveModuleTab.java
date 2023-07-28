package frc.robot.Shuffleboard;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Subsystems.SwerveModule;

public class SwerveModuleTab extends ShuffleboardTabBase {

    private final SwerveModule module;
    private final BooleanPublisher isActive;
    private final DoublePublisher moduleAnglePub;
    


    public SwerveModuleTab(SwerveModule module){
        this.module = module;

        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        NetworkTable networkTable = inst.getTable("Shuffleboard/swerveDrive #" + module.getModuleNumber());

        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("swerveDrive #" + module.getModuleNumber());


        isActive = networkTable.getBooleanTopic("Is Module Active:").publish();

        moduleAnglePub = networkTable.getDoubleTopic("Module Angle").publish();
    }
    @Override
    public void update() {
       isActive.set(module.isActive());
       moduleAnglePub.set(module.getModuleAngle());
    }
    
}
