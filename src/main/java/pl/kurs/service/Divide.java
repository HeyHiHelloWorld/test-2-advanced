package pl.kurs.service;

public class Divide implements Calculation {

    @Override
    public Double execute(Double a, Double b) {
        if (b == 0)
            throw new ArithmeticException("Cannot divide by 0");

        return a / b;
    }
}
