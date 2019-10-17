package seedu.billboard.model.expense;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Represents an Expense's amount in Billboard.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain a float number and it should not be blank";
    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance();

    //TODO: Add parsing money logic
    public final float amount;

    public Amount(String amount) {
        try {
            System.out.println(numberFormatter.parse(amount).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.amount = Float.parseFloat(amount);
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        try {
            numberFormatter.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Adds this amount to another amount.
     * @param other Amount to be added.
     * @return new Amount representing the combine total of the two amounts.
     */
    public Amount add(Amount other) {
        return new Amount((amount + other.amount) + "");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount == ((Amount) other).amount); // state check
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return "S$" + decimalFormat.format(amount);
    }
}
