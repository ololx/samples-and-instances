package io.github.innopolis.university.java.team.book.api.controller;

import io.github.innopolis.university.java.team.book.api.model.detail.BookDetail;
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

    BookService<BookDetail, UUID> bookService;

    public BookController(@Autowired @Qualifier("DefaultBookService")
                                  BookService<BookDetail, UUID> bookService) {
        this.bookService = bookService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BookDetail create(@RequestBody BookDetail book) {
        return this.bookService.create(book);
    }

    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BookDetail update(@PathVariable(name = "id") UUID id, @RequestBody BookDetail book) {
        return this.bookService.update(id, book);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BookDetail find(@PathVariable(name = "id") UUID id) {
        return this.bookService.find(id);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<BookDetail> find() {
        return this.bookService.find();
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BookDetail delete(@PathVariable(name = "id") UUID id) {
        return this.bookService.delete(id);
    }
}
