package io.github.innopolis.university.java.team.book.api.controller;

import io.github.innopolis.university.java.team.book.api.model.Book;
import io.github.innopolis.university.java.team.book.api.service.BookService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * project book-api
 * created 19.02.2022 10:05
 *
 * @author Alexander A. Kropotin
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/books")
@RestController
public final class BookController {

    BookService<Book, UUID> bookService;

    public BookController(@Autowired @Qualifier("DefaultBookService")
                                  BookService<Book, UUID> bookService) {
        this.bookService = bookService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Book create(@RequestBody Book book) {
        log.info("Receive request:\n model - {}", book);
        book = this.bookService.create(book);

        log.info("Return follow book:\n{}", book);
        return book;
    }

    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Book update(@PathVariable(name = "id") UUID id, @RequestBody Book book) {
        log.info("Receive request:\n model - {}\nid - ", book, id);
        book = this.bookService.update(id, book);

        log.info("Return follow book:\n{}", book);
        return book;
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Book find(@PathVariable(name = "id") UUID id) {
        log.info("Receive request:\nid - ", id);
        Book book = this.bookService.find(id);

        log.info("Return follow book:\n{}", book);
        return book;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Book> find() {
        log.info("Receive request - empty params");
        List<Book> books = this.bookService.find();

        log.info("Return follows books:\n{}", books);
        return books;
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Book delete(@PathVariable(name = "id") UUID id) {
        log.info("Receive request:\nid - ", id);
        Book book = this.bookService.delete(id);

        log.info("Return follow book:\n{}", book);
        return book;
    }
}
