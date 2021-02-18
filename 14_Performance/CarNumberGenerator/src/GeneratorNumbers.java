public class GeneratorNumbers implements Runnable {
    public static final int COUNT_NUMBERS = 999;
    public static final char[] LETTERS = {'Ó', 'Ê', 'Å', 'Í', 'Õ', 'Â', 'À', 'Ð', 'Î', 'Ñ', 'Ì', 'Ò'};
    public static final int SIZE_BLOCK = 1000;

    private int startRegion;
    private int lastRegion;

    public GeneratorNumbers(int startRegion) {
        this.startRegion = startRegion;
        this.lastRegion = startRegion + 9;
    }

    @Override
    public void run() {
        Writer writer = new Writer(startRegion);
        StringBuilder builder = new StringBuilder();
        for (int regionCode = startRegion; regionCode <= lastRegion; regionCode++) {
            for (int number = 1; number <= COUNT_NUMBERS; number++) {
                for (char firstLetter : LETTERS) {
                    for (char secondLetter : LETTERS) {
                        for (char thirdLetter : LETTERS) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
                            if (builder.length() > SIZE_BLOCK)    {
                                writer.write(builder.toString());
                                builder = new StringBuilder();
                            }
                        }
                    }
                }
            }
        }
        if (builder.length() > 0) {
            writer.write(builder.toString());
        }
        writer.stopWrite();
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }
}
