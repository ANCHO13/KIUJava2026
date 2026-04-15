package badsmells;

/*
 * Smell:
 * The course title is duplicated across multiple classes, so a change to course
 * wording would require several small edits in different places.
 *
 * Refactorings:
 * - Kept the course title in one source of truth (Course)
 * - Changed Invoice and Certificate to depend on Course instead of storing their own title copy
 *
 * Why better:
 * Course-related wording is now centralized. Changes to the course title no
 * longer require copying data into several classes, which reduces scattered edits.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class ShotgunSurgeryExample {

    static class Course {

        private final String title;

        Course(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String label() {
            return "Course: " + title;
        }
    }

    static class Invoice {

        private final Course course;

        Invoice(Course course) {
            this.course = course;
        }

        public String description() {
            return "Invoice for " + course.getTitle();
        }
    }

    static class Certificate {

        private final Course course;

        Certificate(Course course) {
            this.course = course;
        }

        public String text() {
            return "Completed " + course.getTitle();
        }
    }

    public void clientCode() {
        Course course = new Course("Refactoring");
        Invoice invoice = new Invoice(course);
        Certificate certificate = new Certificate(course);

        System.out.println(course.label());
        System.out.println(invoice.description());
        System.out.println(certificate.text());
    }
}
