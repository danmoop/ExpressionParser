import java.util.*;

public class LogicValidator {
    private Map<String, BooleanExpression> actions;

    public LogicValidator() {
        actions = new HashMap<>();

        // Add lambda expressions to the map to easily access the operator logic
        actions.put("&", (a, b) -> a && b);
        actions.put("|", (a, b) -> a || b);
    }

    public boolean evaluate(String query) {
        // This one contains the array of values and operators separated
        String[] values = Utils.getLogicExpressionTokens(query);

        // Double linked list containing all booleans and evaluations
        Deque<Boolean> booleans = new LinkedList<>();

        // Double linked list containing all operators (& and |)
        Deque<String> operations = new LinkedList<>();

        // Stack contains any calculation results from inner expressions, which should be added to the booleans queue
        Stack<Boolean> recursionStack = new Stack<>();

        boolean shouldNegate = false;

        for (int i = 0; i < values.length; i++) {
            /*
             * token is a single element from the original expression we want to evaluate
             * values is an array of elements separated, so it might contain either a boolean or an operator
             * if there are some results from the recursion evaluation, take that one instead and process it
             *   since it is the most recent evaluation
             */
            String token = recursionStack.isEmpty() ? values[i] : String.valueOf(recursionStack.pop());

            /*
             * If the token is a boolean, we should see if there are any operations present we added before
             * If there is a high-precedence operator currently sitting in a queue (AND),
             *   we should immediately evaluate the result and push it to the booleans queue
             * Order of precedence: NOT -> AND -> OR
             */
            if (Utils.isBoolean(token)) {
                String mostRecentOperation = operations.peekLast();

                boolean bool = Boolean.parseBoolean(token);
                if (shouldNegate) {
                    bool = !bool;
                    shouldNegate = false;
                }

                if (!operations.isEmpty() && (mostRecentOperation.equals("&"))) {
                    booleans.add(actions.get(operations.removeLast()).evaluate(booleans.removeLast(), bool));
                } else {
                    booleans.add(bool);
                }
            }

            /*
             * If the token is not a boolean value, it might be either an opening bracket of a new inner expression,
             *   or it might be either of (& or |)
             * If it is an opening bracket, recursively evaluate that inner expression and move the index "i" forward
             *   to the very end of the inner expression (closing bracket) so we don't evaluate the same thing twice
             * If the token is not a bracket (and is an operator instead), add it to the operations queue
             */
            else {
                if (token.equals("(")) {
                    String innerExpression = Utils.extractExpression(values, i);
                    boolean result = evaluate(innerExpression);
                    recursionStack.push(result);

                    i += (Utils.getLogicExpressionTokens(innerExpression).length);
                } else if (token.equals("!")) {
                    shouldNegate = true;
                } else {
                    operations.add(token);
                }
            }
        }

        /*
         * At this point, operations queue contains only either "AND" or "OR" operations
         * While the queue with operations is not empty, perform all the evaluations and
         *   add the result to the front of the booleans queue
         */
        while (!operations.isEmpty()) {
            boolean top = booleans.removeFirst(), next = booleans.removeFirst();

            booleans.offerFirst(actions.get(operations.removeFirst()).evaluate(top, next));
        }

        // The only boolean remaining in the booleans queue is the expression result
        return booleans.pollFirst();
    }
}