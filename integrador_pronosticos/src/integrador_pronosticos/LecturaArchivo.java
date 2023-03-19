package integrador_pronosticos;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LecturaArchivo {

	public static void main(String[] args) {
		String archivoPronostico = "C:\\Users\\54116\\Documents\\Git\\pronosticos-deportivos\\integrador_pronosticos\\src\\integrador_pronosticos\\Pronosticos.txt";
		String archivoResultado = "C:\\Users\\54116\\Documents\\Git\\pronosticos-deportivos\\integrador_pronosticos\\src\\integrador_pronosticos\\Resultados.txt";
		String equipoPronostico;
		String resultadoPronostico;
		String equipo1;
		String equipo2;
		int goles1;
		int goles2;
		Pronostico pronostico1 = new Pronostico();
		Resultados resultado1 = new Resultados();

		
		//leer el archivo pronostico linea por linea y asignación de variables equipo y resultado
		try {
            Scanner sc = new Scanner(new File(archivoPronostico));
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                equipoPronostico = separar[0];
                resultadoPronostico = separar[1];
                pronostico1 = new Pronostico(equipoPronostico, resultadoPronostico);

            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de pronosticos");
            e.printStackTrace();
        }
		
		//leer el archivo resultado linea por linea y asignación de variables equipo y goles
		try {
            Scanner sc = new Scanner(new File(archivoResultado));
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                equipo1 = separar[0];
                goles1 = Integer.parseInt(separar[1]);
                goles2 = Integer.parseInt(separar[2]);
                equipo2 = separar[3];
                resultado1 = new Resultados(equipo1, goles1, equipo2, goles2);
                
            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados");
            e.printStackTrace();
        }
		
		
	}

}
