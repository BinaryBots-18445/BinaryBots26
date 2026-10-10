package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Turret {
    private DcMotor intakeMotor;

    public enum Direction {
        FORWARD,
        BACKWARD,
        STOPPED
    }

    public void init(HardwareMap hwMap){
        intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runIntakeForwards(){
        intakeMotor.setPower(1.0);
    }

    public void stopIntake(){
        intakeMotor.setPower(0);
    }

    public void runIntakeBackwards(){
        intakeMotor.setPower(-1.0);
    }

    // our turret should rotate
    // rotateTurret


}
