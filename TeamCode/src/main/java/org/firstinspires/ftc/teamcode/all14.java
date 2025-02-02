package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "all2_combined", group = "TeleOp")
public class all14 extends OpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotorEx motor1, motor2, motor3;
    private Servo servoMotor;
    private ColorSensor colorSensor;

    public static PIDCoefficients armCoefficients = new PIDCoefficients(0.7, 0, 0);
    private double kP = 0.0005;
    private double kG;
    double armVert = 0;
    double armRad = 1500;
    int count;
    boolean servo_pos;
    private String color = "";
    private int currentServoPositionIndex = 0;
    private final double[] Positions = {0, 200, 300, 700, 1200, 1200}; // פוזיציות למנוע הסרוו
    private boolean lastLeftBumper = false, lastRightBumper = false, lastDpad = false;
    boolean wasButtonAPressed = false;
    private boolean lock = false;
    private static final int MAX_ENCODER_TICKS = 3500; // מקסימום ערכי אינקודר (לדוגמה)
    private static final int MIN_ENCODER_TICKS = -5000; // מינימום ערכים

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        motor1 = hardwareMap.get(DcMotorEx.class, "armMotor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "armMotor2");
        motor3 = hardwareMap.get(DcMotorEx.class, "armMotor3");
        servoMotor = hardwareMap.get(Servo.class, "leftServo");
        colorSensor = hardwareMap.get(ColorSensor.class, "color");

        resetArm();

        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);

        //updateServoPosition();
    }

    private void resetArm() {

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /*
        private void updateServoPosition() {
            double position = servoPositions[currentServoPositionIndex];
            servoMotor.setPosition(position);

        }
    */
    public void setPowerV(double target) {
        //motor1.setPower((target - motor1.getCurrentPosition()) * 0.7);
        motor2.setPower(kP * (target - motor2.getCurrentPosition()));
        //  motor2.setPower(motor1.getPower());
    }

    public void setPowerR(double target) {
        //motor1.setPower((target - motor1.getCurrentPosition()) * 0.7);
        motor1.setPower((5 * kP + kG) * (target - (1500 + motor1.getCurrentPosition())));
        motor3.setPower(motor1.getPower());
        //  motor2.setPower(motor1.getPower());
    }

    @Override
    public void loop() {
        kG = Math.cos((2 * Math.PI * (1000 + motor1.getCurrentPosition())) / 3895.9) * (((Math.abs(motor2.getCurrentPosition())) * 19.5) / (384.5 * 2 * Math.PI * 1000));
        double y = -gamepad1.left_stick_y; // Inverted because y is negative when pushed forward
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double frontLeftPower = y + x + rx;
        double frontRightPower = y - x - rx;
        double backLeftPower = y - x + rx;
        double backRightPower = y + x - rx;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        // שליטה על הזרוע באמצעות כפתורים
        boolean leftBumper = gamepad2.left_bumper;
        boolean rightBumper = gamepad2.right_bumper;
/*
        if (rightBumper & !lastRightBumper) {
            if (currentServoPositionIndex < servoPositions.length - 1) {
                currentServoPositionIndex++;
                armRad = 1000 - (250 * currentServoPositionIndex);
                setPowerR(armRad);
                //  updateServoPosition();
                //count = count + 1;
            }
        }

        if (leftBumper & !lastLeftBumper) {
            if (currentServoPositionIndex > 0) {
                currentServoPositionIndex--;
                armRad = 1000 - (250 * currentServoPositionIndex );
                setPowerR(armRad);
                // updateServoPosition();
                //count = count - 1;
            }
        }*/
        if (gamepad2.left_bumper & !lastLeftBumper) {
            lastLeftBumper = true;
        } else if (!gamepad2.left_bumper & lastLeftBumper) {
            lastLeftBumper = false;
            count = count + 1;
            if (count > 5) {
                count = 5;
            }
            armRad = 1500 - Positions[count];


        }
        setPowerR(armRad);
        if (gamepad2.right_bumper & !lastRightBumper) {
            lastRightBumper = true;
        } else if (!gamepad2.right_bumper & lastRightBumper) {
            lastRightBumper = false;
            count = count - 1;
            if (count < 0) {
                count = 0;
            }
            armRad = 1500 - Positions[count];

        }
        setPowerR(armRad);
        if (gamepad2.y) {
            servoMotor.setPosition(1);
        }
        if (gamepad2.x) {
            servoMotor.setPosition(0);
        }
        //  lastLeftBumper = leftBumper;
        // lastRightBumper = rightBumper;
/*
        if (gamepad2.a & !wasButtonAPressed) {
            wasButtonAPressed = true;
        } else if (!gamepad2.a & wasButtonAPressed & servo_pos) {
            wasButtonAPressed = false;
            servoMotor.setPosition(1);
            servo_pos = true;
            // count = count + 1;
        } else if (!gamepad2.a & wasButtonAPressed & !servo_pos){
            wasButtonAPressed = false;
            servo_pos = false;
            servoMotor.setPosition(0);

        } */

        if (gamepad2.dpad_up & !lastDpad) {
            lastDpad = true;
        } else if (!gamepad2.dpad_up & lastDpad) {
            lastDpad = false;
            lock = !lock;
        }
        if (!lock) {

            if (gamepad2.right_stick_y < 0.1 && gamepad2.right_stick_y > -0.1) {
                setPowerV(armVert);
            } else {
                motor2.setPower(gamepad2.right_stick_y);
                armVert = motor2.getCurrentPosition();
            }
        } else if (lock) {
            motor2.setPower(0.9);
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


        telemetry.addData("Detected Color", color);
        telemetry.addData("Arm power", motor1.getPower());
        telemetry.addData("Arm Motor 1 Position", motor1.getCurrentPosition());
        telemetry.addData("Arm Motor 2 Position", motor2.getCurrentPosition());
        telemetry.addData("press a", count);
        telemetry.addData("armRad", armRad);
        telemetry.addData("last", lastDpad);
        telemetry.addData("Down", gamepad2.dpad_down);
        // telemetry.addData("Servo Position", position);
        telemetry.update();
    }
}

