package io.github.innopolis.university.java.team.book.api.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.innopolis.university.java.team.book.api.model.detail.BookDetail;
import io.github.innopolis.university.java.team.book.api.model.entity.Book;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * project book-api
 * created 21.02.2022 11:48
 *
 * @author Alexander A. Kropotin
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("DefaultBookMapper")
public final class DefaultBookMapper implements BookMapper<Book, BookDetail> {

    ObjectMapper objectMapper;

    public DefaultBookMapper(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BookDetail map(Book entity) {
        return this.objectMapper.convertValue(entity, BookDetail.class);
    }

    @SneakyThrows({
            JsonMappingException.class
    })
    @Override
    public BookDetail map(Book from, BookDetail to) {
        return this.objectMapper.updateValue(to, from);
    }

    @Override
    public Book map(BookDetail detail) {
        return this.objectMapper.convertValue(detail, Book.class);
    }

    @SneakyThrows({
            JsonMappingException.class
    })
    @Override
    public Book map(BookDetail from, Book to) {
        return this.objectMapper.updateValue(to, from);
    }
}
