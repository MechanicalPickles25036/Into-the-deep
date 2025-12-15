package org.firstinspires.ftc.teamcode.newcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.newcode.SubSystem.Drive;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "newTeleop", group = "TeleOp")
    public class newTeleop extends OpMode {
    double power = 0.5;
    DcMotorEx manof;
    DcMotor right_front;
    DcMotor left_front;
    DcMotor right_back;
    DcMotor left_back;
    Drive my_drive = new Drive();

    private Servo servoMotor;
    private boolean lastDpad = false,lock = false;
    // לכתוב את הפונקציה:


    public void init() {
        my_drive.init(hardwareMap);
//         my_drive.right_front.setPower(power);
         double forward = gamepad1.left_stick_y;

         servoMotor = hardwareMap.get(Servo.class, "leftServo");
         manof = hardwareMap.get(DcMotorEx.class,"armMotor2");
         manof.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         manof.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);





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
        manof.setPower(gamepad2.right_stick_y);


        my_drive.all_drive(direction);
            if (!lock) {
                servoMotor.setPosition(0.15);
            } else {
                servoMotor.setPosition(1);
            }

                if (gamepad2.left_bumper & !lastDpad) {
                    lastDpad = true;
                } else if (!gamepad2.left_bumper & lastDpad) {
                    lastDpad = false;
                    lock = !lock;



            }

            telemetry.update();
        }
}

