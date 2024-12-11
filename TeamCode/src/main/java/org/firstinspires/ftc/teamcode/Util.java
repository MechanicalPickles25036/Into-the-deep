package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Supplier;

/**
 * Utility class.
 */
public class Util {



    /**
     * Custom exception for timeout scenarios.
     */
    public static class TimeoutException extends Exception {
        public TimeoutException(String message) {
            super(message);
        }
    }
}