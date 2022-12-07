public class Main {
    public static void main(String[] args) {
        MathValidator mvad = new MathValidator();
        LogicValidator lvad = new LogicValidator();

        String m1 = "3-5-2+2-5+5";
        String m2 = "5+6+2+6+4/6+5-3/3/1*5*4*4*(2+5/3*6*5+6+4)*3-4-6-4*5";

        String l1 = "false&true";
        String l2 = "true|true|(false|true&!true|false&true&false|true)&false";

        System.out.println(mvad.calculate(m1));
        System.out.println(mvad.calculate(m2));

        System.out.println(lvad.evaluate(l1));
        System.out.println(lvad.evaluate(l2));
    }
}