package com.aluracursos.BookCatalog.model;


import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Year anioDeNacimiento;
    private Year anioDeFallecimiento;
    @ManyToMany(mappedBy = "autor", fetch = FetchType.EAGER)
     List<Libro> libros = new ArrayList<>();



    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anioDeNacimiento = Year.of(datosAutor.anioDeNacimiento());
        this.anioDeFallecimiento = Year.of(datosAutor.anioDeFallecimiento());

    }



    public Autor(){

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Year getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Year anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Year getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Year anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "-------------------------------------Autor-----------------------------------"+
                "Nombre: '" + nombre + '\n' +
                "Fecha de nacimiento: '" + anioDeNacimiento + '\n' +
                "Fecha de fallecimiento:'" + anioDeFallecimiento + '\n' +
                "Libros: " + libros.stream().map(l -> l.getTitulo()).collect(Collectors.toList())+
                "----------------------------------------------------------------------------";
    }
}
