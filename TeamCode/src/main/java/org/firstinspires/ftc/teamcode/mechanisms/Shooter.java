package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotor shooter;

    public enum Direction {
        FORWARD,
        BACKWARD,
        STOPPED
    }

    public void init(HardwareMap hwMap){
        shooter = hwMap.get(DcMotor.class, "shooterMotor");
        shooter.setDirection(DcMotor.Direction.FORWARD);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stopshooter(){
        shooter.setPower(0);
    }

    public void runshooter(double power) {
        shooter.setPower(power);
    }

    // our turret should rotate
    // rotateTurret

}
