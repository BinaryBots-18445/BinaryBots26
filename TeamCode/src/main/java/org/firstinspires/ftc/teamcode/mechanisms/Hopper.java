package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hopper {
    private DcMotor hopperMotor;

    public enum Direction {
        FORWARD,
        BACKWARD,
        STOPPED
    }

    public void init(HardwareMap hwMap){
       hopperMotor = hwMap.get(DcMotor.class, "hopperMotor");
       hopperMotor.setDirection(DcMotor.Direction.FORWARD);
        hopperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runhopperForwards(){
        hopperMotor.setPower(1.0);
    }

    public void stophopper() { hopperMotor.setPower(0);
    }

    public void runhopperBackwards(){
        hopperMotor.setPower(-1.0);
    }
}
