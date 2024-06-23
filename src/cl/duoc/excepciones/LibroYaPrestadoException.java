package cl.duoc.excepciones;

/*  Creado por Sebastian Olave y Jaime Barraza 
    para POO I - Desarrollo de aplicaciones 
    Duoc UC
*/

public class LibroYaPrestadoException extends Exception{
    
    public LibroYaPrestadoException(){
    }
    
    public LibroYaPrestadoException(String msj_error) { // Manejo excepciones
    super(msj_error);
    }
}
