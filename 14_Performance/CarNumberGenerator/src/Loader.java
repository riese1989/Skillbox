public class Loader {
    private static final int COUNT_REGIONS = 100;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int regionCode = 0; regionCode < COUNT_REGIONS; regionCode += 9) {
            GeneratorNumbers generatorNumbers = new GeneratorNumbers(regionCode);
            generatorNumbers.run();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
