package integrador_pronosticos;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Pronosticos {

	public static void main(String[] args) {
		String archivoPronostico = "C:\\Users\\54116\\Documents\\Git\\pronosticos-deportivos\\integrador_pronosticos\\src\\integrador_pronosticos\\Pronosticos.txt";
		String archivoResultado = "C:\\Users\\54116\\Documents\\Git\\pronosticos-deportivos\\integrador_pronosticos\\src\\integrador_pronosticos\\Resultados.txt";
		String equipo1;
		String equipo2;
		String goles1;
		String goles2;
		
		try {
            Scanner sc = new Scanner(new File(archivoPronostico));
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                equipo1 = separar[0];
                goles1 = separar[1];
                equipo2 = separar[3];
                goles2 = separar[4];
            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de pronosticos");
            e.printStackTrace();
        }
		
		
	}

}
