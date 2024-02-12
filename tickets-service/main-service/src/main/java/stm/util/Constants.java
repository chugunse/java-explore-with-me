package stm.util;

import java.time.format.DateTimeFormatter;

public interface Constants {
    String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    String TIME_PATTERN = "HH:mm";
    DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);
}
