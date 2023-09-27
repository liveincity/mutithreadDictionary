/* This file is made by Tianchen FAN, 1166401
 * */
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class Cookies {
    private int key = 1500;
    private int expireSeconds = 60;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Cookies() {
    }
    public Cookies(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    private String encryptAndDecode(String text) {
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char)(charArray[i]^this.key);
        }

        return (new String(charArray));
    }

    public String generateCookies() {
        LocalDateTime currentDate = LocalDateTime.now();
        String currentDateString = currentDate.format(dateTimeFormatter);
        return encryptAndDecode(currentDateString);
    }

    public boolean checkIfCookieNotxpired(String cookies) {
        String loginDateString = encryptAndDecode(cookies);
        LocalDateTime loginDate = LocalDateTime.parse(loginDateString, dateTimeFormatter);
        LocalDateTime currentDate = LocalDateTime.now();
        long secondsAfterLogin = Math.abs(Duration.between(loginDate, currentDate).getSeconds());
        if (secondsAfterLogin < this.expireSeconds) {
            //System.out.println("Seconds after login: "+ secondsAfterLogin);
            return true;
        } else {
            //System.out.println("Seconds after login: "+ secondsAfterLogin);
            return false;
        }
    }
}
