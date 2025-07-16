package pl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.kurs.service.CalculationService;

@ComponentScan
public class Runner {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Runner.class);
        CalculationService equationSolver = ctx.getBean("equationSolver", CalculationService.class);
        String input = "2+2/2";
        Double execute = equationSolver.execute(input);
        System.out.println(execute);

    }

}
