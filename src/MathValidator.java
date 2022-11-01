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
        String[] values = query.split("(?=[+-/*])|(?<=[+-/*])"); // xd

        Deque<Float> numbers = new LinkedList<>();
        Deque<String> operations = new LinkedList<>();
        Stack<Float> recursionCalculations = new Stack<>();

        Map<String, Action> actions = new HashMap<>();
        actions.put("+", (a, b) -> a + b);
        actions.put("-", (a, b) -> a - b);
        actions.put("*", (a, b) -> a * b);
        actions.put("/", (a, b) -> a / b);

        for (int i = 0; i < values.length; i++ ){
            String token = recursionCalculations.isEmpty() ? values[i] : recursionCalculations.pop().toString();

            if (isNumber(token)) {
                String mostRecentOperation = operations.peekLast();

                if (!operations.isEmpty() && (mostRecentOperation.equals("*") || mostRecentOperation.equals("/"))) {
                    numbers.add(actions.get(operations.removeLast()).calculate(numbers.removeLast(), Integer.parseInt(token)));
                } else {
                    numbers.add(Float.parseFloat(token));
                }
            } else {
                if (token.equals("(")) {
                    float innerExpressionResult = calculate(extractInnerExpression(query, i + 1));
                    recursionCalculations.push(innerExpressionResult);
                    while (!values[i].equals(")")) i++;
                } else {
                    operations.add(token);
                }
            }

            System.out.println(numbers);
            System.out.println(operations);
            System.out.println("_____________________________");
        }

        while (!operations.isEmpty()) {
            float top = numbers.removeFirst(), next = numbers.removeFirst();

            numbers.offerFirst(actions.get(operations.removeFirst()).calculate(top, next));
        }

        return numbers.pollFirst();
    }

    private String extractInnerExpression(String s, int start) {
        int end = start;
        while (s.charAt(end) != ')') end++;
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