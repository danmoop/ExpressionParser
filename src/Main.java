public class Main {
    public static void main(String[] args) {
        MathValidator exp = new MathValidator();
        System.out.println(exp.calculate("7+12/(32-(11-7/4*6-4)/4/5*7)*11")); // 10.972911963882618
    }
}