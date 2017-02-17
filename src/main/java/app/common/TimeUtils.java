package app.common;

import org.joda.time.DateTime;

/**
 * Created by Dachu on 2016-04-24.
 */
public interface TimeUtils {
    static boolean checkIsActive(DateTime dateFrom, DateTime dateTo){
        return (DateTime.now().isBefore(dateTo) || DateTime.now().isEqual(dateTo)) && (DateTime.now().isAfter(dateFrom) || DateTime.now().isEqual(dateFrom));
    }
}
