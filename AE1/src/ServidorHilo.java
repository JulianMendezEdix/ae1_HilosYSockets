
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Este hilo va a contar el numero de letras que tiene la frase que le manda el 
//cliente.

//La conexion se mantendra abierta hasta que el cliente mande la palabra
//"FIN" al servidor.

//Recibe el socket que abre el servidor con el cliente y con el que
//mantendra la conversacion
public class ServidorHilo implements Runnable{
	
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;	
	
	public ServidorHilo(Socket socketAlCliente) {
		numCliente++;
		hilo = new Thread(this, "Cliente_"+numCliente);
		this.socketAlCliente = socketAlCliente;
		hilo.start();
	}
	
	@Override
	public void run() {
		System.out.println("Estableciendo comunicacion con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		
		try {
			//Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String opcion = "";
			boolean continuar = true;
			String texto = "";
			Servidor server = new Servidor();
			Pelicula pelicula;
			List<Pelicula> peliculas = new ArrayList<>();
			
			
			//Procesaremos entradas hasta que la opcion sea la 5 (salir del programa)
			while (continuar) {
				
				String[] palabras =  entradaBuffer.readLine().split("~");
				opcion = palabras[0];
				
				switch (opcion) {
				
					case "1":
						texto = palabras[1];
						pelicula = server.buscarPeliculaPorID(texto);
						System.out.println(hilo.getName() + " da la ID: " + texto + " y corresponde a: " 
								+ pelicula);
						salida.println(pelicula);
						break;
						
					case "2":
						texto = palabras[1];
						pelicula = server.buscarPeliculaPorTitulo(texto);
						System.out.println(hilo.getName() + " da el titulo: " + texto + " y corresponde a: " 
								+ pelicula);
						salida.println(pelicula);
						break;
						
					case "3":
						texto = palabras[1];
						peliculas = server.buscarPeliculasPorDirector(texto);
						System.out.println(hilo.getName() + " da el director: " + texto + " y corresponde a: " 
								+ peliculas);
						salida.println(peliculas);
						break;
					
					case "4":
						pelicula = new Pelicula(palabras[1],palabras[2], palabras[3], Double.parseDouble(palabras[4]));
						boolean añadida = server.agregarPelicula(pelicula);
						System.out.println(añadida);
						if (añadida) {
							salida.println(hilo.getName() + " ha añadido:" + pelicula);}
						else
							salida.println(hilo.getName() + " no ha añadido la película porque el ID ya existe)");
							
						break;

					case "5":
						salida.println("SALIR");
						System.out.println(hilo.getName() + " ha cerrado la comunicacion");
						continuar = false;
						break;
						
					case "99101099":
						System.out.println(hilo.getName() + " ha intropducido una opcion erronea");
						salida.println("Opcion erronea. try again");
				}
			}
			//Cerramos el socket
			socketAlCliente.close();
			//Notese que si no cerramos el socket ni en el servidor ni en el cliente, mantendremos
			//la comunicacion abierta
		} catch (IOException e) {
			System.err.println("ServidorHilo: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Servidorhilo: Error");
			e.printStackTrace();
		}
	}
}
