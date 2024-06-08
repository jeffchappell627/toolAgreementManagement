package org.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        agreement.print();
    }
}