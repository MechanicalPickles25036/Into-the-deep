// קובץ זה מתמצת את הקוד עבור מנועים, חיישנים, ומערכות הנעה ב-FTC

package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutonomousFTC extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private final DistanceSensor distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
    private TouchSensor touchSensor;

    @Override
    public void runOpMode() {
        initializeHardware();

        waitForStart();

        // תנועה אוטונומית פשוטה
        driveStraight();  // נסיעה ישרה למרחק מוגדר
        turnToAngle(0.5);     // פנייה של 90 מעלות

        // שילוב חיישנים בתנועה
        if (distanceSensor.getDistance(DistanceUnit.CM) < 10) {
            stopMotors();
            telemetry.addData("Warning", "Object too close!");
        }

        if (touchSensor.isPressed()) {
            stopMotors();
            telemetry.addData("Touch", "Sensor pressed");
        }

        telemetry.update();
    }

    // אתחול חומרה
    private void initializeHardware() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        hardwareMap.get(ColorSensor.class, "colorSensor");
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");

        for (DcMotor motor : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }
    }

    // תנועה ישרה
    private void driveStraight() {
        for (DcMotor motor : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            motor.setTargetPosition(1000);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(0.5);
        }

        while (opModeIsActive() && frontLeft.isBusy()) {
            telemetry.addData("Position", frontLeft.getCurrentPosition());
            telemetry.update();
        }

        stopMotors();
    }

    // פנייה לזווית מוגדרת
    private void turnToAngle(double power) {
        double currentAngle = 0;  // הנחה: יש חיישן IMU המודד זווית
        while (Math.abs(currentAngle - (double) 90) > 1) {
            frontLeft.setPower((double) 90 > currentAngle ? -power : power);
            backLeft.setPower((double) 90 > currentAngle ? -power : power);
            frontRight.setPower((double) 90 > currentAngle ? power : -power);
            backRight.setPower((double) 90 > currentAngle ? power : -power);

            currentAngle = 0;  // עדכון זווית מה-IMU
            telemetry.addData("Current Angle", currentAngle);
            telemetry.update();
        }
        stopMotors();
    }

    // עצירת מנועים
    private void stopMotors() {
        for (DcMotor motor : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            motor.setPower(0);
        }//
    }
}

