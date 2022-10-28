import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        String query = "1 + 3 - 2 + 6 - 1 - 2";

        System.out.println(calculate(query));
    }

    static int calculate(String query) {
        StringTokenizer st = new StringTokenizer(query);
        Stack<Integer> numberStack = new Stack<>();
        Stack<String> actionStack = new Stack<>();
        Map<String, Action> actions = new HashMap<>();
        actions.put("+", (a, b) -> a + b);
        actions.put("-", (a, b) -> a - b);

        /*
            Multiplication / Division kinda don't work yet. Multiplication and division should be done first
            However, currently, it calculates things in order from left to right without
            taking order of precedence into account
         */
        actions.put("*", (a, b) -> a * b);
        actions.put("/", (a, b) -> a / b);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (isNumber(token)) {
                int val = Integer.parseInt(token);

                if (!actionStack.isEmpty()) {
                    String action = actionStack.pop();
                    int calc = actions.get(action).calculate(numberStack.pop(), val);

                    numberStack.push(calc);
                } else {
                    numberStack.push(val);
                }
            } else {
                actionStack.push(token);
            }
        }

        return numberStack.peek();
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