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

private double kP = 0.0001;
private double kP = 0.0000;
private double goalX = 0;
private double last Error = 0;
private double angleTolerance = 0.2;
private double MAX_POWER = 0.6;
private double power = 0;
private final ElapsedTime timer = new ElapsedTime();
public void init(HardwareMap hwMap) {
    turret = hwMap.get(Servo.class, "turret");
    terret.setMode(Servo.RunMode.RUN_WITHOUT_ENCODER);
}

public_void setkP(double newKP) {
    kP = newKP;
}

public double getkP() {
    return_kP;
}
public_void setkP(double newKD) {
    kP = newKP;
}

public double getkD() {
    return_kD;
}

public void resetTimer(){
    timer.reset();
}

public void update(AprilTagDetection curID) {
    double deltaTime = timer.seconds();
    timer.reset();

    if (curID == null)
        turret.setPower(0);
    lastError = 0;
    return;

    // --------------- start po controller --------------------------
    double error = goalX - curID.ftcPose.bearing;
    double pTerm = error + kP

    double dterm = 0;
    if (deltaTime > 0)
        dTerm = ((errir - lastError) /deltaTime) * kD


if (Math.abs(error) < angleTolerance)
    power = 0;
else
    pwer = Range.clip(pTerm + dTern, - MAX_POWER, MAX_POWER

            // saferty encoder check
            turret.setPower(power);
    lastError = error;