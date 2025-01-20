package com.aluracursos.BookCatalog.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Autor autor;
    private String idiomas;
    private Double numeroDeDescargas;

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        Autor autor = new Autor(datosLibro.autor().get(0));
        this.autor = autor;
        this.idiomas = datosLibro.idiomas().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public Libro(){

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return  "-------------------------------- LIBRO -----------------------------------"+"'\n"+
                "\nTítulo:             " + titulo +
                "\nAutor:              " + autor.getNombre()+
                "\nIdioma:             " + idiomas +
                "\nNúmero de Download: " + numeroDeDescargas +
                "---------------------------------------------------------------------------";
    }

}
