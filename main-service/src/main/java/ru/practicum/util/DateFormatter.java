package ru.practicum.util;

import lombok.experimental.UtilityClass;
import util.Constants;

import java.time.LocalDateTime;

@UtilityClass
public class DateFormatter {
    public static LocalDateTime creatDataFromString(String date) {
        return LocalDateTime.parse(date, Constants.formatter);
    }
}
