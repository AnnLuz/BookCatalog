package com.aluracursos.BookCatalog.repository;


import com.aluracursos.BookCatalog.model.Autor;
import com.aluracursos.BookCatalog.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdioma(String idioma);

    Integer countByIdioma(String idioma);

    @Query("SELECT l.numeroDeDescargas FROM Libro l")
    List<Double> buscarNumeroDeDescargas();

    @Query("SELECT a FROM Libro l JOIN l.autor a")
    List<Autor> buscarAutores();

    @Query ("SELECT a FROM Libro l JOIN l.autor a WHERE a.anioDeNacimiento <= :anio and a.anioDeFallecimiento >= :anio")
    List<Autor> buscarAutoresVivosPorAnio(Year anio);

    @Query ("SELECT a FROM Libro l JOIN l.autor a WHERE a.autor = :autor")
    Autor buscarAutorPorNombre(String autor);



}
