import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Tax {
    private final List<TaxSlab> newTax = new ArrayList<>();
    private final List<TaxSlab> oldTax = new ArrayList<>();

    public Tax() {
        newTax.add(new TaxSlab(850, 0.32));
        newTax.add(new TaxSlab(750, 0.28));
        newTax.add(new TaxSlab(650, 0.24));
        newTax.add(new TaxSlab(550, 0.2));
        newTax.add(new TaxSlab(450, 0.16));
        newTax.add(new TaxSlab(350, 0.12));
        newTax.add(new TaxSlab(250, 0.08));
        newTax.add(new TaxSlab(150, 0.04));

        oldTax.add(new TaxSlab(750, 0.18));
        oldTax.add(new TaxSlab(500, 0.12));
        oldTax.add(new TaxSlab(250, 0.06));
    }

    public static void main(String[] args) {
        Tax tax = new Tax();
        IntStream.iterate(50000, i -> i <= 3000000, i -> i + 50000).forEach(i -> {
            String s = i + ", " + tax.getOldTax(i) + ", " + tax.getNewTax(i);
            System.out.println(s);
        });

    }

    public int getOldTax(int salary) {
        return getTax(salary, oldTax);
    }

    public int getNewTax(int salary) {
        return getTax(salary, newTax);
    }

    private int getTax(int amount, List<TaxSlab> slabs) {
        double tax = 0;
        int runningSalary = amount;
        for (TaxSlab slab : slabs) {
            if (runningSalary > slab.minAmount) {
                tax += (runningSalary - slab.minAmount) * slab.taxPercent;
                runningSalary = slab.minAmount;
            }
        }
        return (int) tax;
    }

    static class TaxSlab implements Comparable<TaxSlab> {
        private final int minAmount;
        private final double taxPercent;

        public TaxSlab(int minAmountInThousands, double taxPercent) {
            this.minAmount = minAmountInThousands * 1000;
            this.taxPercent = taxPercent;
        }

        @Override
        public int compareTo(TaxSlab o) {
            return Integer.compare(o.minAmount, minAmount);
        }
    }
}
