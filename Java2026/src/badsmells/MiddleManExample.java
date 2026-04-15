package badsmells;

/*
 * Smell:
 * StudentPortal acts only as a middle man by forwarding calls to
 * TranscriptService without adding any behavior.
 *
 * Refactorings:
 * - Removed the unnecessary StudentPortal class
 * - Let the client interact directly with TranscriptService
 *
 * Why better:
 * The unnecessary layer of indirection is removed, making the code simpler and
 * easier to understand.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class MiddleManExample {

    static class TranscriptService {

        public String findGrade(String studentId) {
            return "A";
        }
    }

    public void clientCode() {
        TranscriptService service = new TranscriptService();
        System.out.println(service.findGrade("s-1001"));
    }
}
