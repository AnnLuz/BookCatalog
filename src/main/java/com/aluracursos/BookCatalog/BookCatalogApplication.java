package com.aluracursos.BookCatalog;

import com.aluracursos.BookCatalog.main.Main;
import com.aluracursos.BookCatalog.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class BookCatalogApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(BookCatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main principal = new Main(repository);
		principal.menuPrincipal();
	}

}
