import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**En este caso, vamos a abrir un hilo por cada peticion que haga el servidor
*para as� poder procesar varias peticiones simultaneas de diferentes clientes
*/
public class Servidor{
	
	public static final int PUERTO = 2018;
	private static List<Pelicula> peliculas = new ArrayList<>();

	public static void main(String[] args) {
		
		Servidor server = new Servidor(); // (La fiesta de blas) UN UNICO OBJETO Se la pasaremos a los hilos.
		
		// Inicializa las películas de ejemplo
		
	    peliculas.add(new Pelicula("1", "Titanic", "James Cameron", 9.99));
	    peliculas.add(new Pelicula("2", "Jurassic Park", "Steven Spielberg", 7.99));
	    peliculas.add(new Pelicula("3", "The Shawshank Redemption", "Frank Darabont", 8.99));
	    peliculas.add(new Pelicula("4", "The Godfather", "Francis Ford Coppola", 9.99));
	    peliculas.add(new Pelicula("5", "Avatar", "James Cameron", 10.99));
	    
	    
		System.out.println("         APLICACI�N DE SERVIDOR            ");
		System.out.println("-------------------------------------------");		
		
		int peticion = 0;
		
		try (ServerSocket servidor = new ServerSocket()){			
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);

			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			
			while (true) {
				//Por cada peticion de cliente aceptada se me crea un objeto socket diferente
				Socket socketAlCliente = servidor.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
				//Abrimos un hilo nuevo y liberamos el hilo principal para que pueda
				//recibir peticiones de otros clientes
				new ServidorHilo(socketAlCliente, server);
			}			
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	}
	
    public Pelicula buscarPeliculaPorID(String id) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getId().equals(id)) {
                return pelicula;
            }
        }
        return null;
    }
    
    public Pelicula buscarPeliculaPorTitulo(String titulo) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }
    
    public List<Pelicula> buscarPeliculasPorDirector(String director) {
        List<Pelicula> peliculasPorDirector = new ArrayList<>();
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                peliculasPorDirector.add(pelicula);
            }
        }
        return peliculasPorDirector;
    }
    
    public synchronized boolean agregarPelicula(Pelicula nuevaPelicula) {
    	
    	System.out.println("Soy la pelicula" + nuevaPelicula + "y voy a guardarme");
    	
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	            if (!peliculaExiste(nuevaPelicula.getId())) {
	                peliculas.add(nuevaPelicula);
	                return true;
	            } else {
	                System.out.println("La película con el mismo ID ya existe.");
	                return false;
	            }
        
    }
    
    private boolean peliculaExiste(String id) {
    	
            for (Pelicula pelicula : peliculas) {
                if (pelicula.getId().equals(id)) {
                    return true; // La película con el mismo ID ya existe
                }
            }
            return false; // La película no existe
        }
    
}
