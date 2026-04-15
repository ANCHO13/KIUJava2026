package badsmells;

/*
 * Smell:
 * The ScholarshipCalculator class contains logic that primarily uses data from
 * StudentAccount, which indicates feature envy. The behavior belongs closer to
 * the data it operates on.
 *
 * Refactorings:
 * - Moved the qualification logic into StudentAccount
 * - Removed unnecessary external calculator class
 *
 * Why better:
 * The logic is now located with the data it depends on, improving cohesion and
 * making the design easier to understand and maintain.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class FeatureEnvyExample {

    static class StudentAccount {

        private final int completedCredits;
        private final double gpa;

        StudentAccount(int completedCredits, double gpa) {
            this.completedCredits = completedCredits;
            this.gpa = gpa;
        }

        public int getCompletedCredits() {
            return completedCredits;
        }

        public double getGpa() {
            return gpa;
        }

        public boolean qualifiesForScholarship() {
            return completedCredits >= 30 && gpa >= 3.7;
        }
    }

    public void clientCode() {
        StudentAccount account = new StudentAccount(36, 3.9);
        System.out.println(account.qualifiesForScholarship());
    }
}
