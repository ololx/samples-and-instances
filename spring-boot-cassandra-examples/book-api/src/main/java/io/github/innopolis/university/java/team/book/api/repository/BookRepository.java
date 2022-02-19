package io.github.innopolis.university.java.team.book.api.repository;

import io.github.innopolis.university.java.team.book.api.model.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * project book-api
 * created 19.02.2022 09:43
 *
 * @author Alexander A. Kropotin
 */
public interface BookRepository extends CassandraRepository<Book, UUID> {
}
