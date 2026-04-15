package badsmells;

/*
 * Smell:
 * The processOrder method performs multiple distinct tasks in one place:
 * discount calculation, shipping calculation, tax calculation, approval
 * decision, and summary formatting. This makes the method harder to read,
 * test, and modify.
 *
 * Refactorings:
 * - Extracted methods for discount, shipping, tax, and approval logic
 * - Extracted summary formatting into a separate method
 *
 * Why better:
 * Each method now has a single clear responsibility, which improves
 * readability and makes future changes safer and more localized.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class LongFunctionExample {

    public String processOrder(String customerType, int quantity, double price, boolean expressDelivery) {
        double subtotal = calculateSubtotal(quantity, price);
        double discount = calculateDiscount(customerType, subtotal);
        double shipping = calculateShipping(quantity, expressDelivery);
        double tax = calculateTax(subtotal, discount);
        double total = calculateTotal(subtotal, discount, shipping, tax);
        String status = determineStatus(total);

        return buildSummary(customerType, quantity, price, subtotal, discount, shipping, tax, total, status);
    }

    private double calculateSubtotal(int quantity, double price) {
        return quantity * price;
    }

    private double calculateDiscount(String customerType, double subtotal) {
        if ("STUDENT".equals(customerType)) {
            return subtotal * 0.05;
        } else if ("VIP".equals(customerType)) {
            return subtotal * 0.12;
        } else if ("EMPLOYEE".equals(customerType)) {
            return subtotal * 0.20;
        }
        return 0;
    }

    private double calculateShipping(int quantity, boolean expressDelivery) {
        double shipping;
        if (expressDelivery) {
            shipping = 25;
            if (quantity > 10) {
                shipping += 10;
            }
        } else {
            shipping = 10;
            if (quantity > 10) {
                shipping += 5;
            }
        }
        return shipping;
    }

    private double calculateTax(double subtotal, double discount) {
        return (subtotal - discount) * 0.18;
    }

    private double calculateTotal(double subtotal, double discount, double shipping, double tax) {
        return subtotal - discount + shipping + tax;
    }

    private String determineStatus(double total) {
        if (total > 500) {
            return "MANAGER_APPROVAL";
        } else if (total > 200) {
            return "FINANCE_REVIEW";
        }
        return "AUTO_APPROVED";
    }

    private String buildSummary(String customerType, int quantity, double price,
                                double subtotal, double discount, double shipping,
                                double tax, double total, String status) {
        StringBuilder summary = new StringBuilder();
        summary.append("customerType=").append(customerType).append('\n');
        summary.append("quantity=").append(quantity).append('\n');
        summary.append("price=").append(price).append('\n');
        summary.append("subtotal=").append(subtotal).append('\n');
        summary.append("discount=").append(discount).append('\n');
        summary.append("shipping=").append(shipping).append('\n');
        summary.append("tax=").append(tax).append('\n');
        summary.append("total=").append(total).append('\n');
        summary.append("status=").append(status);
        return summary.toString();
    }

    public void clientCode() {
        System.out.println(processOrder("VIP", 12, 30, true));
        System.out.println(processOrder("STUDENT", 2, 50, false));
    }
}
