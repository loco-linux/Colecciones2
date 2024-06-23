package cl.duoc.excepciones;

/*  Creado por Sebastian Olave y Jaime Barraza 
    para POO I - Desarrollo de aplicaciones 
    Duoc UC
*/

public class LibroNoEncontradoException extends Exception {
    
    public LibroNoEncontradoException(){
    }
    
    public LibroNoEncontradoException (String msj_error) { // Manejo excepciones
    super(msj_error);
    }
    
}
