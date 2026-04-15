package badsmells;

/*
 * Smell:
 * The interface includes parameters for future use that are not currently used.
 * This adds unnecessary complexity and makes the design harder to understand.
 *
 * Refactorings:
 * - Removed unused parameters
 * - Simplified the interface to match actual requirements
 *
 * Why better:
 * The code now reflects real usage instead of hypothetical scenarios, making it
 * simpler and easier to maintain.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class SpeculativeGeneralityExample {

    interface NotificationChannel {
        void send(String message);
    }

    static class EmailChannel implements NotificationChannel {

        @Override
        public void send(String message) {
            System.out.println(message);
        }
    }

    public void clientCode() {
        NotificationChannel channel = new EmailChannel();
        channel.send("Exam starts at 10:00");
    }
}
