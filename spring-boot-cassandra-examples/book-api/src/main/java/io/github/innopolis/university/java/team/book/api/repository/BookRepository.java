package io.github.innopolis.university.java.team.book.api.repository;

import io.github.innopolis.university.java.team.book.api.model.entity.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * project book-api
 * created 19.02.2022 09:43
 *
 * @author Alexander A. Kropotin
 */
@Repository("BookRepository")
public interface BookRepository extends CassandraRepository<Book, UUID> {
}
