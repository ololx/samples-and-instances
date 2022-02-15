package io.github.innopolis.university.java.team.di.realization.examples;

import com.google.inject.AbstractModule;
import io.github.innopolis.university.java.team.di.realization.examples.commons.ColoredOutPrinter;
import io.github.innopolis.university.java.team.di.realization.examples.commons.GreenOutPrinter;

/**
 * project di-realization-examples
 * created 15.02.2022 13:10
 *
 * @author Alexander A. Kropotin
 */
public class DefaultModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ColoredOutPrinter.class).to(GreenOutPrinter.class);
    }
}

