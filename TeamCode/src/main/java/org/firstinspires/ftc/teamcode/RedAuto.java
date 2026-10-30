package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareControl;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

@Autonomous (name="RedAuto", group="Robot")
public class RedAuto extends LinearOpMode {
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
                shooter.runshooter(1200);
                sleep(5500);

                //   for (int i = 0; i < 4; i++) {

                    hopper.runhopperForwards();
                    for (int i = 0; i < 5; i++) {
                        intake.runIntakeForwards();
                       sleep(2000);
                       intake.stopIntake();
                    }
          //          hopper.stophopper();
        //        }

           }
            intake.stopIntake();
            hopper.stophopper();
            shooter.stopshooter();

            hc.mechanumMoveRobotBackwardsInches(-100);
            hc.mechanumStrafeLeftInches(50);

        }
       //run_once = false;
   }
}
