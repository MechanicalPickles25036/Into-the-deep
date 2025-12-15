package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class pid {
    double kp, ki, kd, kg;

    double last_error, last_time;
    ElapsedTime stopwatch;

    public pid(double kp, double ki, double kd, double kg) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kg = kg;
        this.last_error = 0;

        stopwatch = new ElapsedTime();

        this.last_time = 0;
    }

    public double calculate(double messaurment) {

    }
}