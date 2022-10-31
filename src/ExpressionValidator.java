import java.util.*;

public class ExpressionValidator {
    private Map<String, Action> actions;

    public ExpressionValidator() {
        actions = new HashMap<>();
        actions.put("+", (a, b) -> a + b);
        actions.put("-", (a, b) -> a - b);
        actions.put("*", (a, b) -> a * b);
        actions.put("/", (a, b) -> a / b);
    }

    float calculate(String query) {
        StringTokenizer tokenizer = new StringTokenizer(query, " +-*/()^", true);
        Deque<Float> numbers = new LinkedList<>();
        Deque<String> operations = new LinkedList<>();

        Map<String, Action> actions = new HashMap<>();
        actions.put("+", (a, b) -> a + b);
        actions.put("-", (a, b) -> a - b);
        actions.put("*", (a, b) -> a * b);
        actions.put("/", (a, b) -> a / b);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (isNumber(token)) {
                if (!operations.isEmpty() && (operations.peekLast().equals("*") || operations.peekLast().equals("/"))) {
                    numbers.add(actions.get(operations.removeLast()).calculate(numbers.removeLast(), Integer.parseInt(token)));
                } else {
                    numbers.add(Float.parseFloat(token));
                }
            } else {
                operations.add(token);
            }

        }

        while (!operations.isEmpty()) {
            float top = numbers.removeFirst(), next = numbers.removeFirst();

            numbers.offerFirst(actions.get(operations.removeFirst()).calculate(top, next));
        }

        return numbers.pollFirst();
    }

    static boolean isNumber(String s) {
        try {
            int n = Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}