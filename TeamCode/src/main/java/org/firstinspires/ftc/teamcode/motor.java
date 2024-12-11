package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Two Motors Control", group="TeleOp")
public class motor extends OpMode {

    // הכרזת מנועים
    private DcMotor motor1;
    private DcMotor motor2;

    @Override
    public void init() {
        // אתחול מנועים לפי השמות שהוגדרו בקובץ הקונפיגורציה
        motor1 = hardwareMap.dcMotor.get("armMotor1");
        motor2 = hardwareMap.dcMotor.get("armMotor2");

        // הגדרת כיוון מנוע, אם יש צורך
        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        // קבלת ערך הסטיק השמאלי מ-Gamepad 2
        double power = -gamepad2.left_stick_y;  // למעלה זה ערך שלילי, לכן צריך להכפיל במינוס

        // הפעלת המנועים עם אותה עוצמה
        motor1.setPower(power);
        motor2.setPower(power);

        // הצגת המידע בטלמטריה (הדפסה למסך בקר)
        telemetry.addData("Motor Power", power);
        telemetry.update();
    }
}
