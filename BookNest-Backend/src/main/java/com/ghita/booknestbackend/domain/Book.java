package com.ghita.booknestbackend.domain;

/*
For the toMany relationships, the default value is LAZY, but for the toOne
relationships, you should define it. FetchType defines the strategy for fetching data from
the database. The value can be either EAGER or LAZY. In our case, the LAZY strategy means
that when the owner is fetched from the database, the books associated with the owner
will be fetched when needed. EAGER means that the books will be fetched immediately by
the owner.
 */

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String author;
    private String publisher;
    private String genre;
    private String topic;
    private int releaseYear;
    private int price;

    public Book(String name, String author, String publisher, String genre, String topic, int releaseYear, int price, Owner owner) {
        super();
        this.name=name;
        this.author=author;
        this.publisher=publisher;
        this.genre=genre;
        this.topic=topic;
        this.releaseYear=releaseYear;
        this.price=price;
        this.owner=owner;
    }

    // one-to-many relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner")
    private Owner owner;

    //many-to-many relationship
    /*
    @Setter
    @Getter
    @ManyToMany(mappedBy="books")
    private Set<Owner> owners = new HashSet<Owner>();
    */

}
