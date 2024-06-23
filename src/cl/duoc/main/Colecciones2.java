/*  Creado por Jaime Barraza 
    para POO I - Desarrollo de aplicaciones 
    Duoc UC
*/


package cl.duoc.main;


import cl.duoc.excepciones.LibroNoEncontradoException;
import cl.duoc.excepciones.LibroYaPrestadoException;
import cl.duoc.libro.Libro;
import cl.duoc.usuario.Usuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class Colecciones2 {

    public static void main(String[] args) throws InterruptedException {

        Scanner teclado = new Scanner(System.in);
        int opcionUsuario = 0;


        List<Libro> bibliotecaDuoc = new ArrayList<>();
        Libro biblioteca = new Libro();
        Usuario usuarioBiblioteca = new Usuario();
        
        HashSet<Libro> listaNoDuplicados = new HashSet<>();
        TreeSet<Libro> listaOrdenada = new TreeSet<>();
        
        // Creo archivo CSV con los libros
        CreaCSV_inicial();
        
        
        
        // Agrego libros a la coleccion
        Libro libro1 = new Libro("Juego de Tronos", "George RR Martin", 1996, "Disponible");
        bibliotecaDuoc.add(libro1);
        listaNoDuplicados.add(libro1);
        listaOrdenada.add(libro1);
        
        Libro libro2 = new Libro("Fuego y Sangre", "George RR Martin", 2018, "Disponible");
        bibliotecaDuoc.add(libro2);
        listaNoDuplicados.add(libro2);
        listaOrdenada.add(libro2);
        
        Libro libro3 = new Libro("Danza de Dragones", "George RR Martin", 2011, "Disponible");
        bibliotecaDuoc.add(libro3);
        listaNoDuplicados.add(libro3);
        listaOrdenada.add(libro3);
        
        Libro libro4 = new Libro("El Señor de los Anillos: La Comunidad del Anillo", "JRR Tolkien", 1954, "Disponible");
        bibliotecaDuoc.add(libro4);
        listaNoDuplicados.add(libro4);
        listaOrdenada.add(libro4);
        
        Libro libro5 = new Libro("El Hobbit", "JRR Tolkien", 1937, "Disponible");
        bibliotecaDuoc.add(libro5);
        listaNoDuplicados.add(libro5);
        listaOrdenada.add(libro5);
        
        Libro libro6 = new Libro("Las Dos Torres", "JRR Tolkien", 19544, "Disponible");
        bibliotecaDuoc.add(libro6);
        listaNoDuplicados.add(libro6);
        listaOrdenada.add(libro6);
        
        Libro libro7 = new Libro("El Retorno del Rey", "JRR Tolkien", 1955, "Disponible");
        bibliotecaDuoc.add(libro7);
        listaNoDuplicados.add(libro7);
        listaOrdenada.add(libro7);
        
        Libro libro8 = new Libro("El Silmarillion", "JRR Tolkien", 1977, "Disponible");
        bibliotecaDuoc.add(libro8);
        listaNoDuplicados.add(libro8);
        listaOrdenada.add(libro8);

        Libro libro9 = new Libro("Choque de Reyes", "George RR Martin", 1998, "Disponible");
        bibliotecaDuoc.add(libro9);
        listaNoDuplicados.add(libro9);
        listaOrdenada.add(libro9);
        
        Libro libro10 = new Libro("Festin de Cuervos", "George RR Martin", 2005, "Disponible");
        bibliotecaDuoc.add(libro10);
        listaNoDuplicados.add(libro10);
        listaOrdenada.add(libro10);

        // Menu   
        do{
            System.out.println("""
                                ----------=== [ BIBLIOTECA DUOC UC ] ===---------- 
                               
                                Bienvenido a nuestra Biblioteca. Por favor, digite la opcion deseada: 
                                [1] BUSCAR Libros en nuestra Biblioteca DUOC UC.
                                [2] VER la lista de Libros actualizada.
                                [3] VER la lista de Libros ORDENADA por su Codigo.
                                [4] PEDIR PRESTADO Libros disponibles en nuestra Biblioteca DUOC UC.
                                [5] VER la lista de Usuarios que tienen prestamos de libros vigentes.
                                [6] SALIR.
                               """);     
        try {
        opcionUsuario = teclado.nextInt();
        } catch (InputMismatchException e) { 
            System.out.println("[ERROR] Debes ingresar un numero entero. Ejemplo: 1"); // Manejo de excepciones
            System.out.println(e.getClass());
            teclado.nextLine();
        }
          
        switch(opcionUsuario){
            case 1 -> { 
                try {
                    biblioteca.buscarLibros(bibliotecaDuoc);
                    } catch (LibroNoEncontradoException ex) { 
                        System.out.println("El Libro buscado no ha sido encontrado en la Biblioteca de DuocUC.");
                    }
                    }
            
            // Esta lista no permite elementos duplicados
            // Si los llegase a haber (duplicados), solo muestra 1
            case 2 -> biblioteca.ListaLibrosNoDuplicados(listaNoDuplicados); 
                
            case 3 -> biblioteca.ListaLibrosOrdenada(listaOrdenada);
                
            case 4 -> { 
                try {
                    usuarioBiblioteca.prestamoLibro(bibliotecaDuoc);
                    } catch (LibroYaPrestadoException e) { 
                        System.out.println("[Error] El libro que solicitas, ya ha sido solicitado previamente.");
                    }
                    }
            
            case 5 -> usuarioBiblioteca.listaUsuariosPrestamo(Usuario.listaUsuarios);
                
            case 6 -> {
                System.out.print("Saliendo ");
                for(int i=0; i<3; i++){ // Un mini contador para darle mayor dinamismo a la interfaz de usuario
                    Thread.sleep(1000);
                    System.out.print(".");
                }
                System.out.println("\nGracias por usar Biblioteca Duoc UC");
            }
            default ->  System.out.println("[ERROR] Opcion invalida. Por favor digite una opcion valida. Ejemplo: 1.");                                              
        }                       
        }while(opcionUsuario!=6);
        teclado.close();

    }   
    
    public static void CreaCSV_inicial() throws InterruptedException{
        // Metodo para crear el archivo CSV con la base de datos de libros 
        File file = new File("libros.csv");
        
        if(file.exists()){ // Corrobora si existe el archivo. Si existe, imprime que ya se encuentra disponible
            System.out.println("[" +file+ "] ya disponible!");
        } else { // Si no existe el archivo, lo crea. De esta forma evita duplicados o reformatear el archivo si ya existiese.
        try(FileWriter writer = new FileWriter(file)){ // cierra el writer -> writer.close();
            writer.write("nombreLibro, autorLibro, codigoLibro, estadoLibro\n");
            writer.write("Juego de Tronos, George RR Martin, 011996, Disponible\n");
            writer.write("Fuego y Sangre, George RR Martin, 072018, Disponible\n");
            writer.write("Danza de Dragones, George RR Martin, 042011, Disponible\n");
            writer.write("El Señor de los Anillos: La Comunidad del Anillo, JRR Tolkien, 011954, Disponible\n");
            writer.write("El Hobbit, JRR Tolkien, 001937, Disponible\n");
            writer.write("Las Dos Torres, JRR Tolkien, 021954, Disponible\n");
            writer.write("El Retorno del Rey, JRR Tolkien, 031955, Disponible\n");
            writer.write("El Silmarillion, JRR Tolkien, 041977, Disponible\n");
            writer.write("Choque de Reyes, George RR Martin, 021998, Disponible\n");
            writer.write("Festin de Cuervos, George RR Martin, 032005, Disponible\n");
            writer.flush(); // limpia el buffer
            for(int i=0; i<3; i++){ // Un mini contador para darle mayor dinamismo a la interfaz de usuario
                    Thread.sleep(1000);
                    System.out.print(".");
                }
            System.out.println("[" +file+ "] ha sido creado!");
        } catch(IOException io){
            io.printStackTrace();
        }
        }
        
    }
    
}
