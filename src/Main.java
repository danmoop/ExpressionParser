public class Main {
    public static void main(String[] args) {
        String query = "87+3*75-32-96/55+74+71-0-15/60+69*18/74/92-61-7"; // ~ 355.18
        String bracketQuery = "7-3+8+(3-6*3*2)+2-3*3/5+6"; // 78.2, doesn't work yet

        MathValidator exp = new MathValidator();
        System.out.println(exp.calculate(bracketQuery));
    }
}