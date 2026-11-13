package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareControl;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

@Autonomous (name="RedAuto", group="Robot")
public class Auto extends LinearOpMode {
    HardwareControl hc;
    BBMecanumDrive drive = new BBMecanumDrive();
    Intake intake = new Intake();
    AprilTagWebcam aTag = new AprilTagWebcam();
    Hopper hopper = new Hopper();
    Shooter shooter = new Shooter();

    public void runOpMode() {
        hc = new HardwareControl(this);
        ElapsedTime runtime = new ElapsedTime();

        shooter.init(hardwareMap);
        hopper.init(hardwareMap);
        intake.init(hardwareMap);
        drive.init(hardwareMap, telemetry);

        waitForStart();


        boolean run_once = true;
        runtime.reset();
        while(opModeIsActive()) {

            if(run_once)
            {
                shooter.runshooter(1200);

                  for (int i = 0; i < 4; i++) {
                      for (int x = 0; x < 20; x++) {

                          // Check if speed is in the target range
                          while (shooter.motor.getVelocity() <= 1190 || shooter.motor.getVelocity() >= 1210) {
                              // For debugging purposes
                              telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                              telemetry.addData("Time Passed", runtime.seconds());
                              telemetry.addData("Checks", x);
                              telemetry.update();
                              sleep(10); // Wait

                              if (runtime.seconds() > 20) {break;}
                          }

                          sleep(1);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.addData("Time Passed", runtime.seconds());
                          telemetry.addData("Checks", x);
                          telemetry.update();
                          if (runtime.seconds() > 20) {break;}
                      }
                      intake.runIntakeForwards();
                      hopper.runhopperBackwards();
                      for (int x = 0; x < 30; x++) { // sleep 0.3 sec and measure
                          sleep(10);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.addData("Time Passed", runtime.seconds());
                          telemetry.update();
                          if (runtime.seconds() > 20) {break;}
                      }
                      hopper.stophopper();
                      if (runtime.seconds() > 20) {break;}
                     /* for (int x = 0; x < 50; x++) { // sleep 0.25 sec and measure
                          sleep(5);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.update();
                      }*/
                    }

                run_once = false;
           }
            intake.stopIntake();
            hopper.stophopper();
            shooter.stopshooter();


          //  hc.mechanumMoveRobotBackwardsInches(100);
          //  hc.mechanumStrafeLeftInches(-61);

            hc.mechanumMoveRobotBackwardsInches(10);
            hc.mechanumStrafeLeftInches(-30);
            hc.mechanumMoveRobotBackwardsInches(68);
            hc.mechanumStrafeLeftInches(-27); // calibrate
            hc.mechanumMoveRobotBackwardsInches(20);

            break;
        }
   }
}
