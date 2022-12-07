public class Utils {
    // Regular expression to match mathematical operators
    private static final String MATH_REGEX = "(?=[+-/*()])|(?<=[+-/*()])";
    // Regular expression to match logical operators
    private static final String LOGIC_REGEX = "(?=[!&|()])|(?<=[!&|()])";

    /**
     * Extracts the expression following the given index in the values array.
     *
     * @param values The array of values.
     * @param index  The index from which to extract the expression.
     * @return The extracted expression.
     */
    public static String extractExpression(String[] values, int index) {
        // Build the expression as a string
        StringBuilder sb = new StringBuilder();
        for (int i = index + 1; i < values.length; i++) sb.append(values[i]);

        // Find the end of the expression by keeping track of open and closed
        // parentheses
        int open = 0;
        int i;

        for (i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(') {
                open++;
            } else if (sb.charAt(i) == ')') {
                if (open == 0) break;
                open--;
            }
        }

        // Return the substring of the expression up to the end
        return sb.substring(0, i);
    }

    /**
     * Splits the given mathematical expression into individual tokens.
     *
     * @param query The mathematical expression to tokenize.
     * @return An array of tokens.
     */
    public static String[] getMathExpressionTokens(String query) {
        // Split the query using the mathematical regular expression
        return query.split(MATH_REGEX);
    }

    /**
     * Splits the given logical expression into individual tokens.
     *
     * @param query The logical expression to tokenize.
     * @return An array of tokens.
     */
    public static String[] getLogicExpressionTokens(String query) {
        // Split the query using the logical regular expression
        return query.split(LOGIC_REGEX);
    }

    /**
     * Determines whether the given string is a number.
     *
     * @param s The string to check.
     * @return true if the string is a number, false otherwise.
     */
    public static boolean isNumber(String s) {
        try {
            // Attempt to parse the string as a float
            float f = Float.parseFloat(s);
        } catch (Exception e) {
            // If an exception is thrown, the string is not a number
            return false;
        }

        // If no exception is thrown, the string is a number
        return true;
    }

    /**
     * Determines whether the given string is a boolean value.
     *
     * @param s The string to check.
     * @return true if the string is "true" or "false", false otherwise.
     */
    public static boolean isBoolean(String s) {
        // Check if the string is either "true" or "false"
        return s.equals("true") || s.equals("false");
    }
}