package pl.kurs.service;

public class Sum implements Calculation {

    @Override
    public Double execute(Double a, Double b) {
        return a + b;
    }
}
