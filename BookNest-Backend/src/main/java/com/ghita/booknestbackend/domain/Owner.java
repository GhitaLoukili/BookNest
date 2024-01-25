package com.ghita.booknestbackend.domain;

/*
The @OneToMany annotation has two attributes that we are using. The cascade attribute
defines how cascading affects the entities in the case of deletions or updates. The ALL
attribute setting means that all operations are cascaded. For example, if the owner is
deleted, the books that are linked to that owner are deleted as well. The mappedBy="owner"
attribute setting tells us that the Book class has the owner field, which is the foreign key
for this relationship.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ownerid;
    private String firstname, lastname;

    public Owner(String firstname, String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // one-to-many relationship
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Book> books;

    //many-to-many relationship
    /*
    @Getter @Setter
    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name="book_owner",joinColumns = {@JoinColumn(name="ownerid") }, inverseJoinColumns = {@JoinColumn(name="id") })
    private Set<Book> cars = new HashSet<Book>();
    */
}
