package io.github.innopolis.university.java.team.di.realization.examples.commons;

/**
 * project di-realization-examples
 * created 15.02.2022 12:52
 * 
 * @author Alexander A. Kropotin
 */
public class GreenOutPrinter implements ColoredOutPrinter {

    @Override
    public void println(String msg) {
        System.out.println(ANSI_RESET + ANSI_GREEN + msg + ANSI_RESET);
    }
}
