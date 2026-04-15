package badsmells;

/*
 * Smell:
 * The comments explain business logic that should be made clear through method
 * structure and naming instead of explanatory inline comments.
 *
 * Refactorings:
 * - Extracted intention-revealing methods
 * - Renamed variables to better reflect meaning
 * - Removed comments that became unnecessary
 *
 * Why better:
 * The code is now easier to understand directly from its structure and names,
 * so the explanatory comments are no longer needed.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class CommentsExample {

    public double finalPrice(double basePrice, boolean vip, int quantity) {
        double discountedPrice = applyVipDiscount(basePrice, vip);
        discountedPrice = applyBulkDiscount(discountedPrice, quantity);
        return applyTax(discountedPrice);
    }

    private double applyVipDiscount(double price, boolean vip) {
        if (vip) {
            return price - price * 0.10;
        }
        return price;
    }

    private double applyBulkDiscount(double price, int quantity) {
        if (quantity > 20) {
            return price - price * 0.05;
        }
        return price;
    }

    private double applyTax(double price) {
        return price + price * 0.18;
    }

    public void clientCode() {
        double vipOrder = finalPrice(120, true, 25);
        double regularOrder = finalPrice(120, false, 5);
        System.out.println(vipOrder);
        System.out.println(regularOrder);
    }
}package badsmells;

/*
 * Smell:
 * The comments explain business logic that should be made clear through method
 * structure and naming instead of explanatory inline comments.
 *
 * Refactorings:
 * - Extracted intention-revealing methods
 * - Renamed variables to better reflect meaning
 * - Removed comments that became unnecessary
 *
 * Why better:
 * The code is now easier to understand directly from its structure and names,
 * so the explanatory comments are no longer needed.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class CommentsExample {

    public double finalPrice(double basePrice, boolean vip, int quantity) {
        double discountedPrice = applyVipDiscount(basePrice, vip);
        discountedPrice = applyBulkDiscount(discountedPrice, quantity);
        return applyTax(discountedPrice);
    }

    private double applyVipDiscount(double price, boolean vip) {
        if (vip) {
            return price - price * 0.10;
        }
        return price;
    }

    private double applyBulkDiscount(double price, int quantity) {
        if (quantity > 20) {
            return price - price * 0.05;
        }
        return price;
    }

    private double applyTax(double price) {
        return price + price * 0.18;
    }

    public void clientCode() {
        double vipOrder = finalPrice(120, true, 25);
        double regularOrder = finalPrice(120, false, 5);
        System.out.println(vipOrder);
        System.out.println(regularOrder);
    }
}
