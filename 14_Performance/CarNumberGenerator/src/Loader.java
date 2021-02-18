import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Loader {
    private static final int COUNT_REGIONS = 100;
    private static final int COUNT_NUMBERS = 999;
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        char[] letters = {'Ó', 'Ê', 'Å', 'Í', 'Õ', 'Â', 'À', 'Ð', 'Î', 'Ñ', 'Ì', 'Ò'};
        StringBuilder builder = new StringBuilder();
        for (int regionCode = 0; regionCode < COUNT_REGIONS; regionCode++) {
            for (int number = 1; number <= COUNT_NUMBERS; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
                            if (builder.length() > 1000) {
                                Writer writer1 = new Writer(builder.toString());
                                writer1.run();
                                builder = new StringBuilder();
                            }
                        }
                    }
                }
            }
        }
        Writer.close();
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for(int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }
}
