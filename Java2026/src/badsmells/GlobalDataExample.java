package badsmells;

/*
 * Smell:
 * The semester and tuition values are stored in public static fields, which
 * makes them globally accessible and easy to modify from anywhere. This weakens
 * encapsulation and makes state changes harder to control and trace.
 *
 * Refactorings:
 * - Encapsulated the global state behind methods
 * - Moved the shared data into a dedicated configuration object
 *
 * Why better:
 * The state now has clear ownership and controlled access. Changes go through
 * explicit methods, which improves encapsulation and makes the design safer.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class GlobalDataExample {

    private final UniversityConfiguration configuration = new UniversityConfiguration();

    static class UniversityConfiguration {
        private String currentSemester = "SPRING";
        private double tuitionRate = 1250.0;

        public String getCurrentSemester() {
            return currentSemester;
        }

        public double getTuitionRate() {
            return tuitionRate;
        }

        public void openFallSemester() {
            currentSemester = "FALL";
        }

        public void increaseTuitionRate(double delta) {
            tuitionRate += delta;
        }
    }

    class BillingService {
        public double calculateInvoice(int credits) {
            return credits * configuration.getTuitionRate();
        }
    }

    class SemesterAdministration {
        public void openFallSemester() {
            configuration.openFallSemester();
        }

        public void approveRateIncrease() {
            configuration.increaseTuitionRate(100);
        }
    }

    public void applyEmergencyIncrease(double delta) {
        configuration.increaseTuitionRate(delta);
    }

    public void clientCode() {
        BillingService billingService = new BillingService();
        SemesterAdministration administration = new SemesterAdministration();

        System.out.println(configuration.getCurrentSemester());
        System.out.println(billingService.calculateInvoice(3));
        administration.openFallSemester();
        administration.approveRateIncrease();
        System.out.println(configuration.getCurrentSemester());
        System.out.println(billingService.calculateInvoice(3));
    }
}
