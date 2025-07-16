package pl.kurs.service;

import org.junit.Before;
import org.junit.Test;

import pl.kurs.dao.CalculationEventDao;
import pl.kurs.dao.ICalculationEventDao;
import pl.kurs.exceptions.InvalidEquationFormatException;
import pl.kurs.exceptions.UnknownOperatorException;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CalculationServiceTest {

    private ICalculationEventDao dao;
    private CalculationService calculationService;

    @Before
    public void init() {
        dao = new CalculationEventDao();
        calculationService = new CalculationService(dao);
    }

    @Test
    public void testValidAddition() {
        assertEquals(8, calculationService.execute("3 + 5"), 0.0);
    }

    @Test()
    public void testValidSubtraction() {
        assertEquals(-2, calculationService.execute("3 - 5"), 0.0);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        calculationService.execute("10 / 0");
    }

    @Test(expected = UnknownOperatorException.class)
    public void testUnknownOperator() {
        calculationService.execute("3 ^ 5");
    }

    @Test(expected = NoSuchElementException.class)
    public void testNonNumericOperand() {
        calculationService.execute("a + 5");
    }
}