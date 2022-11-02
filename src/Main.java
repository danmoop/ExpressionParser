public class Main {
    public static void main(String[] args) {
        String query = "87+3*75-32-96/55+74+71-0-15/60+69*18/74/92-61-7"; // ~ 355.18
        String bracketQuery = "2+3*(5+(3-3*3))+7/(2-(3*1*5+2-3))"; // -1.5833333

        MathValidator exp = new MathValidator();
        System.out.println(exp.calculate(query));
        System.out.println(exp.calculate(bracketQuery));
    }
}