package com.charakhovich.club.web.validation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.testng.Assert.*;

public class DataValidateTest {

    @Test(dataProvider = "dataForIsAddBalanceAmountValid")
    public void testIsAddBalanceAmountValid(BigDecimal amount, boolean expected) {
        boolean actual = DataValidate.isAddBalanceAmountValid(amount);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "dataForIsAddBalanceAmountValid")
    public Object[][] dataForIsLoginValid() {
        return new Object[][]{
                {new BigDecimal(0.5), true},
                {new BigDecimal(100), true},
                {new BigDecimal(0.2), false},
                {new BigDecimal(101), false},
                {new BigDecimal(0), false},
                {new BigDecimal(-1), false}
        };
    }

    @Test(dataProvider = "dataForIsValidLocalDateTime")
    public void testIsValidEventDate(LocalDateTime localDateTime, boolean expected) {
        boolean actual = DataValidate.isValidEventDate(localDateTime);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "dataForIsValidLocalDateTime")
    public Object[][] dataForIsValidLocalDateTime() {
        return new Object[][]{
                {LocalDateTime.now(), false},
                {LocalDateTime.now().plusDays(1), true},
                {LocalDateTime.now().minusDays(1), false},
        };
    }

    @Test(dataProvider = "dataForIsValidLocalDate")
    public void testTestIsValidEventDate(LocalDate localDate, boolean expected) {
        boolean actual = DataValidate.isValidEventDate(localDate);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "dataForIsValidLocalDate")
    public Object[][] dataForIsValidLocalDate() {
        return new Object[][]{
                {LocalDate.now(), false},
                {LocalDate.now().plusDays(1), true},
                {LocalDate.now().minusDays(1), false},
        };
    }

    @Test(dataProvider = "dataForIsValidCostTicket")
    public void testIsValidCostTicket(BigDecimal cost,boolean expected) {
        boolean actual = DataValidate.isValidCostTicket(cost);
        assertEquals(actual, expected);
    }
    @DataProvider(name = "dataForIsValidCostTicket")
    public Object[][] dataForIsValidCostTicket() {
        return new Object[][]{
                {new BigDecimal(5), true},
                {new BigDecimal(50), true},
                {new BigDecimal(25), true},
                {new BigDecimal(50.01), false},
                {new BigDecimal(101), false},
                {new BigDecimal(0), false},
                {new BigDecimal(-1), false}
        };
    }

    @Test(dataProvider = "dataForIsValidCountTicket")
    public void testIsValidCountTicket(int count,boolean expected) {
        boolean actual = DataValidate.isValidCountTicket(count);
        assertEquals(actual, expected);
    }
    @DataProvider(name = "dataForIsValidCountTicket")
    public Object[][] dataForIsValidCountTicket() {
        return new Object[][]{
                {1, true},
                {10, true},
                {-1, false},
                {30, true},
                {101, false},
                {0, false}

        };
    }

    @Test(dataProvider = "dataForIsIsValidEventTime")
    public void testIsValidEventTime(LocalTime localTime,boolean expected) {
        boolean actual = DataValidate.isValidEventTime(localTime);
        assertEquals(actual, expected);

    }
    @DataProvider(name = "dataForIsIsValidEventTime")
    public Object[][] dataForIsIsValidEventTime() {
        return new Object[][]{
                {LocalTime.of(8, 00), false},
                {LocalTime.of(9, 00), true},
                {LocalTime.of(20, 00), true},
                {LocalTime.of(21, 00), false}
        };
    }


    @Test(dataProvider = "dataForIsValidEventTimeFromDateTime")
    public void testTestIsValidEventTime(LocalDateTime localDateTime,boolean expected) {
        boolean actual = DataValidate.isValidEventTime(localDateTime);
        assertEquals(actual, expected);
    }
    @DataProvider(name = "dataForIsValidEventTimeFromDateTime")
    public Object[][] dataForIsValidEventTimeFromDateTime() {
        return new Object[][]{
                {LocalDateTime.parse("2018-11-03T08:45:30"), false},
                {LocalDateTime.parse("2022-11-03T09:45:30"), true},
                {LocalDateTime.parse("2022-11-03T20:00:00"), true},
                {LocalDateTime.parse("2022-11-03T21:00:00"),false}

        };
    }
    @Test(dataProvider = "dataForIsValidMessage")
    public void testIsValidMessage(String str,boolean expected) {
        boolean actual = DataValidate.isValidMessage(str);
        assertEquals(actual, expected);

    }
    @DataProvider(name = "dataForIsValidMessage")
    public Object[][] dataForIsValidMessage() {
        return new Object[][]{
                {" ", false},
                {"   ", false},
                {"<script>", false},
                {"<script> xcgxcbhx ", false},
                {"Это было невероятное выступление", true},

        };
    }
    @Test
    public void testIsValidUserNew() {
    }

    @Test
    public void testIsValidUserUpdate() {
    }

    @Test
    public void testIsValidEventNew() {
    }

    @Test
    public void testIsValidEventUpdate() {
    }



    @Test
    public void testIsValidDataForChangePassword() {
    }

    @Test(dataProvider = "dataIsValidDurationEvent")
    public void testIsValidDurationEvent(double duration,boolean expected) {
        boolean actual = DataValidate.isValidDurationEvent(duration);
        assertEquals(actual, expected);

    }
    @DataProvider(name = "dataIsValidDurationEvent")
    public Object[][] dataIsValidDurationEvent() {
        return new Object[][]{
                {0.3, false},
                {0.5, true},
                {1, true},
                {3, true},
                {3.1, false},

        };
    }

    @Test(dataProvider = "dataForIsXssAttack")
    public void testIsXssAttack(String str,boolean expected) {
        boolean actual = DataValidate.isXssAttack(str);
        assertEquals(actual, expected);

    }
    @DataProvider(name = "dataForIsXssAttack")
    public Object[][] dataForIsXssAttack() {
        return new Object[][]{
                {" ", false},
                {"   ", false},
                {"lsdlskkflas <script>", true},
                {"<script> xcgxcbhx ", true},
                {"Это было невероятное выступление", false},

        };
    }

}