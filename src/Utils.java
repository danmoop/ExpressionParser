public class Utils {
    private static final String MATH_REGEX = "(?=[+-/*()])|(?<=[+-/*()])";
    private static final String LOGIC_REGEX = "(?=[!&|()])|(?<=[!&|()])";

    public static String extractExpression(String[] values, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = index + 1; i < values.length; i++) sb.append(values[i]);
        String s = sb.toString();

        int open = 0;
        int i;

        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            } else if (s.charAt(i) == ')') {
                if (open == 0) break;
                open--;
            }
        }

        return sb.substring(0, i);
    }

    public static String[] getMathExpressionTokens(String query) {
        return query.split(MATH_REGEX);
    }

    public static String[] getLogicExpressionTokens(String query) {
        return query.split(LOGIC_REGEX);
    }

    public static boolean isNumber(String s) {
        try {
            float f = Float.parseFloat(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isBoolean(String s) {
        return s.equals("true") || s.equals("false");
    }
}