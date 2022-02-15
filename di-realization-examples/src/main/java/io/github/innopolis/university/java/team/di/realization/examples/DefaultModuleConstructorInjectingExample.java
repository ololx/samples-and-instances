package io.github.innopolis.university.java.team.di.realization.examples;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.innopolis.university.java.team.di.realization.examples.commons.ColoredOutPrinter;

/**
 * project di-realization-examples
 * created 15.02.2022 13:01
 *
 * @author Alexander A. Kropotin
 */
public class DefaultModuleConstructorInjectingExample {

    private final ColoredOutPrinter redOutPrinter;

    private final ColoredOutPrinter grenOutPrinter;

    private final ColoredOutPrinter blueOutPrinter;

    @Inject
    public DefaultModuleConstructorInjectingExample(ColoredOutPrinter redOutPrinter,
                                                    ColoredOutPrinter greenOutPrinter,
                                                    ColoredOutPrinter blueOutPrinter) {
        this.redOutPrinter = redOutPrinter;
        this.grenOutPrinter = greenOutPrinter;
        this.blueOutPrinter = blueOutPrinter;
    }

    public static void main(String [] args) {
        Injector injector = Guice.createInjector(new DefaultModule());
        DefaultModuleConstructorInjectingExample self = injector.getInstance(DefaultModuleConstructorInjectingExample.class);

        self.redOutPrinter.println("Hello World!");
        self.grenOutPrinter.println("Hello World!");
        self.blueOutPrinter.println("Hello World!");
    }
}
