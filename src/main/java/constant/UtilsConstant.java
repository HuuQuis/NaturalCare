package constant;

public class UtilsConstant {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    public static final int ITEMS_PER_PAGE = 5;
    public static final int MAX_VISIBLE_PAGES = 5;

    public static final long OTP_EXPIRY_TIME = 5 * 60 * 1000;

    public static final int MAX_OTP_ATTEMPTS = 3;

    public static final long RESEND_COOLDOWN = 60 * 1000; // 60 giây cooldown

}
