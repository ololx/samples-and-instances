package io.github.innopolis.university.java.team.di.realization.examples.named;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.github.innopolis.university.java.team.di.realization.examples.commons.BlueOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.ColoredOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.GreenOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.RedOutPrinter;

/**
 * project di-realization-examples
 * created 15.02.2022 19:10
 *
 * @author Alexander A. Kropotin
 */
public class NamedModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ColoredOutPrinter.class)
                .annotatedWith(Names.named("RedOutPrinter"))
                .to(RedOutPrinter.class);
        bind(ColoredOutPrinter.class)
                .annotatedWith(Names.named("GreenOutPrinter"))
                .to(GreenOutPrinter.class);
        bind(ColoredOutPrinter.class)
                .annotatedWith(Names.named("BlueOutPrinter"))
                .to(BlueOutPrinter.class);
    }
}

