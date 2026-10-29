package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

/*
 * This is the driver oriented op mode for BB 2026 season using mecanum drive.
 */
@TeleOp
public class DriverOrientedOp extends OpMode {
    BBMecanumDrive drive = new BBMecanumDrive();
    Intake intake = new Intake();
    AprilTagWebcam aTag = new AprilTagWebcam();
    Hopper hopper = new Hopper();
    Shooter shooter = new Shooter();

    // Drivetrain variables
    double forward;
    double strafe;
    double rotate;

    boolean slowMode = false;

    // Intake variables
    boolean intakeForward = false;
    boolean intakeBackward = false;
    Intake.Direction intakeDirection = Intake.Direction.STOPPED;
    private boolean prevIntakeForward = false;
    private boolean prevIntakeBackward = false;

    // Hopper variables
    boolean hopperForward = false;
    boolean hopperBackward = false;
    Hopper.Direction hopperDirection = Hopper.Direction.STOPPED;
    private boolean prevHopperForward = false;
    private boolean prevHopperBackward = false;

    // Shooter variables
    boolean shooterEnabled = false;
    double shooterPower = 1200;
    boolean shooterStart = false;
    Shooter.Direction shooterDirection = Shooter.Direction.STOPPED;
    private boolean prevShooterEnabled = false;
    boolean bpressed = false;
    boolean apressed = false;
    boolean aPressedLast = gamepad1.a;

    @Override
    public void init() {
        drive.init(hardwareMap, this.telemetry);
        intake.init(hardwareMap);
        hopper.init(hardwareMap);
        shooter.init(hardwareMap); //added shooter init
       // aTag.init(hardwareMap, this.telemetry);

        telemetry.addData("Initialization", "Init complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Drivetrain inputs
        //CHECK: If robot is not moving correctly, check the stick inputs and scalar values
        //CHECK: all names in hardware map
        //Pollen pusher servo is not defined as a 90 degree, redefine it correctly in the expansion hub hardware map.
        forward = gamepad1.left_stick_y * -1; // inverted y input
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.a && !aPressedLast) {
            slowMode = !slowMode; //toggle
        }
        aPressedLast = gamepad1.a;


        double speedMult = slowMode ? 0.5 : 1.0;

        drive.drive(forward, strafe, rotate, speedMult);

        // Gamepad2 inputs
        intakeForward = gamepad2.right_bumper;
        intakeBackward = gamepad2.right_trigger_pressed;

        hopperForward = gamepad2.left_trigger_pressed;
        hopperBackward = gamepad2.left_bumper;

        shooterEnabled = gamepad2.y;
        //shooterPower = gamepad2.right_stick_y;

        // INTAKE CONTROLS (Toggle with debouncing)
        if (intakeForward && !prevIntakeForward) {
            if (intakeDirection == Intake.Direction.FORWARD) {
                intake.stopIntake();
                intakeDirection = Intake.Direction.STOPPED;
            } else {
                intake.runIntakeForwards();
                intakeDirection = Intake.Direction.FORWARD;
            }
        } else if (intakeBackward && !prevIntakeBackward) {
            if (intakeDirection == Intake.Direction.BACKWARD) {
                intake.stopIntake();
                intakeDirection = Intake.Direction.STOPPED;
            } else {
                intake.runIntakeBackwards();
                intakeDirection = Intake.Direction.BACKWARD;
            }
        }
        prevIntakeForward = intakeForward;
        prevIntakeBackward = intakeBackward;

        //Changed shooter to be powered on a number value instead of bool
        // SHOOTER CONTROLS (Toggle with debouncing)
        if (shooterEnabled && !prevShooterEnabled) {
             shooterStart = !shooterStart;
        }
        prevShooterEnabled = shooterEnabled;

        if (gamepad2.b && !bpressed) {
            shooterPower += 100;
        }
        bpressed = gamepad2.b;

        if (gamepad2.a && !apressed) {
            shooterPower -= 100;
        }
        apressed = gamepad2.a;
        // Apply power
        if (shooterStart) {
            shooter.runshooter(shooterPower);
            shooterDirection = Shooter.Direction.FORWARD;
        } else {
            shooter.stopshooter();
            shooterDirection = Shooter.Direction.STOPPED;
        }

        // HOPPER CONTROLS (Toggle with debouncing)
        if (hopperForward && !prevHopperForward) {
            if (hopperDirection == Hopper.Direction.FORWARD) {
                hopper.stophopper();
                hopperDirection = Hopper.Direction.STOPPED;
            } else {
                hopper.runhopperForwards();
                hopperDirection = Hopper.Direction.FORWARD;
            }
        } else if (hopperBackward && !prevHopperBackward) {
            if (hopperDirection == Hopper.Direction.BACKWARD) {
                hopper.stophopper();
                hopperDirection = Hopper.Direction.STOPPED;
            } else {
                hopper.runhopperBackwards();
                hopperDirection = Hopper.Direction.BACKWARD;
            }
        }
        prevHopperForward = hopperForward;
        prevHopperBackward = hopperBackward;

        telemetry.addData("Intake State", intakeDirection);
        telemetry.addData("Shooter State", shooterDirection);
        telemetry.addData("Hopper State", hopperDirection);
        telemetry.addData("Shooter Power", shooterPower);
        telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
        telemetry.update();
    }
}