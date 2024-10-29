package com.cercli.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class for handling time zone conversions between the server and local time zones.
 */
public class TimeZoneUtils {
    private static final ZoneId SERVER_ZONE_ID = ZoneId.of("UTC"); // Simulated server timezone
    private static final ZoneId LOCAL_ZONE_ID = ZoneId.systemDefault();

    /**
     * Returns the current time in the server's timezone.
     *
     * @return current server time as ZonedDateTime
     */
    public static ZonedDateTime nowInServerTime() {
        return ZonedDateTime.now(SERVER_ZONE_ID);
    }

    /**
     * Converts a server time to local time.
     *
     * @param serverTime the time in server timezone
     * @return the time in local timezone
     */
    public static ZonedDateTime toLocalTime(ZonedDateTime serverTime) {
        if (serverTime == null) {
            return null;
        }
        return serverTime.withZoneSameInstant(LOCAL_ZONE_ID);
    }
}
