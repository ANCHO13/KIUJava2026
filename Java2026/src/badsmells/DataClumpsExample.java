package badsmells;

/*
 * Smell:
 * The same related group of values (name, email, and phone) appears together
 * repeatedly across multiple methods and callers. This indicates a missing
 * concept in the design.
 *
 * Refactorings:
 * - Introduced a ContactInfo class for name, email, and phone
 * - Moved related behavior onto the ContactInfo object
 *
 * Why better:
 * The repeated data clump is now modeled as one coherent object. This improves
 * readability, reduces duplication, and keeps related data and behavior together.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class DataClumpsExample {

    static class ContactInfo {
        private final String name;
        private final String email;
        private final String phone;

        ContactInfo(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public String buildLabel() {
            return name + " <" + email + ">, phone: " + phone;
        }

        public String buildEmailGreeting() {
            return "To: " + email + ", hello " + name;
        }

        public String buildSmsMessage() {
            return "SMS to " + phone + ": Hi " + name;
        }

        public boolean isReachable() {
            return email != null && !email.trim().isEmpty()
                    && phone != null && !phone.trim().isEmpty();
        }
    }

    public void clientCode() {
        ContactInfo student = new ContactInfo("Nino", "nino@example.com", "+995-555-000-001");
        ContactInfo advisor = new ContactInfo("Giorgi", "giorgi@example.com", "+995-555-000-002");
        ContactInfo accountant = new ContactInfo("Maka", "maka@example.com", "+995-555-000-003");

        String studentLabel = student.buildLabel();
        String studentGreeting = student.buildEmailGreeting();
        String studentSms = student.buildSmsMessage();
        boolean studentReachable = student.isReachable();

        String advisorLabel = advisor.buildLabel();
        String advisorGreeting = advisor.buildEmailGreeting();
        String advisorSms = advisor.buildSmsMessage();
        boolean advisorReachable = advisor.isReachable();

        String accountantLabel = accountant.buildLabel();
        String accountantGreeting = accountant.buildEmailGreeting();
        String accountantSms = accountant.buildSmsMessage();
        boolean accountantReachable = accountant.isReachable();

        System.out.println(studentLabel);
        System.out.println(studentGreeting);
        System.out.println(studentSms);
        System.out.println(studentReachable);

        System.out.println(advisorLabel);
        System.out.println(advisorGreeting);
        System.out.println(advisorSms);
        System.out.println(advisorReachable);

        System.out.println(accountantLabel);
        System.out.println(accountantGreeting);
        System.out.println(accountantSms);
        System.out.println(accountantReachable);
    }
}
