
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//El cliente manda opciones al servidor y información relevante.
//La conexion se mantendra abierta hasta que el cliente escoja la opcion de 
//salir del programa.

//En este caso se usara una misma conexion para todo el intercambio de mensajes
//del cliente al servidor
public class Cliente {
	
	// IP y Puerto a la que nos vamos a conectar
	public static final int PUERTO = 2018;
	public static final String IP_SERVER = "localhost";
	
	public static void main(String[] args) {
		System.out.println("        APLICACION CLIENTE         ");
		System.out.println("-----------------------------------");

		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		
		try (Scanner sc = new Scanner(System.in)){
						
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexion");
			Socket socketAlServidor = new Socket();
			socketAlServidor.connect(direccionServidor);
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + 
					" por el puerto " + PUERTO);

			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);
			
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
			
			String respuesta = "";
			boolean continuar = true;
			String opcion = "";
			String texto = "";
			
			do {
				
				System.out.println("Menú:");
                System.out.println("1. Consultar película por ID");
                System.out.println("2. Consultar película por Título");
                System.out.println("3. Consultar películas por Director");
                System.out.println("4. Añadir película");
                System.out.println("5. Salir");
                System.out.print("Elige una opción: ");
                
                opcion = sc.nextLine();
                
                switch (opcion) {
                
                case "1":
                	System.out.println("Introduce ID: ");
                	texto = sc.nextLine();
                    salida.println("1~" + texto);
                	break;
                case "2":
                	System.out.println("Introduce titulo: ");
                	texto = sc.nextLine();
                    salida.println("2~" + texto);
                	break;
                case "3":
                	System.out.println("Introduce director: ");
                	texto = sc.nextLine();
                    salida.println("3~" + texto);
                	break;
                case "4":
                	texto = "4~";
                	System.out.println("Introduce ID: ");
                	texto += sc.nextLine();
                	texto += "~";
                	System.out.println("Introduce titulo: ");
                	texto += sc.nextLine();
                	texto += "~";
                	System.out.println("Introduce director: ");
                	texto += sc.nextLine();
                	texto += "~";
                	System.out.println("Introduce precio: ");
                	texto += sc.nextLine();
                    salida.println(texto);
                    break;
                case "5":
                	texto = "5~INUTIL";
                	salida.println(texto);
                	break; 
                default:
                	texto = "99101099";
                	salida.println(texto);
                	break; 

                }
                
				System.out.println("CLIENTE: Esperando respuesta ...... ");	
				
				respuesta = entradaBuffer.readLine();
                
				if("SALIR".equalsIgnoreCase(respuesta)) {
					continuar = false;
				}else {
					System.out.println("CLIENTE: Servidor responde: " + respuesta);
				}		
             
			}
			
			while(continuar);		
			//Cerramos la conexion
			socketAlServidor.close();
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direcci�n" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");
	}
	
}
