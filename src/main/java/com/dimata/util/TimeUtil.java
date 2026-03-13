package com.dimata.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public interface TimeUtil {

    static LocalDateTime plusTime(LocalDateTime time, int second) {
        return time.plusSeconds(second);
    }

    static LocalDateTime plusTimeNow(int second) {
        return LocalDateTime.now().plusSeconds(second);
    }

    static Date plusTimeNowToDate(int second) {
        var plusTime = plusTimeNow(second);
        return toDate(plusTime);
    }

    static boolean isBeforeNow(LocalDateTime compareWith) {
        var date = toDate(compareWith);
        return isBeforeNow(date);
    }

    static boolean isBeforeNow(Date compareWith) {
        var now = new Date().getTime();
        return compareWith.getTime() < now;
    }

    static boolean isExpired(LocalDateTime compareWith) {
        var date = toDate(compareWith);
        return isExpired(date);
    }

    static boolean isExpired(Date compareWith) {
        return isBeforeNow(compareWith);
    }

    static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
