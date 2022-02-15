package io.github.innopolis.university.java.team.di.realization.examples.named;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import io.github.innopolis.university.java.team.di.realization.examples.annotated.AnnotatedModule;
import io.github.innopolis.university.java.team.di.realization.examples.annotated.Blue;
import io.github.innopolis.university.java.team.di.realization.examples.annotated.Green;
import io.github.innopolis.university.java.team.di.realization.examples.annotated.Red;
import io.github.innopolis.university.java.team.di.realization.examples.commons.ColoredOutPrinter;

/**
 * project di-realization-examples
 * created 15.02.2022 19:01
 *
 * @author Alexander A. Kropotin
 */
public class NamedModuleConstructorInjectingExample {

    private final ColoredOutPrinter redOutPrinter;

    private final ColoredOutPrinter grenOutPrinter;

    private final ColoredOutPrinter blueOutPrinter;

    @Inject
    public NamedModuleConstructorInjectingExample(@Named("RedOutPrinter") ColoredOutPrinter redOutPrinter,
                                                  @Named("GreenOutPrinter") ColoredOutPrinter greenOutPrinter,
                                                  @Named("BlueOutPrinter") ColoredOutPrinter blueOutPrinter) {
        this.redOutPrinter = redOutPrinter;
        this.grenOutPrinter = greenOutPrinter;
        this.blueOutPrinter = blueOutPrinter;
    }

    public static void main(String [] args) {
        Injector injector = Guice.createInjector(new NamedModule());
        NamedModuleConstructorInjectingExample self = injector.getInstance(NamedModuleConstructorInjectingExample.class);

        self.redOutPrinter.println("Hello World!");
        self.grenOutPrinter.println("Hello World!");
        self.blueOutPrinter.println("Hello World!");
    }
}
