public class Test {
    public static void main(String[] args) {
        System.out.println("Преобразование1");
        int i = 33;
        long start = System.currentTimeMillis();
        String string = String.valueOf(i);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("Преобразование2");
        start = System.currentTimeMillis();
        string = Integer.toString(i);
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("Преобразование3");
        start = System.currentTimeMillis();
        double count = 3 - Math.log10(i);
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
