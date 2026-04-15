package badsmells;

/*
 * Smell:
 * AuditService directly manipulates BankAccount internals, which creates tight
 * coupling and exposes account implementation details.
 *
 * Refactorings:
 * - Encapsulated BankAccount fields
 * - Moved freezing decision into BankAccount
 * - Exposed intention-revealing methods instead of raw field access
 *
 * Why better:
 * BankAccount now controls its own state and business rules. AuditService no
 * longer depends on internal fields, which improves encapsulation and reduces coupling.
 *
 * Behavior:
 * The observable behavior remains unchanged.
 */
public class InsiderTradingExample {

    static class BankAccount {

        private double balance;
        private String secretFlag;

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public void freezeIfOverdrawn() {
            if (balance < 0) {
                secretFlag = "FROZEN";
            }
        }

        public String getSecretFlag() {
            return secretFlag;
        }
    }

    static class AuditService {

        public void freezeIfNeeded(BankAccount account) {
            account.freezeIfOverdrawn();
        }
    }

    public void clientCode() {
        BankAccount account = new BankAccount();
        account.setBalance(-50);

        AuditService auditService = new AuditService();
        auditService.freezeIfNeeded(account);

        System.out.println(account.getSecretFlag());
    }
}
