import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Tests {
    public static void main(String[] args) throws ScriptException {
        // Generate 1000 strings to see if validations are correct
        System.out.println(test(1000));
    }

    private static boolean test(int n) throws ScriptException {
        MathValidator exp = new MathValidator();
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        for (int i = 0; i < n; i++) {
            // Generate expressions of length "32" minimum
            String str = generate(32);

            // Evaluate using JavaScript (gives correct result)
            float jsValue = Float.parseFloat(String.valueOf(engine.eval(str)));

            // Evaluate using our Java logic
            float javaValue = exp.calculate(str);

            System.out.println(str);
            System.out.println("✔️" + (i + 1) + ": " + jsValue + ": " + javaValue);
            System.out.println("___________________________");

            // If difference is small, it means both values are equal, so calculations are correct
            if (Math.abs(jsValue - javaValue) > 1) return false;
        }

        return true;
    }

    private static String generate(int length) {
        StringBuilder sb = new StringBuilder();
        String[] actions = {"+", "-", "*", "/"};

        for (int i = 0; i < length; i++) {
            // Append random number and operator to the test expression
            sb.append(Math.round(Math.random() * 5) + 1);
            sb.append(actions[(int) Math.floor(Math.random() * actions.length)]);

            // Generate expressions inside expression with 5% chance.
            if (Math.round(Math.random() * 100) < 5) {
                sb.append("(");
                sb.append(generate(6));
                sb.append(")");
                sb.append(actions[(int) Math.floor(Math.random() * actions.length)]);
            }
        }
        sb.append(Math.round(Math.random() * 5) + 1);

        return sb.toString();
    }
}