public class Account
{
    private long money;
    private String accNumber;
    private boolean block = false;

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

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
