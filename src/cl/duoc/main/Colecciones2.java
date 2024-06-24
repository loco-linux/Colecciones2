/*  Creado por Jaime Barraza 
    para POO I - Desarrollo de aplicaciones 
    Duoc UC
*/


package cl.duoc.main;


import cl.duoc.excepciones.LibroNoEncontradoException;
import cl.duoc.excepciones.LibroYaPrestadoException;
import cl.duoc.libro.Libro;
import cl.duoc.usuario.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class Colecciones2 {

        static List<Libro> bibliotecaDuoc = new ArrayList<>();
        static Libro biblioteca = new Libro();
        static Usuario usuarioBiblioteca = new Usuario();
        
        static HashSet<Libro> listaNoDuplicados = new HashSet<>();
        static TreeSet<Libro> listaOrdenada = new TreeSet<>();
        
        private static String ruta = "libros.csv";
    
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        Scanner teclado = new Scanner(System.in);
        int opcionUsuario = 0;

        // Creo archivo CSV con los libros
        CreaCSV_inicial();
        
        // Lee la base de datos de libros y la incorporo a las colecciones
        leerLibrosCSV(ruta);
        
        // Setea el HashMap de Usuarios con prestamos si existe
        SetPrestamosRealizados("info.txt");
  

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
                ReeditarLibrosCSV(ruta);
                Usuario.infoUsuarios("info.txt");
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
    
    private static void CreaCSV_inicial() throws InterruptedException{
        // Metodo para crear el archivo CSV con la base de datos de libros 
        File file = new File(ruta);
        
        if(file.exists()){ // Corrobora si existe el archivo. Si existe, imprime que ya se encuentra disponible
            System.out.println("[" +file+ "] ya disponible!");
        } else { // Si no existe el archivo, lo crea. De esta forma evita duplicados o reformatear el archivo si ya existiese.
        try(FileWriter writer = new FileWriter(file)){ // cierra el writer -> writer.close();
            writer.write("nombreLibro,autorLibro,codigoLibro,estadoLibro\n");
            writer.write("Juego de Tronos,George RR Martin,11996,Disponible\n");
            writer.write("Fuego y Sangre,George RR Martin,72018,Disponible\n");
            writer.write("Danza de Dragones,George RR Martin,42011,Disponible\n");
            writer.write("El Señor de los Anillos: La Comunidad del Anillo,JRR Tolkien,11954,Disponible\n");
            writer.write("El Hobbit,JRR Tolkien,1937,Disponible\n");
            writer.write("Las Dos Torres,JRR Tolkien,21954,Disponible\n");
            writer.write("El Retorno del Rey,JRR Tolkien,31955,Disponible\n");
            writer.write("El Silmarillion,JRR Tolkien,41977,Disponible\n");
            writer.write("Choque de Reyes,George RR Martin,21998,Disponible\n");
            writer.write("Festin de Cuervos,George RR Martin,32005,Disponible\n");
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
    
    public static void leerLibrosCSV(String ruta){
        
       try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
           String linea;
           String separador = ",";
           boolean esPrimeraLinea = true;
           
           while((linea = br.readLine()) != null){
               if(esPrimeraLinea){
                   esPrimeraLinea = false;
                   continue;
               }
               String[] values = linea.split(separador);
               Libro libro = new Libro(
                       values[0], // nombreLibro, 
                       values[1], // autorLibro, 
                       Integer.parseInt(values[2].trim()), // codigoLibro,        
                       values[3] // estadoLibro     
               );
                
                bibliotecaDuoc.add(libro);
                listaNoDuplicados.add(libro);
                listaOrdenada.add(libro);
           }
           
           System.out.println("Libros extraidos desde "+ruta+" para operar biblioteca.\n");
           
       }catch(IOException e){
           e.getMessage();
       }
       
    }
    
    private static void ReeditarLibrosCSV(String ruta){
        File file = new File(ruta);
        
        try(FileWriter writer = new FileWriter(file)){ // cierra el writer -> writer.close();
            writer.write("nombreLibro,autorLibro,codigoLibro,estadoLibro\n");
            for(Libro libro : bibliotecaDuoc){
                String datos = libro.getNombreLibro() +","+ libro.getAutorLibro() +","+ libro.getCodigoLibro() +","+ libro.getEstadoLibro()+"\n";
                writer.write(datos);
            }
            
            writer.flush(); // limpia el buffer
            
            
        } catch(IOException io){
            io.printStackTrace();
        }
        
    }
    
    private static void SetPrestamosRealizados(String ruta) throws FileNotFoundException{
                
       try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
           String linea;
           String separador = ",";
           boolean esPrimeraLinea = true;
           
           while((linea = br.readLine()) != null){
               if(esPrimeraLinea){
                   esPrimeraLinea = false;
                   continue;
               }
               String[] values = linea.split(separador);
               


               Usuario.listaUsuarios.put(
                       Integer.parseInt(values[0]), 
                       new Usuario(values[1],Integer.parseInt(values[2]))
                    );
               /*
                codigoPrestamo,rutUsuario,codigoLibro
                906653,17.294.864-2,32005
                912006,18.169.065-8,21998
               */
           }
           
       }catch(IOException e){
           e.getMessage();
       }
        
        
    }
    
    
}
