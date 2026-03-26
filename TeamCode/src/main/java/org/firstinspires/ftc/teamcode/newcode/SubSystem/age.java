package org.firstinspires.ftc.teamcode.newcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.newcode.SubSystem.Drive;
import org.firstinspires.ftc.teamcode.newcode.SubSystem.arm;
import org.firstinspires.ftc.teamcode.newcode.SubSystem.intake;
import org.firstinspires.ftc.teamcode.newcode.SubSystem.shooter;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "age", group = "TeleOp")

public class age extends OpMode {
    DcMotor right_front;
    DcMotor left_front;
    DcMotor right_back;
    DcMotor left_back;

    Drive my_drive = new Drive();
    intake my_intake = new intake();
    shooter my_shooter = new shooter();
    arm my_arm = new arm();
    Camera my_camera = new Camera();

    boolean shooter_on = false;
    boolean lastX = false;

    boolean lockOn = false;
    boolean lockX = false;
    boolean lockY = false;
    boolean lastA = false;
    boolean lastB1 = false;
    boolean lastDpadAny = false;
    long lastB1Time = 0;
    long lastDpadTime = 0;

    private Servo servoMotor;
    private boolean lastDpad = false, lock = false;
    private Servo myServo;
    boolean servoDown = false;
    boolean lastB = false;
    private Servo myServo2;

    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        my_camera.init(hardwareMap);
        my_drive.init(hardwareMap);
        my_intake.init(hardwareMap);
        my_shooter.init(hardwareMap);
        my_arm.init(hardwareMap);
        FtcDashboard.getInstance().startCameraStream(my_camera.getVisionPortal(), 0);
    }

    public double[] get_direction() {
        double forward = gamepad1.left_trigger - gamepad1.right_trigger;
        double rot = -Math.pow(gamepad1.right_stick_x, 3);
        double right_left = 0;
        if (gamepad1.right_bumper) right_left = -0.85;
        if (gamepad1.left_bumper) right_left = 0.85;
        double[] direction = {forward, right_left, rot};
        return direction;
    }

    public void loop() {
        my_arm.loop(gamepad2);

        // A - לחיצה אחת להפעיל, לחיצה אחת לכבות
        if (gamepad1.a && !lastA) {
            lockOn = !lockOn;
            if (lockOn) {
                lockX = false;
                lockY = false;
            }
        }
        lastA = gamepad1.a;

        // Dpad - דאבל לחיצה להפעיל, לחיצה לכבות
        boolean dpadAny = gamepad1.dpad_left || gamepad1.dpad_right || gamepad1.dpad_up || gamepad1.dpad_down;
        if (dpadAny && !lastDpadAny) {
            long now = System.currentTimeMillis();
            if (lockX) {
                lockX = false;
            } else if (now - lastDpadTime < 400) {
                lockX = true;
                lockOn = false;
                lockY = false;
            }
            lastDpadTime = now;
        }
        lastDpadAny = dpadAny;

        // B - דאבל לחיצה להפעיל, לחיצה לכבות
        if (gamepad1.b && !lastB1) {
            long now = System.currentTimeMillis();
            if (lockY) {
                lockY = false;
            } else if (now - lastB1Time < 400) {
                lockY = true;
                lockOn = false;
                lockX = false;
            }
            lastB1Time = now;
        }
        lastB1 = gamepad1.b;

        // נהיגה
        if (lockOn) {
            // A - רק סיבוב אוטומטי, שאר הנהיגה חופשית לגמרי
            List<AprilTagDetection> detectionsA = my_camera.getAprilTagProcessor().getDetections();
            if (!detectionsA.isEmpty()) {
                AprilTagDetection tag = detectionsA.get(0);
                double xError = tag.center.x - 320;
                double turnPower = -xError * 0.005;
                if (turnPower > 0.4) turnPower = 0.4;
                if (turnPower < -0.4) turnPower = -0.4;

                double forward = gamepad1.left_trigger - gamepad1.right_trigger;
                double right_left = 0;
                if (gamepad1.right_bumper) right_left = -0.85;
                if (gamepad1.left_bumper) right_left = 0.85;

                my_drive.all_drive(new double[]{forward, right_left, turnPower});
            } else {
                my_drive.all_drive(get_direction());
            }
        } else if (lockX || lockY) {
            List<AprilTagDetection> detectionsXY = my_camera.getAprilTagProcessor().getDetections();
            if (!detectionsXY.isEmpty()) {
                AprilTagDetection tag = detectionsXY.get(0);
                double xError = tag.center.x - 320;
                double yError = tag.center.y - 240;

                double turnPower = lockX ? (-xError * 0.005) : (-Math.pow(gamepad1.right_stick_x, 3));
                if (turnPower > 0.4) turnPower = 0.4;
                if (turnPower < -0.4) turnPower = -0.4;

                double forward = lockY ? (yError * 0.005) : (gamepad1.left_trigger - gamepad1.right_trigger);
                if (lockY) {
                    if (forward > 0.4) forward = 0.4;
                    if (forward < -0.4) forward = -0.4;
                }

                double right_left = 0;
                if (gamepad1.right_bumper) right_left = -0.85;
                if (gamepad1.left_bumper) right_left = 0.85;

                my_drive.all_drive(new double[]{forward, right_left, turnPower});
            } else {
                my_drive.all_drive(get_direction());
            }
        } else {
            my_drive.all_drive(get_direction());
        }

        if (gamepad2.left_bumper && gamepad2.right_bumper) {
            my_intake.start_in();
        } else if (gamepad2.left_bumper || gamepad2.right_bumper) {
            my_intake.start_out();
        } else {
            my_intake.stop();
        }

        if (gamepad2.x && !lastX) {
            shooter_on = !shooter_on;
            if (shooter_on) my_shooter.start();
            else my_shooter.stop();
        }
        lastX = gamepad2.x;

        if (gamepad2.right_stick_button || gamepad2.left_stick_button) {
            my_shooter.reverse();
        }

        // טלמטרי מצלמה
        List<AprilTagDetection> detections = my_camera.getAprilTagProcessor().getDetections();
        if (!detections.isEmpty()) {
            AprilTagDetection tag = detections.get(0);
            telemetry.addData("Tag ID", tag.id);
            telemetry.addData("ביטחון", "%.1f", tag.decisionMargin);
            telemetry.addData("מרכז X", "%.0f", tag.center.x);
            telemetry.addData("מרכז Y", "%.0f", tag.center.y);
            telemetry.addData("רוחב תג", "%.0f px", tag.corners[1].x - tag.corners[0].x);
            double myX = tag.center.x;
            double myY = 480 - tag.center.y;
            telemetry.addData("X (שמאל=0)", "%.0f", myX);
            telemetry.addData("Y (למטה=0)", "%.0f", myY);
        } else {
            telemetry.addData("Tag ID", 0);
            telemetry.addData("ביטחון", 0);
            telemetry.addData("מרכז X", 0);
            telemetry.addData("מרכז Y", 0);
            telemetry.addData("רוחב תג", 0);
            telemetry.addData("X (שמאל=0)", 0);
            telemetry.addData("Y (למטה=0)", 0);
        }

        telemetry.addData("Lock-On", lockOn);
        telemetry.addData("Lock-X", lockX);
        telemetry.addData("Lock-Y", lockY);
        telemetry.addData("left2 ", gamepad2.dpad_left);
        telemetry.addData("up2 ", gamepad2.dpad_up);
        telemetry.addData("right2 ", gamepad2.dpad_right);
        telemetry.addData("down2 ", gamepad2.dpad_down);
        telemetry.addData("a2 ", gamepad2.a);
        telemetry.addData("b2 ", gamepad2.b);
        telemetry.addData("x2 ", gamepad2.x);
        telemetry.addData("y2 ", gamepad2.y);
        telemetry.addData("rb2 ", gamepad2.right_bumper);
        telemetry.addData("lb2 ", gamepad2.left_bumper);
        telemetry.addData("rt2 ", gamepad2.right_trigger);
        telemetry.addData("lt2 ", gamepad2.left_trigger);
        telemetry.addData("lt ", gamepad1.left_trigger);
        telemetry.addData("rt ", gamepad1.right_trigger);
        telemetry.addData("lb ", gamepad1.left_bumper);
        telemetry.addData("rb ", gamepad1.right_bumper);
        telemetry.addData("left ", gamepad1.dpad_left);
        telemetry.addData("up ", gamepad1.dpad_up);
        telemetry.addData("right ", gamepad1.dpad_right);
        telemetry.addData("down ", gamepad1.dpad_down);
        telemetry.addData("a ", gamepad1.a);
        telemetry.addData("b ", gamepad1.b);
        telemetry.addData("x ", gamepad1.x);
        telemetry.addData("y ", gamepad1.y);
        telemetry.update();
    }
}
