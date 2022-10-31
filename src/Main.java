public class Main {
    public static void main(String[] args) {
        String query = "10-3/1+2+3-2*3*5+7/3/2/5*32+1/3+3+9-2*5*1*2-3+23";

        ExpressionValidator exp = new ExpressionValidator();
        System.out.println(exp.calculate(query));
    }
}