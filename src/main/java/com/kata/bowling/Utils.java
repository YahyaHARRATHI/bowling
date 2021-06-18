package main.java.com.kata.bowling;

public class Utils {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static int getIntValue(String s) {
        if (isNumeric(s))
            return Integer.parseInt(s);
        return -1;
    }
}
