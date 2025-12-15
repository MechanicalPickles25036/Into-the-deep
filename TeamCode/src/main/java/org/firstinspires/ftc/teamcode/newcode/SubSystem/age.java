package org.firstinspires.ftc.teamcode.newcode.SubSystem;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.newcode.SubSystem.Drive;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "age", group = "TeleOp")


public class age extends OpMode{
    DcMotor right_front;
    DcMotor left_front;
    DcMotor right_back;
    DcMotor left_back;
    Drive my_drive = new Drive();
    private BNO055IMU imu;
    private Orientation angles;

//    public volatile Gamepad gamepad1;
//    public volatile Gamepad gamepad2;

    private Servo servoMotor;
    private boolean lastDpad = false,lock = false;
    // לכתוב את הפונקציה:


    public void init() {
        my_drive.init(hardwareMap);
//         my_drive.right_front.setPower(power);
//        double forward;
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(params);

        }


    public double[] get_direction(){

        double forward = gamepad1.right_trigger - gamepad1.left_trigger;
        double rot  = Math.pow(gamepad1.right_stick_x, 3);
        double right_left =0;
        if (gamepad1.right_bumper){
            right_left = 0.75;
        }
        if (gamepad1.left_bumper){
            right_left = -0.75;
        }
        double[] direction = {forward, right_left, rot};

        return direction;
    }
    public void loop() {
        double[] direction = get_direction();
        my_drive.all_drive(direction);


        angles = imu.getAngularOrientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES
        );
        double heading = angles.firstAngle;   // Z
        double pitch   = angles.secondAngle;  // Y
        double roll    = angles.thirdAngle;   // X

        telemetry.addData("Heading (Z)", heading);
        telemetry.addData("Pitch (Y)", pitch);
        telemetry.addData("Roll (X)", roll);
        telemetry.update();



    }





    }
