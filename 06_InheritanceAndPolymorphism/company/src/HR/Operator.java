package HR;

public class Operator extends Employee {


    public Operator(int salary, Company company) {
        super(salary, company);
    }

    @Override
    public Operator clone() {
        return new Operator(salary, company);
    }

    @Override
    public double funcSalary(int salary) {
        return salary;
    }
}