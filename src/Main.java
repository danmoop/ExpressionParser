public class Main {
    public static void main(String[] args) {
        MathValidator exp = new MathValidator();
        String mathExpression = "7+12/(32-(11-7/4*6-4)/4/5*7)*11";
        System.out.println(exp.calculate(mathExpression)); // ~ 10.972

        LogicValidator lvad = new LogicValidator();
        String logicExpression = "false&(false&(false|(true|false&false)))"; // false

        System.out.println(lvad.evaluate(logicExpression));
    }
}