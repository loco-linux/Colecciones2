/*  Creado por Jaime Barraza 
    para POO I - Desarrollo de aplicaciones 
    Duoc UC
*/
package cl.duoc.libro;

import cl.duoc.excepciones.LibroNoEncontradoException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Libro implements Comparable<Libro> {
    // encapsulamiento
    private String  nombreLibro;
    private String  autorLibro;
    private int     codigoLibro;
    private String  estadoLibro;


    Scanner teclado = new Scanner(System.in);
    
    // Constructor vacio
    public Libro(){
        
    }
    
    // Constructor con parámetros
    public Libro(String nombreLibro, String autorLibro, int codigoLibro, String estadoLibro) {
        this.nombreLibro = nombreLibro;
        this.autorLibro = autorLibro;
        this.codigoLibro = codigoLibro;
        this.estadoLibro = estadoLibro;
    }
    
    
    // Getters y Setters
    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }

    public int getCodigoLibro() {
        return codigoLibro;
    }

    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public String getEstadoLibro() {
        return estadoLibro;
    }

    public void setEstadoLibro(String estadoLibro) {
        this.estadoLibro = estadoLibro;
    }

    @Override
    public String toString() {
        return "Libro{" + "nombreLibro=" + nombreLibro + ", autorLibro=" + autorLibro + ", codigoLibro=" + codigoLibro + ", estadoLibro=" + estadoLibro + '}';
    }
    
    // Métodos personalizados
    public void buscarLibros(List<Libro> librosDuoc) throws LibroNoEncontradoException{ 
        int opcionUsuario=0;
        System.out.println("---------- [BUSCADOR DE LIBROS BIBLIOTECA] ----------\n");
        System.out.println("Por favor DIGITE la opcion deseada.");
        System.out.println("[1] Para BUSCAR los Libros del Autor: George RR Martin");
        System.out.println("[2] Para BUSCAR los Libros del Autor: JKK Tolkien");
        System.out.println("[3] Para BUSCAR OTROS Libros");
        try {
        opcionUsuario = teclado.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Debes ingresar un numero"); // Manejo de excepciones
            System.out.println(e.getClass());
            teclado.nextLine();
        }
        String autorBuscado = "";
        switch (opcionUsuario) {
            case 1 -> {
                autorBuscado = "George RR Martin";
                for (Libro libro : librosDuoc) {
                    if (libro.getAutorLibro().equalsIgnoreCase(autorBuscado)) {
                        System.out.println(
                                "Nombre Libro: " +libro.getNombreLibro()+ 
                                " / Codigo Libro: " +libro.getCodigoLibro()+ 
                                " / Disponibilidad: " + libro.getEstadoLibro());
                    }
                }
            }
            case 2 -> {
                autorBuscado = "JRR Tolkien";
                for (Libro libro : librosDuoc) {
                    if (libro.getAutorLibro().equalsIgnoreCase(autorBuscado)) {
                        System.out.println(
                                "Nombre Libro: " +libro.getNombreLibro()+ 
                                " / Codigo Libro: " +libro.getCodigoLibro()+ 
                                " / Disponibilidad: " + libro.getEstadoLibro());
                    }
                }
            }
            case 3 -> {
                teclado.nextLine();
                boolean encontrado = false;
                System.out.println("Por favor ingrese el titulo del Libro que usted desea buscar: ");
                String busquedaUsuario = teclado.nextLine();
                for (Libro libro : librosDuoc) {
                    if (libro.getNombreLibro().equalsIgnoreCase(busquedaUsuario)) {
                        System.out.println("Libro Encontrado!");
                        System.out.println("[NOMBRE]        : "+libro.getNombreLibro());
                        System.out.println("[CODIGO]        : "+libro.getCodigoLibro());
                        System.out.println("[DISPONIBILIDAD]: "+libro.getEstadoLibro()); 
                        encontrado = true;
                        break;
                    } 
                }
                if (!encontrado) {
                    throw new LibroNoEncontradoException(); 
                }
            }
            default -> System.out.println("[ERROR] Opcion no valida. Digite una opcion correcta. Ejemplo: 2");
        }
        
    }
    
    public void ListaLibrosNoDuplicados(HashSet<Libro> librosNoDuplicados) {
            System.out.println("---------- [LISTA DE LIBROS BIBLIOTECA] ----------\n");
            System.out.println("A continuacion, una lista unica con todos los libros de la biblioteca: \n");
            for(Libro libros : librosNoDuplicados) {
                System.out.println("[NOMBRE]        : "+libros.getNombreLibro());
                System.out.println("[AUTOR]         : "+libros.getAutorLibro());
                System.out.println("[CODIGO]        : "+libros.getCodigoLibro());
                System.out.println("[DISPONIBILIDAD]: "+libros.getEstadoLibro()); 
                System.out.println("\n--------------------------------------------------");
        }
    }
    
    public void ListaLibrosOrdenada(TreeSet<Libro> librosOrdenados) {
        System.out.println("---------- [LISTA DE LIBROS ORDENADOS POR CODIGO] ----------\n");
        System.out.println("A continuacion, una lista con los Libros ordenados segun su codigo: \n");
        for(Libro ordenados : librosOrdenados) {
            System.out.println("[NOMBRE]        : "+ordenados.getNombreLibro());
            System.out.println("[AUTOR]         : "+ordenados.getAutorLibro());
            System.out.println("[CODIGO]        : "+ordenados.getCodigoLibro());
            System.out.println("[DISPONIBILIDAD]: "+ordenados.getEstadoLibro()); 
            System.out.println("\n--------------------------------------------------");
    }
    }

    @Override
    public int compareTo(Libro o) {
        return codigoLibro - o.codigoLibro;
    }   
}