package badsmells;

/*
 * Smell:
 *
 * The summer and winter invoice methods duplicate the same tax, shipping,
 * and total calculation logic. This makes the code harder to maintain because
 * shared pricing rules must be updated in more than one place.
 *
 * Refactorings:
 * - Extracted the shared invoice calculation into one helper method.
 * - Kept only the seasonal discount logic separate.
 *
 * Why better:
 * The common pricing behavior is now defined in one place, which reduces
 * duplication and makes future changes safer and easier.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class DuplicatedCodeExample {

    public double summerInvoice(double subtotal) {
        double discount = subtotal > 200 ? subtotal * 0.10 : 0;
        return calculateInvoiceTotal(subtotal, discount);
    }

    public double winterInvoice(double subtotal) {
        double discount = subtotal > 200 ? subtotal * 0.20 : 50;
        return calculateInvoiceTotal(subtotal, discount);
    }

    private double calculateInvoiceTotal(double subtotal, double discount) {
        double tax = subtotal * 0.18;
        double shipping = subtotal > 100 ? 0 : 15;
        return subtotal + tax + shipping - discount;
    }

    public void clientCode() {
        System.out.println(summerInvoice(240));
        System.out.println(winterInvoice(240));
    }
}
