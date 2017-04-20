package com.itsupportme.gis.entity.test;


public class Main {

    public static void main(String[] args) {

        CalculatorInterface calculator         = new Calculator();
        CalculatorInterface extendedCalculator = new ExtendedCalculator();

        JobExecutor jobExecutor = new JobExecutor(extendedCalculator);

        int result = jobExecutor.execute();

    };
}
