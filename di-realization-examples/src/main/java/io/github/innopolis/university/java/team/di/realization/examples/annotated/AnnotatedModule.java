package io.github.innopolis.university.java.team.di.realization.examples.annotated;

import com.google.inject.AbstractModule;
import io.github.innopolis.university.java.team.di.realization.examples.commons.BlueOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.ColoredOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.GreenOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.RedOutPrinter;

/**
 * project di-realization-examples
 * created 15.02.2022 13:10
 *
 * @author Alexander A. Kropotin
 */
public class AnnotatedModule extends AbstractModule {

    @Override
    protected void configure() {
        //Green by default
        bind(ColoredOutPrinter.class).to(GreenOutPrinter.class);

        bind(ColoredOutPrinter.class).annotatedWith(Red.class).to(RedOutPrinter.class);
        bind(ColoredOutPrinter.class).annotatedWith(Green.class).to(GreenOutPrinter.class);
        bind(ColoredOutPrinter.class).annotatedWith(Blue.class).to(BlueOutPrinter.class);
    }
}