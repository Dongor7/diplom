package com.itsupportme.gis.entity.test;


public class JobExecutor {

    private CalculatorInterface calculator;

    public JobExecutor(CalculatorInterface calculator) {
        this.calculator = calculator;
    }

    public int execute() {

        return calculator.makeAddition(2, 2);
    }
}
