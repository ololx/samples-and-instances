package io.github.innopolis.university.java.team.book.api.service;

import io.github.innopolis.university.java.team.book.api.model.Book;

import java.util.List;

/**
 * project book-api
 * created 19.02.2022 10:06
 *
 * @author Alexander A. Kropotin
 */
public interface BookService<T extends Book> {

    List<T> find();
}
