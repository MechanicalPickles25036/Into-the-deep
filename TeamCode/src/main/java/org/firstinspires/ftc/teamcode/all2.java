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

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "all2", group = "TeleOp")
public class all2 extends OpMode {
    String color = "";
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private Servo servoMotor;
    private DcMotorEx motor1;
    private DcMotorEx motor2;
    private DcMotorEx motor3;
    public static PIDCoefficients armCoefficients = new PIDCoefficients(0.7, 0, 0);
    double armRad = 1000;
    double armVert = 0;
    ColorSensor colorSensor;
    double kP = 0.0005;
    double kG;
    boolean buttonAPress = false;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry,FtcDashboard.getInstance().getTelemetry());
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        motor1 = hardwareMap.get(DcMotorEx.class, "armMotor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "armMotor2");
        motor3= hardwareMap.get(DcMotorEx.class,"armMotor3");
        motor1.getCurrent(CurrentUnit.MILLIAMPS);

        servoMotor = hardwareMap.get(Servo.class, "leftServo");
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        resetArm();
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        // frontLeft.setDirection(DcMotor.Direction.REVERSE);

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);

        backRight.getCurrentPosition();
    }

    private void resetArm() {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPowerV(double target) {
        //motor1.setPower((target - motor1.getCurrentPosition()) * 0.7);
        motor2.setPower(kP * (target - motor2.getCurrentPosition()));
        //  motor2.setPower(motor1.getPower());
    }

    public void setPowerR(double target) {
        //motor1.setPower((target - motor1.getCurrentPosition()) * 0.7);
        motor1.setPower((5*kP +kG) * (target - motor1.getCurrentPosition()));
        motor3.setPower(motor1.getPower());
        //  motor2.setPower(motor1.getPower());
    }

    public void Autodrive(double target) {
//        backLeft.setPower(kP * (target - backLeft.getCurrentPosition()));
//        backRight.setPower(kP * (target - backRight.getCurrentPosition()));
//        motor2.setPower(motor1.getPower());
    }




    @Override
    public void loop() {
        kG = Math.cos((2*Math.PI* motor1.getCurrentPosition())/3895.9)*(1000-(Math.abs(motor2.getCurrentPosition())*19.5)/(384.5*2*Math.PI*1000));
        double y = -gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y); // Inverted because y is negative when pushed forward
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

        if (gamepad2.y){
            servoMotor.setPosition(1);
        }
        if (gamepad2.x){
            servoMotor.setPosition(0);
        }
        //  double rightStickY = gamepad2.right_stick_y;  // הפיכת הערך לשליטה נכונה
        //  double servoPosition = (rightStickY + 1) / 2;  // ממיר את הטווח של -1 עד 1 ל-0 עד 1
        //servoMotor.setPosition(servoPosition);//

        //double power = -gamepad2.left_stick_y;
        //motor1.setPower(power);
        //motor2.setPower(power);
      /*  if (gamepad2.left_stick_y < 0.1 && gamepad2.left_stick_y > -0.1) {
            setPowerR(armRad);
        } else {
            motor1.setPower(-gamepad2.left_stick_y);
            armRad = motor1.getCurrentPosition();
            motor3.setPower(-gamepad2.left_stick_y);
        }2


        if (gamepad2.right_stick_y < 0.1 && gamepad2.right_stick_y > -0.1) {
            setPowerV(armVert);
        } else {
            motor2.setPower(gamepad2.right_stick_y);
            armVert = motor2.getCurrentPosition();
        }*/

        if (colorSensor.blue() > 100 && colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
            color = "BLUE";
        } else if (colorSensor.red() > 100 && colorSensor.red() > colorSensor.blue() && colorSensor.red() > colorSensor.green()) {
            color = "red";
        } else if (colorSensor.green() > 100 && colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
            color = "yellow";
        } else {
            color = "don't know?";
        }
        //  telemetry.addData("frontLeftPower", frontLeftPower);
        //   telemetry.addData("frontRightPower", frontRightPower);
        //  telemetry.addData("backRightPower", backRightPower);
        //  telemetry.addData("Right Stick Y", rightStickY);
        telemetry.addData("Arm power1", motor2.getPower());
        telemetry.addData("Arm power", motor1.getPower());
        telemetry.addData("COLOR: ", color);
        telemetry.addData("pawer", (motor2.getCurrentPosition()*19.5)/(384.5*2*Math.PI*500));
        telemetry.addData("cos", Math.cos((2*Math.PI* motor1.getCurrentPosition())/3895.9));
        // telemetry.addData("COLOR: B", colorSensor.blue());
        //   telemetry.addData("Motor Power", power);
        telemetry.addData("armRad", motor1.getCurrentPosition());
        telemetry.addData("armVert", motor2.getCurrentPosition());
        telemetry.update();
    }


}