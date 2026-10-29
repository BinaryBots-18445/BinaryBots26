package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareControl;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

@Autonomous (name="Auto", group="Robot")
public class Auto extends LinearOpMode {
    HardwareControl hc;
    BBMecanumDrive drive = new BBMecanumDrive();
    Intake intake = new Intake();
    AprilTagWebcam aTag = new AprilTagWebcam();
    Hopper hopper = new Hopper();
    Shooter shooter = new Shooter();

    public void runOpMode() {
        hc = new HardwareControl(this);

        boolean run_once = true;
        while(opModeIsActive()) {
            telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
            telemetry.update();
            if(run_once)
            {
                for (int i = 0; i < 4; i++) {

                   shooter.runshooter(1200);
                    sleep(5500);
                    hopper.runhopperForwards();
                    sleep(5000);
                    hopper.stophopper();

                }

           }
            shooter.stopshooter();

            hc.mechanumMoveRobotForwardInches(60);

        }
       //run_once = false;
   }
}
