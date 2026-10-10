package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Turret {
    private DcMotor turretShooter;
    private Servo turretAngle;
    private Servo turretDirection;

    public enum Direction {
        FORWARD,
        BACKWARD,
        STOPPED
    }

    public void init(HardwareMap hwMap){
        turretShooter = hwMap.get(DcMotor.class, "turretShooter");
        turretShooter.setDirection(DcMotor.Direction.FORWARD);
        turretShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        turretAngle = hwMap.get(Servo.class, "turretAngle");

        turretDirection = hwMap.get(Servo.class, "turretDirection")

    }
    public void setAnglePos(double angle) {
        turretDirection.setPosition(angle);
    }

    public void setDirectionPos(double angle)  {
        turretAngle.setPosition(angle);
    }

    public void runTurretForwards(){
        Turret.setPower(1.0);
    }

    public void stopIntake(){
        Turret.setPower(0);
    }

    public void runTurretBackwards(){
       Turret.setPower(-1.0);
    }
}
