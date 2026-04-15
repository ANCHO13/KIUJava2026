package badsmells;

/*
 * Smell:
 * StudentRecord only stores data while behavior that depends on it is located
 * in separate classes. This creates a weak and passive data structure.
 *
 * Refactorings:
 * - Moved behavior into StudentRecord
 * - Encapsulated fields by making them private
 *
 * Why better:
 * Data and related behavior are now together, improving cohesion and making
 * the design more object-oriented and easier to maintain.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class DataClassExample {

    public static class StudentRecord {

        private final String name;
        private final int credits;
        private final double gpa;

        public StudentRecord(String name, int credits, double gpa) {
            this.name = name;
            this.credits = credits;
            this.gpa = gpa;
        }

        public boolean isEligibleForHonors() {
            return credits >= 30 && gpa >= 3.7;
        }

        public double discountPercent() {
            if (gpa >= 3.8) {
                return 0.15;
            }
            if (gpa >= 3.5) {
                return 0.10;
            }
            return 0.0;
        }

        public String describeStanding() {
            if (gpa < 2.0) {
                return name + " is on academic probation";
            }
            if (credits < 15) {
                return name + " is a new student";
            }
            return name + " is in good standing";
        }
    }

    public void clientCode() {
        StudentRecord student = new StudentRecord("Nino", 32, 3.8);

        System.out.println(student.isEligibleForHonors());
        System.out.println(student.discountPercent());
        System.out.println(student.describeStanding());
    }
}
