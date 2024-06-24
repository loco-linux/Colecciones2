/*  Creado por Jaime Barraza 
    para POO I - Desarrollo de aplicaciones 
    Duoc UC
*/
package cl.duoc.usuario;

import cl.duoc.excepciones.LibroYaPrestadoException;
import cl.duoc.libro.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Usuario {
    private String  rut;
    private int     codigoPrestamo;

    Scanner teclado = new Scanner(System.in);
    public static HashMap<Integer, Usuario> listaUsuarios = new HashMap<>();
    
    // Constructor vacío
    public Usuario(){
        
    }
    
    // Constructor con parámetros 
    public Usuario(String rut, int codigoPrestamo) {
        this.rut = rut;
        this.codigoPrestamo = codigoPrestamo;
    }
    
    
    // Getters y Setters
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public int getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public void setCodigoPrestamo(int codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }

    @Override
    public String toString() {
        return "Usuario{" + "rut=" + rut + ", codigoPrestamo=" + codigoPrestamo + '}';
    }
    
    // Métodos personalizados
    public void prestamoLibro(List<Libro> bibliotecaDuoc) throws LibroYaPrestadoException, InterruptedException {
        System.out.println("---------- [SISTEMA DE PRESTAMO DE LIBROS] ----------");
        System.out.println("Libros para prestamos: \n");
        
        for(Libro libro : bibliotecaDuoc){
            System.out.println("------------------------------");
            System.out.println("[NOMBRE]        : "+libro.getNombreLibro());
            System.out.println("[AUTOR]         : "+libro.getAutorLibro());
            System.out.println("[CODIGO]        : "+libro.getCodigoLibro());
            System.out.println("[DISPONIBILIDAD]: "+libro.getEstadoLibro());            
        }
        
        System.out.println("\nSi alguno de estos libros es de tu interes, digita su codigo: ");
        
        try{
            codigoPrestamo = teclado.nextInt();
            }catch(InputMismatchException io){ // Manejo de excepciones
                System.out.println("[ERROR] Ingrese un numero");
                System.out.println(io.getClass());
                teclado.next();
        }
        
        boolean libroEncontrado = false;
        
        for(Libro libro : bibliotecaDuoc){ // For each para busqueda
            if (codigoPrestamo == libro.getCodigoLibro()) {
                libroEncontrado = true;
                if ("Ocupado".equalsIgnoreCase(libro.getEstadoLibro())) {
                throw new LibroYaPrestadoException(); 
                } else {
                    System.out.println("El Libro que has seleccionado para su prestamo: ");
                    System.out.println("[NOMBRE]        : "+libro.getNombreLibro());
                    System.out.println("[AUTOR]         : "+libro.getAutorLibro());
                    System.out.println("[CODIGO]        : "+libro.getCodigoLibro());
                    System.out.println("---> Para finalizar el prestamo del Libro, por favor escribe tu rut: ");
                    teclado.nextLine();
                    String rut = teclado.nextLine();
                    if(!rut.isEmpty()){
                        int codigoPrestamo = 100000 + (int) (Math.random() * 900000);                     
                        for(int i=0; i<3; i++){ // Un mini contador para darle mayor dinamismo a la interfaz de usuario
                            Thread.sleep(1000);
                            System.out.print(".");
                            }
                    
                        System.out.println("El prestamo de tu Libro ha sido registrado exitosamente!");
                        System.out.println("Codigo de prestamo: " + codigoPrestamo);
                        System.out.println("El prestamo del Libro tiene una duracion de 1 mes. Recuerda devolverlo a la Biblioteca.");
                        System.out.println("Gracias! Disfruta tu lectura y/o estudio.");
                        libro.setEstadoLibro("Ocupado");
                        Usuario.listaUsuarios.put(codigoPrestamo, new Usuario(rut, libro.getCodigoLibro()));
                        break;
                        }else System.out.println("[ERROR] Debes ingresar tu rut!");
                }  
            }
        }
        if (!libroEncontrado) System.out.println("[ERROR] Libro no encontrado.\n");       
    } 
    
    
    public void listaUsuariosPrestamo (HashMap<Integer, Usuario> listaDeUsuarios) {
               
        System.out.println("---------- [USUARIOS CON PRESTAMO DE LIBROS EN CURSO] ----------");
        System.out.println("Lista con todos los usuarios los cuales mantienen prestamos activos en nuestra biblioteca: ");
        for (Map.Entry<Integer, Usuario> entrada : listaDeUsuarios.entrySet()) {
            System.out.println("------------------------------");
            Integer key = entrada.getKey();
            Usuario value = entrada.getValue();        
            System.out.println(">>Codigo de Prestamo: "+key+"\n>>Rut Usuario: "+value.getRut()+"\n>>Codigo del Libro: " + value.getCodigoPrestamo());        
        }
        if(listaDeUsuarios.isEmpty()) System.out.println("[ERROR] Aun no se han realizado prestamos...\n");
    }
    
    
    public static void infoUsuarios(String ruta){
        File file = new File(ruta);
        
        try(FileWriter writer = new FileWriter(file)){ // cierra el writer -> writer.close();
            writer.write("codigoPrestamo,rutUsuario,codigoLibro\n");
                      
            for (Map.Entry<Integer, Usuario> entrada : Usuario.listaUsuarios.entrySet()) {            
                Integer key = entrada.getKey();
                Usuario value = entrada.getValue();  
                String datos = key +"," + value.getRut() +","+ value.getCodigoPrestamo() + "\n";
                writer.write(datos);               
            }                
            writer.flush(); // limpia el buffer                     
        } catch(IOException io){
            io.printStackTrace();
        }
    }
    
}