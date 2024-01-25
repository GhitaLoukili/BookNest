package com.ghita.booknestbackend;

import com.ghita.booknestbackend.domain.AppUser;
import com.ghita.booknestbackend.domain.Book;
import com.ghita.booknestbackend.domain.Owner;
import com.ghita.booknestbackend.repository.AppUserRepository;
import com.ghita.booknestbackend.repository.BookRepository;
import com.ghita.booknestbackend.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class BookNestBackendApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(BookNestBackendApplication.class);
    private final BookRepository bookRepository;
    private final OwnerRepository ownerRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public BookNestBackendApplication(BookRepository bookRepository, OwnerRepository ownerRepository,
                                      AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.ownerRepository = ownerRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookNestBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Add owner objects and save these to db
        Owner owner1 = new Owner("Ghita", "Loukili");
        Owner owner2 = new Owner("Yasmine", "Loukili");
        Owner owner3 = new Owner("Hind", "Loukili");
        ownerRepository.saveAll(Arrays.asList(owner1, owner2, owner3));

        bookRepository.save(new Book("The Butterfly Room", "Lucinda Riley", "Pan Macmillan","Story", "Fictionnal History, romance, mystery", 2019, 130, owner1));
        bookRepository.save(new Book("The Italian Girl", "Lucinda Riley", "Pan Macmillan","Story", "Fictionnal History, romance, mystery", 1996, 130, owner1));
        bookRepository.save(new Book("Before We Say Goodbye", "Toshikazu Kawaguchi", "Picador","Novel", "Mystery, time travelling, fiction", 2021, 150, owner3));
        bookRepository.save(new Book("Days at the Morisaki Bookshop", "Satoshi Yagisawa", "Harper Perennial","Novel", "Japan, fiction, romance", 2010, 110, owner2));
        bookRepository.save(new Book("A Little Life", "Hanya Yanagihara", "Doubleday","Novel", "Fiction, contemporary, mental health", 2015, 210, owner2));
        bookRepository.save(new Book("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin", "Pearson","Technical book", "Computer science, coding, software", 2007, 750, owner1));

        // Username: user, password: user
        appUserRepository.save(new AppUser("sensei", passwordEncoder.encode("RitaSensei"), "USER"));
//        // Username: admin, password: admin
        appUserRepository.save(new AppUser("admin", passwordEncoder.encode("AdminPassword"), "ADMIN"));

        // Fetch all cars and log to console
        for (Book book : bookRepository.findAll()) {
            logger.info("book: {}, author: {}", book.getName(),book.getAuthor());
        }
    }
}
