import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Random;

public class Bank {
    private HashMap<String, Account> accounts;
    private final Random random = new Random();

    public synchronized boolean isFraud(Account from, Account to, long amount)
            throws InterruptedException {
        boolean flag = false;
        if(from.getMoney() / 2 <= amount)   {
            from.setBlock(true);
            to.setBlock(true);
            flag = true;
        }
        Thread.sleep(1000);
        return flag;
    }

    public void setAccounts(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public boolean transfer(String fromAccountNum, String toAccountNum, long amount, int i) throws InterruptedException {
        System.out.println(i);
        Account accFrom = accounts.get(fromAccountNum);
        Account accTo = accounts.get(toAccountNum);
        if (accFrom == accTo)   {
            //System.out.println("Нельза переводить деньги между одним счётом");
            return false;
        }
        if (accFrom.getMoney() < amount)    {
            //System.out.println("На счете недостаточно средств");
            return false;
        }
        if (accFrom.getBlock() || accTo.getBlock()) {
            //System.out.println("Нельзя проводить операции с заблокированными счетами");
            return false;
        }
        if (amount > 500 && isFraud(accFrom, accTo, amount)) {
            System.out.println("Счета " + accFrom.getAccNumber() + " и " + accTo.getAccNumber() + " заблокированы");
            return false;
        }
        accFrom.setMoney(accFrom.getMoney() - amount);
        accTo.setMoney(accTo.getMoney() + amount);
        //System.out.println("Операция проведена");
        return true;
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        Account account = accounts.get(accountNum);
        return account.getMoney();
    }
}
