package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "auton test rr")
public class RRauto extends LinearOpMode {
    final double TILE = 24; // inches
    MecanumDrive drive;
    public static double ANGLE = 90;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        Action driveForward = drive.actionBuilder(new Pose2d(0, 0, 0))
                .turn(Math.toRadians(90))
                .build();


        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(driveForward);
    }
}
