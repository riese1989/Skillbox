public class Test {
    public static void main(String[] args) {
        System.out.println("��������������1");
        int i = 33;
        long start = System.currentTimeMillis();
        String string = String.valueOf(i);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("��������������2");
        start = System.currentTimeMillis();
        string = Integer.toString(i);
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("��������������3");
        start = System.currentTimeMillis();
        double count = 3 - Math.log10(i);
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
