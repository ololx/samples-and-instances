package io.github.innopolis.university.java.team.book.api.service;

import io.github.innopolis.university.java.team.book.api.model.detail.BookDetail;
import io.github.innopolis.university.java.team.book.api.model.entity.Book;

/**
 * project book-api
 * created 21.02.2022 11:40
 *
 * @author Alexander A. Kropotin
 */
public interface BookMapper<E extends Book, D extends BookDetail> {

    D map(E to);

    D map(E from, D to);

    E map(D to);

    E map(D from, E to);
}
