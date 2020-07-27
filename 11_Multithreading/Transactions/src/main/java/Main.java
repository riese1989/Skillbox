import java.util.HashMap;

public class Main {
    static Bank bank = new Bank();

    public static void main(String[] args) throws InterruptedException {
        bank.setAccounts(generationAccounts(200));
        generateTransactions(800000);
    }

    private static HashMap<String, Account> generationAccounts(int count) {
        HashMap<String, Account> hash = new HashMap<String, Account>();
        for (Integer i = 1; i <= count; i++) {
            Account account = new Account((long) (Math.random() * Long.MAX_VALUE), i.toString());
            hash.put(i.toString(), account);
        }
        return hash;
    }

    private static void generateTransactions(int count) throws InterruptedException {
        Integer size = bank.getAccounts().size();
        for (int i = 1; i <= count; i++) {
            String accFrom = generateNumber(size);
            String accTo = generateNumber(size);
            long amount = (long) (10000 * Math.random()*2);
            Thread thread = new Thread(String.valueOf(bank.transfer(accFrom, accTo, amount, i)));
            thread.start();
        }
    }

    private static String generateNumber (Integer size) {
        double rendNumber = size * Math.random();
        Integer number = (int) Math.ceil(rendNumber);
        return number.toString();
    }
}
