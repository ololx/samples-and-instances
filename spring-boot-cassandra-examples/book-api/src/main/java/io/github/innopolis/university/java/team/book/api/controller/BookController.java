package io.github.innopolis.university.java.team.book.api.controller;

import io.github.innopolis.university.java.team.book.api.model.Book;
import io.github.innopolis.university.java.team.book.api.service.BookService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
public class BookController {

    BookService bookService;

    public BookController(@Autowired @Qualifier("DefaultBookService") BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Book> find() {
        log.info("Receive request for find all books from db");
        List<Book> books = this.bookService.find();

        log.info("Return follows books:\n{}", books);
        return books;
    }
}
