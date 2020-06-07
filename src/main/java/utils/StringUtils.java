package utils;

public class StringUtils {

    public static String convertToNullIfEmpty(String text) {
        if (text != null && text.isEmpty()) {
            return null;
        }
        return text;
    }

    public static String[] splitIntoPair(String target, String delimiter) {
        int indexOfDelimiter = target.indexOf(delimiter);

        String first = target;
        String second = "";

        if (indexOfDelimiter > 0) {
            first = target.substring(0, indexOfDelimiter);
            second = target.substring(indexOfDelimiter + 1);
        }

        return new String[]{first, second};
    }
}