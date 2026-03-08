package br.com.systec.opusfinancial.reporting.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class DateUtils {

    public static LocalDate timeStampToLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return timestamp.toLocalDateTime().toLocalDate();
    }

    public static LocalDate dateSqlToLocalDate(Date date) {
        if (date == null) {
            return null;
        }

        return date.toLocalDate();
    }
}
