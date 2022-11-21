public class Main {
    public static void main(String[] args) {
        LogicValidator lvad = new LogicValidator();
        String logicExpression = "true&true"; // false

        System.out.println(lvad.evaluate(logicExpression));
    }
}