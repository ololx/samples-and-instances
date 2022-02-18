package io.github.innopolis.university.java.team.di.realization.examples.annotated;

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
public class AnnotatedModuleConstructorInjectingExample {

    private final ColoredOutPrinter redOutPrinter;

    private final ColoredOutPrinter greenOutPrinter;

    private final ColoredOutPrinter blueOutPrinter;

    @Inject
    public AnnotatedModuleConstructorInjectingExample(@Red ColoredOutPrinter redOutPrinter,
                                                      @Green ColoredOutPrinter greenOutPrinter,
                                                      @Blue ColoredOutPrinter blueOutPrinter) {
        this.redOutPrinter = redOutPrinter;
        this.greenOutPrinter = greenOutPrinter;
        this.blueOutPrinter = blueOutPrinter;
    }

    public static void main(String [] args) {
        Injector injector = Guice.createInjector(new AnnotatedModule());
        AnnotatedModuleConstructorInjectingExample self = injector.getInstance(
                AnnotatedModuleConstructorInjectingExample.class
        );

        self.redOutPrinter.println("Hello World!");
        self.greenOutPrinter.println("Hello World!");
        self.blueOutPrinter.println("Hello World!");
    }
}
