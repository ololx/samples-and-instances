package io.github.innopolis.university.java.team.book.api.service;

import io.github.innopolis.university.java.team.book.api.model.Book;
import io.github.innopolis.university.java.team.book.api.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project book-api
 * created 19.02.2022 09:57
 *
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("DefaultBookService")
public class DefaultBookService implements BookService<Book> {

    BookRepository bookRepository;

    public List<Book> find() {
        List<Book> books = this.bookRepository.findAll();
        log.debug("Retrieved all books from db:\n{}", books);

        return books;
    }
}
