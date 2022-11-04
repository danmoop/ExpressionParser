import java.util.*;

public class MathValidator {
    private Map<String, Action> actions;

    public MathValidator() {
        actions = new HashMap<>();

        // Add lambda expressions to the map to easily access the operator logic
        actions.put("+", (a, b) -> a + b);
        actions.put("-", (a, b) -> a - b);
        actions.put("*", (a, b) -> a * b);
        actions.put("/", (a, b) -> a / b);
    }

    public float calculate(String query) {
        // This one contains the array of values and operators separated
        String[] values = Utils.getExpressionTokens(query);

        // Double linked list containing all numbers and calculations
        Deque<Float> numbers = new LinkedList<>();

        // Double linked list containing all operators (+, -, *, and /)
        Deque<String> operations = new LinkedList<>();

        // Stack contains any calculation results from inner expressions, which should be added to the numbers queue
        Stack<Float> recursionStack = new Stack<>();

        for (int i = 0; i < values.length; i++) {

            /*
             * token is a single element from the original expression we want to evaluate
             * volues is an array of elements separated, so it might contain either a number or an operator
             * if there are some results from the recursion evaluation, take that one instead and process it
             *   since it is the most recent calculation
             */
            String token = recursionStack.isEmpty() ? values[i] : String.valueOf(recursionStack.pop());

            /*
             * If the token is a number, we should see if there are any operations present we added before
             * If there is a high-precedence operator currently sitting in a queue (multiplication, division),
             *   we should immediately evaluate the result and push it to the numbers queue
             * Addition and subtractions are evaluated after multiplication and division
             */
            if (Utils.isNumber(token)) {
                String mostRecentOperation = operations.peekLast();

                if (!operations.isEmpty() && (mostRecentOperation.equals("*") || mostRecentOperation.equals("/"))) {
                    numbers.add(actions.get(operations.removeLast()).calculate(numbers.removeLast(), Float.parseFloat(token)));
                } else {
                    numbers.add(Float.parseFloat(token));
                }
            }

            /*
             * If the token is not a number value, it might be either an opening bracket of a new inner expression,
             *   or it might be either of (+, -, *, /)
             * If it is an opening bracket, recursively evaluate that inner expression and move the index "i" forward
             *   to the very end of the inner expression (closing bracket) so we don't evaluate the same thing twice
             * If the token is not a bracket (and is an operator instead), add it to the operations queue
             */
            else {
                if (token.equals("(")) {
                    String innerExpression = Utils.extractExpression(values, i);
                    float result = calculate(innerExpression);
                    recursionStack.push(result);

                    i += (Utils.getExpressionTokens(innerExpression).length);
                } else {
                    operations.add(token);
                }
            }
        }

        /*
         * At this point, operations queue contains only either additions or subtractions
         * Multiplications and divisions are calculated immediately
         * While the queue with operations is not empty, perform all the calculations and
         *   add the result to the front of the number queue
         */
        while (!operations.isEmpty()) {
            float top = numbers.removeFirst(), next = numbers.removeFirst();

            numbers.offerFirst(actions.get(operations.removeFirst()).calculate(top, next));
        }

        // The only number remaining in the numbers queue is the expression result
        return numbers.pollFirst();
    }
}