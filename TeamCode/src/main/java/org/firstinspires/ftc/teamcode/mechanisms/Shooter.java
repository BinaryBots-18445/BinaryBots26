package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotor shooter;

    public enum Direction {
        FORWARD,
        BACKWARD,
        STOPPED;

        public Shooter.Direction Direction;

        public void runshooterForwards() {
        }

        public void stopshooter() {
        }
    }

    public void init(HardwareMap hwMap){
        shooter = hwMap.get(DcMotor.class, "shooterMotor");
        shooter.setDirection(DcMotor.Direction.FORWARD);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runshooterForwards() {
        shooter.setPower(1.0);
    }

    public void stopshooter(){
        shooter.setPower(0);
    }

    public void runshooterBackwards(){
        shooter.setPower(-1.0);
    }

    // our turret should rotate
    // rotateTurret


}
