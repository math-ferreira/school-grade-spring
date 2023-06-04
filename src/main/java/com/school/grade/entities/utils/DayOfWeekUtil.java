package com.school.grade.entities.utils;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.*;
import static java.time.DayOfWeek.SATURDAY;

public class DayOfWeekUtil {
     public static DayOfWeek getDayOFWeek(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "segunda" -> MONDAY;
            case "terca" -> TUESDAY;
            case "quarta" -> WEDNESDAY;
            case "quinta" -> THURSDAY;
            case "sexta" -> FRIDAY;
            case "sabado" -> SATURDAY;
            default -> SUNDAY;
        };
    }
}
