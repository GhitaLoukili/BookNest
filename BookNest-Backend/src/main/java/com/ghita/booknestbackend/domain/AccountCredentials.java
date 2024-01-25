package com.ghita.booknestbackend.domain;

//A record is a good choice if you need a class that only holds data; you can avoid a lot of boilerplate code.

public record AccountCredentials(String username, String password) {
}
