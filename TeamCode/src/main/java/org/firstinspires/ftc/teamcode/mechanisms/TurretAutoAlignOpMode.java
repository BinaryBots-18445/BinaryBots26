package org.firstinspires.ftc.teamcode.tutorials;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam
public class TurretAutoAlignOpMode extends OpMode{
    private  AprilTagWebcam apriltagwebcam = new AprilTagWebcom
    private TurretMechanism turret = new TurretMchanism



    @Override
    public void init() {
        aprilTagWebcam.init(HardwareMap, telemetry);
        turret.init(hardwareMap);

        telemetry.addline("Initialized all mechanisms")
    }

    public void start() {
        turret.restTimer();

    }

    @Override
    public void loop () {
        // vision logic
        aprilTagWebcam.update();
        AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20)

        turret.update(id20);

        if (id20 != null) {
            telemetry.addData("cur id", aprilTagWebcam);
        }
    }
}



