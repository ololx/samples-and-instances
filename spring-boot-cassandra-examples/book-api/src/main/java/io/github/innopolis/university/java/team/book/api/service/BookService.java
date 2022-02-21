package io.github.innopolis.university.java.team.book.api.service;

import io.github.innopolis.university.java.team.book.api.model.detail.BookDetail;

import java.util.List;
import java.util.UUID;

/**
 * project book-api
 * created 19.02.2022 10:06
 *
 * @author Alexander A. Kropotin
 */
public interface BookService<T extends BookDetail, ID extends UUID> {

    T create(T book);

    T update(ID id, T book);

    T find(ID id);

    List<T> find();

    T delete(ID id);
}
