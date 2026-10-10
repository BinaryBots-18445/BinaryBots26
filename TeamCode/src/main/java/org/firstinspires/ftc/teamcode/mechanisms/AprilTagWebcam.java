package org.firstinspires.ftc.teamcode.mechanisms;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;
import java.util.ArrayList;


public class AprilTagWebcam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;
    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;

                aprilTagProcessor = new AprilTagProcessor.Builder()
                        .setDrawTagID(true)
                        .setDrawTagOutline(true)
                        .setDrawAxes(true)
                        .setDrawCubeProjection(true)
                        .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                        .build();

                VisionPortal.Builder builder = new VisionPortal.Builder();
                builder.setCamera(hwMap.get(WebcamName.class, "Webcam"));
                builder.setCameraResolution(new Size(640, 480));
                builder.addProcessor(aprilTagProcessor);

                visionPortal = builder.build();
    }

    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags(){
        return detectedTags;
    }

    public void displayDetectionTelemetry(List<AprilTagDetection> detectedTags) {
        if (detectedTags == null) {return;}

        // Let's get a single april tag
        for (AprilTagDetection detection : detectedTags) {
            AprilTagSingleDetection myTag = (AprilTagSingleDetection) detection;
            if (myTag.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", myTag.id, myTag.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            }
            else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", myTag.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", myTag.center.x, myTag.center.y));
            }
        }
    }

    public AprilTagDetection getTagBySpecificId(int id) {
        // Let's get a single april tag
        for (AprilTagDetection detection : detectedTags) {
            AprilTagSingleDetection myTag = (AprilTagSingleDetection) detection;
            if (myTag.id == id){
                return myTag;
            }
        }
        return null;
    }

    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
