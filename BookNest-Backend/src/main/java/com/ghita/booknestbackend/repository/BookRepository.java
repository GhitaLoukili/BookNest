package com.ghita.booknestbackend.repository;
/*
If the method returns only one item, Optional<T> is returned instead of T.
The Optional class was introduced in Java 8 SE and is a type of single-value container that either
contains a value or doesn’t. If there is a value, the isPresent() method returns true and
you can get it by using the get() method; otherwise, it returns false. By using Optional,
we can prevent null pointer exceptions. Null pointers can lead to unexpected and often
undesirable behavior in Java programs.

 */

import com.ghita.booknestbackend.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book,Long> {

    //fetch books by author
    List<Book> findByAuthor(@Param("author") String author);

    //fetch books by publisher
    List<Book> findByPublisher(@Param("publisher") String publisher);

    //fetch books by release year
    List<Book> findByReleaseYear(int releaseYear);

    //fetch books by topics using SQL
    @Query("select b from Book b where b.topic='Japan, time travel, magical realm' ")
    List<Book> findByTopic(String topic);

    //fetch by author and price
    List<Book> findByAuthorAndPrice(String author, int price);

    //fetch books by genre order by release year
    List<Book> findByGenreOrderByReleaseYear(String genre);

}
