package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    Tool tool;
    int rentalDays;


    LocalDate checkoutDate;
    LocalDate dueDate;
    int chargeDays;
    double preDiscountCharge;
    int discountPercent;
    double discountAmount;
    double finalCharge;


    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(double preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }

        public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, int discountPercent) {
            this.tool = tool;
            this.rentalDays = rentalDays;
            this.checkoutDate = checkoutDate;
            this.dueDate = checkoutDate.plusDays(rentalDays);
            this.discountPercent = discountPercent;
            this.chargeDays = calculateChargeDays();
            this.preDiscountCharge = chargeDays * tool.dailyCharge;
            this.discountAmount = Math.round((preDiscountCharge * discountPercent / 100.0) * 100.0) / 100.0;
            this.finalCharge = Math.round((preDiscountCharge - discountAmount) * 100.0) / 100.0;
        }

        private int calculateChargeDays() {
            int days = 0;
            LocalDate currentDate = checkoutDate.plusDays(1);
            while (!currentDate.isAfter(dueDate)) {
                if (isChargeable(currentDate)) {
                    days++;
                }
                currentDate = currentDate.plusDays(1);
            }
            return days;
        }

        private boolean isChargeable(LocalDate date) {
            if (HolidayUtility.isHoliday(date)) {
                return tool.holidayCharge;
            } else if (date.getDayOfWeek().getValue() >= 6) {
                return tool.weekendCharge;
            } else {
                return tool.weekdayCharge;
            }
        }

        public void print() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            System.out.printf("Tool code: %s\n", tool.code);
            System.out.printf("Tool type: %s\n", tool.type);
            System.out.printf("Tool brand: %s\n", tool.brand);
            System.out.printf("Rental days: %d\n", rentalDays);
            System.out.printf("Check out date: %s\n", checkoutDate.format(formatter));
            System.out.printf("Due date: %s\n", dueDate.format(formatter));
            System.out.printf("Daily rental charge: $%.2f\n", tool.dailyCharge);
            System.out.printf("Charge days: %d\n", chargeDays);
            System.out.printf("Pre-discount charge: $%.2f\n", preDiscountCharge);
            System.out.printf("Discount percent: %d%%\n", discountPercent);
            System.out.printf("Discount amount: $%.2f\n", discountAmount);
            System.out.printf("Final charge: $%.2f\n", finalCharge);
        }
    }