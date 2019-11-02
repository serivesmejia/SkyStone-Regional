package org.firstinspires.ftc.teamcode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pipeline.SkystonePatternPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="Autonomo Skystone Pattern", group="Test")
public class AutonomoSkystonePattern extends LinearOpMode {

    private OpenCvCamera phoneCam;
    private SkystonePatternPipeline patternPipeline;

    @Override
    public void runOpMode() {
        //creamos la vista desde el celular de la camara
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        //creamos la camara de OpenCV
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        //la inicializamos
        phoneCam.openCameraDevice();

        //creamos el detector de skystones
        patternPipeline = new SkystonePatternPipeline();
        phoneCam.setPipeline(patternPipeline);

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        //esperamos que el usuario presione <play> en la driver station
        waitForStart();

        while (opModeIsActive()) {
            //enviamos mensajes telemetry que contienen informacion
            telemetry.addData("PATTERN", patternPipeline.pattern);
            telemetry.addData("FRAME", phoneCam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", phoneCam.getFps()));
            telemetry.addData("TFT MS", phoneCam.getTotalFrameTimeMs());
            telemetry.addData("PT MS", phoneCam.getPipelineTimeMs());
            telemetry.addData("OT MS", phoneCam.getOverheadTimeMs());
            telemetry.addData("MAX FPS", phoneCam.getCurrentPipelineMaxFps());
            telemetry.addData("LEFT RECT", patternPipeline.left_hue + " " + patternPipeline.left_br);
            telemetry.addData("RIGHT RECT", patternPipeline.right_hue + " " + patternPipeline.right_br);
            telemetry.update();
        }
    }
}