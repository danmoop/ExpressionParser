import java.util.*;

public class MathValidator {
    private Map<String, Action> actions;

    public MathValidator() {
        actions = new HashMap<>();
        actions.put("+", (a, b) -> a + b);
        actions.put("-", (a, b) -> a - b);
        actions.put("*", (a, b) -> a * b);
        actions.put("/", (a, b) -> a / b);
    }

    public float calculate(String query) {
        String[] values = query.split("(?=[+-/*()])|(?<=[+-/*()])");
        Deque<Float> numbers = new LinkedList<>();
        Deque<String> operations = new LinkedList<>();
        Stack<Float> st = new Stack<>();

        for (int i = 0; i < values.length; i++) {
            String token = st.isEmpty() ? values[i] : String.valueOf(st.pop());

            if (isNumber(token)) {
                String mostRecentOperation = operations.peekLast();

                if (!operations.isEmpty() && (mostRecentOperation.equals("*") || mostRecentOperation.equals("/"))) {
                    numbers.add(actions.get(operations.removeLast()).calculate(numbers.removeLast(), Float.parseFloat(token)));
                } else {
                    numbers.add(Float.parseFloat(token));
                }
            } else {
                if (token.equals(")")) continue;

                if (token.equals("(")) {
                    String innerExpression = extractInnerExpression(query, i + 1);
                    float result = calculate(innerExpression);
                    i += (innerExpression.length() - 1);

                    st.push(result);
                } else {
                    operations.add(token);
                }
            }
        }

        while (!operations.isEmpty()) {
            float top = numbers.removeFirst(), next = numbers.removeFirst();

            numbers.offerFirst(actions.get(operations.removeFirst()).calculate(top, next));
        }

        return numbers.pollFirst();
    }

    private String extractInnerExpression(String s, int start) {
        int end = start;
        while (end < s.length() && s.charAt(end) != ')') end++;
        return s.substring(start, end);
    }

    private boolean isNumber(String s) {
        try {
            float f = Float.parseFloat(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}