package io.github.innopolis.university.java.team.book.api.service;

import com.datastax.driver.core.utils.UUIDs;
import io.github.innopolis.university.java.team.book.api.model.detail.BookDetail;
import io.github.innopolis.university.java.team.book.api.model.entity.Book;
import io.github.innopolis.university.java.team.book.api.repository.BookRepository;
import io.github.ololx.cranberry.statement.annotation.NotNull;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * project book-api
 * created 19.02.2022 09:57
 *
 * @author Alexander A. Kropotin
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("DefaultBookService")
public final class DefaultBookService implements BookService<BookDetail, UUID> {

    BookRepository bookRepository;

    BookMapper<Book, BookDetail> bookMapper;

    public DefaultBookService(@Autowired @Qualifier("BookRepository")
                                      BookRepository bookRepository,
                              @Autowired @Qualifier("DefaultBookMapper")
                                      BookMapper<Book, BookDetail> bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDetail create(@NotNull(message = "The book must be define") BookDetail bookDetail) {
        Book newBook = this.bookMapper.map(bookDetail);
        newBook.setId(UUIDs.timeBased());
        log.debug("Create new entity from detail:\nentity - {}\ndetail - {}", newBook, bookDetail);

        this.bookRepository.insert(newBook);
        log.debug("Save new book in db:\n{}", newBook);

        return this.bookMapper.map(newBook);
    }

    @Override
    public BookDetail update(@NotNull(message = "The book UUID must be define") UUID uuid,
                             @NotNull(message = "The book must be define") BookDetail bookDetail) {
        @NotNull(message = "The bookDetail is not exists")
        Book storedBook = this.bookRepository.findById(uuid).orElse(null);
        storedBook = this.bookMapper.map(bookDetail, storedBook);
        storedBook.setId(uuid);
        log.debug("Update entity from detail:\nentity - {}\ndetail - {}", storedBook, bookDetail);

        this.bookRepository.save(storedBook);
        log.debug("Update stored book in db:\n{}", storedBook);

        return this.bookMapper.map(storedBook);
    }

    @Override
    public BookDetail find(@NotNull(message = "The book UUID must be define") UUID uuid) {
        @NotNull(message = "The book is not exists")
        Book storedBook = this.bookRepository.findById(uuid).orElse(null);
        log.debug("Retrieve stored book from db:\n{}", storedBook);

        return this.bookMapper.map(storedBook);
    }

    @Override
    public List<BookDetail> find() {
        List<Book> books = this.bookRepository.findAll();
        log.debug("Retrieve stored books from db:\n{}", books);

        return books.parallelStream()
                .map(bk -> this.bookMapper.map(bk))
                .collect(Collectors.toList());
    }

    @Override
    public BookDetail delete(@NotNull(message = "The book UUID must be define") UUID uuid) {
        @NotNull(message = "The book is not exists")
        Book storedBook = this.bookRepository.findById(uuid).orElse(null);
        log.debug("Retrieve stored book from db:\n{}", storedBook);

        this.bookRepository.delete(storedBook);
        log.debug("Delete stored book in db:\n{}", storedBook);

        return this.bookMapper.map(storedBook);
    }
}
