package com.aluracursos.BookCatalog.main;

import com.aluracursos.BookCatalog.model.*;
import com.aluracursos.BookCatalog.repository.LibroRepository;
import com.aluracursos.BookCatalog.service.ConsumoAPI;
import com.aluracursos.BookCatalog.service.ConvierteDatos;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;


    public Main(LibroRepository repository) {

        this.repositorio = repository;

    }


    public void menuPrincipal() {


        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        int contador = 0;


        var opcion = -1;
        while (opcion != 0) {
            System.out.println("-------------------------------------------------------------------------------------------------------");
            System.out.println("                                            Book Catalog                                               ");
            System.out.println("-------------------------------------------------------------------------------------------------------");
            System.out.println("|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Bienvenido al Menú Catalogo de Libros!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("|1|Buscar libro por título                                                                             ");
            System.out.println("|2|Listar libros registrados                                                                           ");
            System.out.println("|3|Listar autores registrados                                                                          ");
            System.out.println("|4|Listar autores vivos en un determinado año                                                          ");
            System.out.println("|5|Listar libros por idioma                                                                            ");
            System.out.println("|*|Ingrese un número según la opción valida                                                            ");
            System.out.println("-------------------------------------------------------------------------------------------------------");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();

                    break;
                case 2:
                    listarLibros();

                    break;
                case 3:
                    listarAutores();


                    break;
                case 4:
                    listarAutoresVivosPorAnio();

                    break;
                case 5:
                    listarLibrosPorIdioma();

                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }


    //-------------------------------------------LIBRO POR TÍTULO---------------------------------------------------

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desee buscar: ");
        var nombreLibro = teclado.nextLine();
        System.out.println("Buscando libro...");
        String busqueda = URL_BASE.concat(nombreLibro.replace(" ", "%20").toLowerCase().trim());

        String json = consumoAPI.obtenerDatos(busqueda);
        String jsonLibro = conversor.extraerObjetoJson(json, "resultado");

        List<DatosLibro> librosDTO = conversor.obtenerLista(jsonLibro, DatosLibro.class);

        if (librosDTO.size() > 0) {
            Libro libro = new Libro(librosDTO.get(0));

            //Verifica se o Autor já está cadastrado
            Autor autor = repositorio.buscarAutores(libro.getAutor());
            if (autor != null) {
                libro.setAutor(null);
                repositorio.save(libro);
                libro.setAutor(autor);
            }
            libro = repositorio.save(libro);
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado!");
        }
    }

    //------------------------------------------LISTAR LIBROS REGISTRADOS-------------------------------------------

    private void listarLibros() {
        List<Libro> livros = repositorio.findAll();
        livros.forEach(System.out::println);
    }

    //------------------------------------------------------------------------------------------------------------


    //-----------------------------------------LISTAR AUTORES REGISTRADOS------------------------------------------

    private void listarAutores() {
        List<Autor> autores = repositorio.buscarAutores();
        autores.forEach(System.out::println);
    }


    //----------------------------------------LISTAR AUTORES VIVOS EN DETERMINADO AÑO-----------------------------

    private void listarAutoresVivosPorAnio() {
        try {
            System.out.println("Digite un anio:");
            int ano = teclado.nextInt();
            teclado.nextLine();

            List<Autor> autores = repositorio.buscarAutoresVivosPorAnio(Year.of(anio));
            autores.forEach(System.out::println);
        }catch (InputMismatchException e){
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            teclado.nextLine();
        }
    }

    //--------------------------------------LISTAR LIBROS POR IDIOMA----------------------------------------------

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Digite un idioma para buscar
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
        String idioma = teclado.nextLine();
        List<Libro> livros = repositorio.findByIdioma(idioma);
        if (!livros.isEmpty()){
            livros.forEach(System.out::println);
        }else{
            System.out.println("No existen libros registrados en ese idioma.");
        }
    }
}

