package pl.kurs.service;

public class Subtract implements Calculation {

    @Override
    public Double execute(Double a, Double b) {
        return a - b;
    }
}
