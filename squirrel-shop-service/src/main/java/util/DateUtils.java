package util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-25 15:45
 */
@UtilityClass
public class DateUtils {

    /**
     * 获取当前时间
     */
    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
