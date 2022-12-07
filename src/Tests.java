import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/*
 * This class is used to perform tests to make sure the algorithm is correct
 * To do so, we calculate the result using our algorithm and compare results with JavaScript engine evaluation
 */

public class Tests {
    public static void main(String[] args) throws ScriptException {
        // Generate strings to see if math validations are correct
        testMathValidator(1000);

        // Generate strings to see if logic validations are correct
        testBooleanValidator(1000);
    }

    private static boolean testBooleanValidator(int n) throws ScriptException {
        LogicValidator lvad = new LogicValidator();
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        for (int i = 0; i < n; i++) {
            // Generate expressions of length "1 - 32" minimum
            String str = generateRandomBooleanExpression((int) Math.ceil(Math.random() * 12));

            // Evaluate using our Java logic
            boolean javaValue = lvad.evaluate(str);

            // Evaluate using JavaScript (gives correct result)
            str = str.replace("&", "&&");
            str = str.replace("|", "||");
            boolean jsValue = String.valueOf(engine.eval(str)).equals("true");

            System.out.println(str);
            System.out.println("✔️" + (i + 1) + ": " + jsValue + " == " + javaValue);
            System.out.println("___________________________");

            if (jsValue != javaValue) return false;
        }

        return true;
    }

    private static boolean testMathValidator(int n) throws ScriptException {
        MathValidator exp = new MathValidator();
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        for (int i = 0; i < n; i++) {
            // Generate expressions of length "1 - 32" minimum
            String str = generateRandomMathExpression((int) Math.ceil(Math.random() * 32));

            // Evaluate using JavaScript (gives correct result)
            float jsValue = Float.parseFloat(String.valueOf(engine.eval(str)));

            // Evaluate using our Java logic
            float javaValue = exp.calculate(str);

            System.out.println(str);
            System.out.println("✔️" + (i + 1) + ": " + jsValue + " == " + javaValue);
            System.out.println("___________________________");

            // If difference is small, it means both values are equal, so calculations are correct
            if (Math.abs(jsValue - javaValue) > 1) return false;
        }

        return true;
    }

    private static String generateRandomBooleanExpression(int length) {
        StringBuilder sb = new StringBuilder();
        String[] actions = {"&", "|"};

        for (int i = 0; i < length; i++) {
            // Append random boolean and operator to the test expression
            if (Math.round(Math.random() * 100) < 20) {
                sb.append("!");
            }
            sb.append(Math.round(Math.random() * 100) <= 50);
            sb.append(actions[(int) Math.floor(Math.random() * actions.length)]);

            // Generate expressions inside expression with 6% chance.
            if (Math.round(Math.random() * 100) < 6) {
                sb.append("(");
                sb.append(generateRandomBooleanExpression(6));
                sb.append(")");
                sb.append(actions[(int) Math.floor(Math.random() * actions.length)]);
            }
        }
        sb.append(Math.round(Math.random() * 100) <= 50);

        return sb.toString();
    }

    private static String generateRandomMathExpression(int length) {
        StringBuilder sb = new StringBuilder();
        String[] actions = {"+", "-", "*", "/"};

        for (int i = 0; i < length; i++) {
            // Append random number and operator to the test expression
            sb.append(Math.round(Math.random() * 5) + 1);
            sb.append(actions[(int) Math.floor(Math.random() * actions.length)]);

            // Generate expressions inside expression with 6% chance.
            if (Math.round(Math.random() * 100) < 6) {
                sb.append("(");
                sb.append(generateRandomMathExpression(6));
                sb.append(")");
                sb.append(actions[(int) Math.floor(Math.random() * actions.length)]);
            }
        }
        sb.append(Math.round(Math.random() * 5) + 1);

        return sb.toString();
    }
}