package badsmells;

/*
 * Smell:
 * The StudentNameFormatter class adds no meaningful abstraction and only wraps
 * a simple trim operation. This makes the design unnecessarily complex.
 *
 * Refactorings:
 * - Inlined the formatter logic into the client
 * - Removed the unnecessary class
 *
 * Why better:
 * The code is now simpler and avoids an unnecessary layer of abstraction.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class LazyElementExample {

    public void clientCode() {
        System.out.println("  Nino  ".trim());
    }
}
