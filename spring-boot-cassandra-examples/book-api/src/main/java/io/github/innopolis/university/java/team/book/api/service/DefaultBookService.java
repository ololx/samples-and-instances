package io.github.innopolis.university.java.team.book.api.service;

import com.datastax.driver.core.utils.UUIDs;
import io.github.innopolis.university.java.team.book.api.model.Book;
import io.github.innopolis.university.java.team.book.api.repository.BookRepository;
import io.github.ololx.cranberry.statement.annotation.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
public class DefaultBookService implements BookService<Book, UUID> {

    BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        book.setId(UUIDs.timeBased());
        this.bookRepository.insert(book);
        log.debug("Create new book in db:\n{}", book);

        return book;
    }

    @Override
    public Book update(UUID uuid, Book book) {
        @NotNull(message = "The book is not exists")
        Book storedBook = this.bookRepository.findById(uuid).orElse(null);

        BeanUtils.copyProperties(book, storedBook, "id");
        this.bookRepository.save(storedBook);
        log.debug("Update stored book in db:\n{}", storedBook);

        return storedBook;
    }

    @Override
    public Book find(UUID uuid) {
        @NotNull(message = "The book is not exists")
        Book storedBook = this.bookRepository.findById(uuid).orElse(null);
        log.debug("Retrieve stored book from db:\n{}", storedBook);

        return storedBook;
    }

    @Override
    public List<Book> find() {
        List<Book> books = this.bookRepository.findAll();
        log.debug("Retrieve all books from db:\n{}", books);

        return books;
    }

    @Override
    public Book delete(UUID uuid) {
        @NotNull(message = "The book is not exists")
        Book storedBook = this.bookRepository.findById(uuid).orElse(null);
        log.debug("Retrieve stored book from db:\n{}", storedBook);

        this.bookRepository.delete(storedBook);

        return storedBook;
    }
}
