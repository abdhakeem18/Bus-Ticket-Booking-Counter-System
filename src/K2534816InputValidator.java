import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class K2534816InputValidator {
    public static void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }

    public static void validateContact(String contactNumber) {
        requireText(contactNumber, "Contact number");
        if (!contactNumber.matches("\\d{9,12}")) {
            throw new IllegalArgumentException("Contact number must contain 9 to 12 digits.");
        }
    }

    public static LocalDate parseFutureDate(String dateText) {
        try {
            LocalDate date = LocalDate.parse(dateText.trim());
            if (date.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Travel date cannot be in the past.");
            }
            return date;
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Travel date must use yyyy-MM-dd format.");
        }
    }
}
