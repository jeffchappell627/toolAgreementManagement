import org.example.Checkout;
import org.example.RentalAgreement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class JunitTestForAgreementApp {

    @Test
    public void testCheckoutValid() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertEquals("LADW", agreement.getTool().getCode());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
        assertEquals(2, agreement.getChargeDays());  // 3 days minus one holiday
        assertEquals(3.98, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0.40, agreement.getDiscountAmount(), 0.01);
        assertEquals(3.58, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCheckoutInvalidRentalDays() {
        Checkout checkout = new Checkout();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkout.checkout("LADW", 0, 10, LocalDate.of(2020, 7, 2));
        });
        assertEquals("Rental day count must be 1 or greater.", exception.getMessage());
    }

    @Test
    public void testCheckoutInvalidDiscountPercent() {
        Checkout checkout = new Checkout();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkout.checkout("LADW", 3, 101, LocalDate.of(2020, 7, 2));
        });
        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
    }

    @Test
    public void testCheckoutInvalidToolCode() {
        Checkout checkout = new Checkout();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkout.checkout("INVALID_CODE", 3, 10, LocalDate.of(2020, 7, 2));
        });
        assertEquals("Invalid tool code.", exception.getMessage());
    }

    @Test
    public void testCheckoutOnHoliday() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout("CHNS", 5, 25, LocalDate.of(2020, 7, 2));
        assertEquals(3, agreement.getChargeDays());  // Only 1 chargeable day because of holidays and weekends
    }

    @Test
    public void testCheckoutWithNoDiscount() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout("JAKR", 4, 0, LocalDate.of(2020, 7, 2));
        assertEquals(2.99 * 1, agreement.getFinalCharge(), 0.01); // 2 weekdays charged
    }
}