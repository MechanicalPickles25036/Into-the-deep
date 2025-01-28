/**
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomi_Mode", group = "FTC")
public class Autonomi_Mode extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor motor1;
    private DcMotor motor2;

    private double kP = 0.15; // קבוע לולאת הבקרה
    private double armTargetPos = 0;
    private double autodrivepos = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // אתחול החומרה
        initHardware();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart(); // מחכה לתחילת האוטונומיה

        while (opModeIsActive()) {
            // שליטה בתנועה
            handleDriving();

            // שליטה בזרוע
            if (gamepad2.dpad_up) {
                armTargetPos += 50; // העלאת הזרוע
            } else if (gamepad2.dpad_down) {
                armTargetPos -= 50; // הורדת הזרוע
            }
            setArmPower(armTargetPos);

            // טלמטריה
            telemetry.addData("Front Left", frontLeft.getCurrentPosition());
            telemetry.addData("Front Right", frontRight.getCurrentPosition());
            telemetry.addData("Back Left", backLeft.getCurrentPosition());
            telemetry.addData("Back Right", backRight.getCurrentPosition());
            telemetry.addData("Arm Motor 1", motor1.getCurrentPosition());
            telemetry.addData("Arm Motor 2", motor2.getCurrentPosition());
            telemetry.update();
        }
    }

    private void initHardware() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        motor1 = hardwareMap.dcMotor.get("armMotor1");
        motor2 = hardwareMap.dcMotor.get("armMotor2");
        Servo servoMotor = hardwareMap.get(Servo.class, "leftServo");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
    }

    private void handleDriving() {
        if (gamepad1.dpad_up) {
            driveForward(0.5);
        } else if (gamepad1.dpad_down) {
            driveBackward(0.5);
        } else if (gamepad1.dpad_left) {
            turnLeft(0.5);
        } else if (gamepad1.dpad_right) {
            turnRight(0.5);
        } else if (gamepad1.right_bumper) {
            rotateClockwise(0.5);
        } else if (gamepad1.left_bumper) {
            rotateCounterClockwise(0.5);
        } else {
            stopDriving();
        }
    }

    private void driveForward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    private void driveBackward(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(-power);
    }

    private void turnLeft(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(-power);
        backRight.setPower(power);
    }

    private void turnRight(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    private void rotateClockwise(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    private void rotateCounterClockwise(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(-power);
        backRight.setPower(power);
    }

    private void stopDriving() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private void setArmPower(double target) {
        motor1.setPower(kP * (target - motor1.getCurrentPosition()));
        motor2.setPower(kP * (target - motor2.getCurrentPosition()));
    }
}
*/

