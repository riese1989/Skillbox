public class Account
{
    private long money;
    private long beforeTransactionMoney;
    private String accNumber;
    private boolean block = false;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        beforeTransactionMoney = money;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean getBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public long getBeforeTransactionMoney() {
        return beforeTransactionMoney;
    }

    public void setBeforeTransactionMoney() {
        this.beforeTransactionMoney = money;
    }

    public boolean compareMoney(long diff)   {
        if (Math.abs(beforeTransactionMoney - money) == diff)   {
            return true;
        }
        return false;
    }
}
