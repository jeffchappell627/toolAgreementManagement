package org.example;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class HolidayUtility {
    public static boolean isHoliday(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);
        if (independenceDay.getDayOfWeek().getValue() == 6) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().getValue() == 7) {
            independenceDay = independenceDay.plusDays(1);
        }

        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1)
                .with(TemporalAdjusters.firstInMonth(java.time.DayOfWeek.MONDAY));

        return date.equals(independenceDay) || date.equals(laborDay);
    }
}