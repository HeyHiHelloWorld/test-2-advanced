package pl.kurs.service;

import org.springframework.stereotype.Service;
import pl.kurs.dao.ICalculationEventDao;
import pl.kurs.exceptions.UnknownOperatorException;
import pl.kurs.model.CalculationEvent;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculationService {

    private final ICalculationEventDao eventDao;
    private final Map<String, Calculation> operatorsMap = Map.of(
            "+", new Sum(),
            "-", new Subtract(),
            "*", new Multiply(),
            "/", new Divide()
    );



    public CalculationService(ICalculationEventDao eventDao) {
        this.eventDao = eventDao;
    }

    public Double execute(String input) {
        List<String> tokens = tokenize(input);
        CalculationEvent event = new CalculationEvent(input, Timestamp.from(Instant.now()));
        eventDao.save(event);
        return evaluate(tokens);

    }

    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        Matcher m = Pattern.compile("\\d+(?:\\.\\d+)?|[+\\-*/]").matcher(input.replaceAll("\\s+", ""));

        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

    private Double evaluate(List<String> tokens) {
        Deque<Double> values = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                values.push(Double.valueOf(token));
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && precedence(operators.pop()) >= precedence(token)) {
                    applyTopOperator(values, operators);
                }
                operators.push(token);
            } else {
                throw new UnknownOperatorException("Invalid token");
            }
        }
        while (!operators.isEmpty()) {
            applyTopOperator(values, operators);
        }

        if (values.size() != 1) {
            throw new UnknownOperatorException("Malformed expression, leftover values: " + values);
        }
        return values.pop();
    }

    private boolean isNumber(String s) {
        return s.matches("\\d+(?:\\.\\d+)?");
    }

    private boolean isOperator(String s) {
        return operatorsMap.containsKey(s);
    }

    private int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private void applyTopOperator(Deque<Double> values, Deque<String> ops) {
        String op = ops.pop();
        Double b = values.pop();
        Double a = values.pop();
        values.push(operatorsMap.get(op).execute(a, b));
    }
}