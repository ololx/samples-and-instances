package io.github.innopolis.university.java.team.di.realization.examples.named;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import io.github.innopolis.university.java.team.di.realization.examples.commons.ColoredOutPrinter;

/**
 * project di-realization-examples
 * created 15.02.2022 19:01
 *
 * @author Alexander A. Kropotin
 */
public class NamedModuleConstructorInjectingExample {

    private final ColoredOutPrinter redOutPrinter;

    private final ColoredOutPrinter greenOutPrinter;

    private final ColoredOutPrinter blueOutPrinter;

    @Inject
    public NamedModuleConstructorInjectingExample(@Named("RedOutPrinter")
                                                              ColoredOutPrinter redOutPrinter,
                                                  @Named("GreenOutPrinter")
                                                          ColoredOutPrinter greenOutPrinter,
                                                  @Named("BlueOutPrinter")
                                                              ColoredOutPrinter blueOutPrinter) {
        this.redOutPrinter = redOutPrinter;
        this.greenOutPrinter = greenOutPrinter;
        this.blueOutPrinter = blueOutPrinter;
    }

    public static void main(String [] args) {
        Injector injector = Guice.createInjector(new NamedModule());
        NamedModuleConstructorInjectingExample self = injector.getInstance(
                NamedModuleConstructorInjectingExample.class
        );

        self.redOutPrinter.println("Hello World!");
        self.greenOutPrinter.println("Hello World!");
        self.blueOutPrinter.println("Hello World!");
    }
}
