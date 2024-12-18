package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "auton test rr")
public class RRauto extends LinearOpMode {
    final double TILE = 24; // inches
    SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);

        Trajectory driveForward = drive.trajectoryBuilder(new Pose2d())
                .forward(TILE)
                .build();



        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectory(driveForward);
    }
}
