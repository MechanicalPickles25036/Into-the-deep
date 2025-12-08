package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "horim_single_motor", group = "TeleOp")
public class horim_single_motor extends OpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotorEx motor1;
    private Servo servoMotor;
    private ColorSensor colorSensor;
    private TouchSensor touch1, touch2;

    int hit1;
    public static PIDCoefficients armCoefficients = new PIDCoefficients(0.7, 0, 0);
    private double kP = 0.0005;
    private double kG = 2;
    double armVert = 0;
    double armRad = 1100;
    int count = 1;
    private String color = "";
    private final double[] Positions = {-100, 0, 200, 350, 640,800}; // פוזיציות לזרוע
    private boolean lastLeftBumper = false, lastRightBumper = false, lastDpad = false;
    private boolean lock = false;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        motor1 = hardwareMap.get(DcMotorEx.class, "armMotor1");
        servoMotor = hardwareMap.get(Servo.class, "leftServo");
        servoMotor.setPosition(1);
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        touch1 = hardwareMap.get(TouchSensor.class,"button1");
        touch2 = hardwareMap.get(TouchSensor.class,"button2");

        resetArm();

        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        motor1.setDirection(DcMotor.Direction.REVERSE);
    }

    private void resetArm() {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPowerR(double target) {
        motor1.setPower((10 * kP + kG * 1.5) * (target - (1000 + motor1.getCurrentPosition())));
    }

    @Override
    public void loop() {
        kG = Math.cos((2 * Math.PI * (2000 + motor1.getCurrentPosition())) / 5895.9);

        double y = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
        double x = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);

        double frontLeftPower = y + x + rx;
        double frontRightPower = y - x - rx;
        double backLeftPower = y - x + rx;
        double backRightPower = y + x - rx;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        // שליטה על הזרוע באמצעות כפתורים
        if (gamepad2.left_bumper & !lastLeftBumper) {
            lastLeftBumper = true;
        } else if (!gamepad2.left_bumper & lastLeftBumper) {
            lastLeftBumper = false;
            count++;
            if (count > 5) count = 5;
            armRad = 1000 - Positions[count];
        }
        setPowerR(armRad);

        if (gamepad2.right_bumper & !lastRightBumper) {
            lastRightBumper = true;
        } else if (!gamepad2.right_bumper & lastRightBumper) {
            lastRightBumper = false;
            count--;
            if (count < 0) count = 0;
            armRad = 1000 - Positions[count];
        }
        setPowerR(armRad);

        // סרוו
        if (gamepad2.y) servoMotor.setPosition(0.9);
        if (gamepad2.x) servoMotor.setPosition(0.19);

        if (gamepad2.a){
            motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // נעילה
        if (gamepad2.dpad_up & !lastDpad) {
            lastDpad = true;
        } else if (!gamepad2.dpad_up & lastDpad) {
            lastDpad = false;
            lock = !lock;
        }

        if (!lock) {
            motor1.setPower(gamepad2.right_stick_y * Math.abs(gamepad2.right_stick_y));
        } else {
            motor1.setPower(0.9);
        }

        // זיהוי צבע
        if (colorSensor.blue() > 100 && colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
            color = "BLUE";
        } else if (colorSensor.red() > 100 && colorSensor.red() > colorSensor.blue() && colorSensor.red() > colorSensor.green()) {
            color = "RED";
        } else if (colorSensor.green() > 100 && colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
            color = "GREEN";
        } else {
            color = "UNKNOWN";
        }

        // Telemetry
        telemetry.addData("Arm Motor 1 Position", motor1.getCurrentPosition());
        telemetry.addData("Arm Power", motor1.getPower());
        telemetry.addData("Count", count);
        telemetry.addData("Detected Color", color);
        telemetry.update();
    }
}
