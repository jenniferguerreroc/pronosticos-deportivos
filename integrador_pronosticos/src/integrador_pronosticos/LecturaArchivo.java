package integrador_pronosticos;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LecturaArchivo {

	public static void main(String[] args) {
		
		//variables de los archivos
		Path archivoPronostico = Paths.get("src\\integrador_pronosticos\\Pronosticos.txt");
		Path archivoResultado = Paths.get("src\\integrador_pronosticos\\Resultados.txt");
		
		//variables de los pronosticos
		String equipoPronostico;
		String resultadoPronostico;
		
		//variables de los resultados
		String equipo1;
		String equipo2;
		int goles1;
		int goles2;
		
		//constructores
		Pronostico pronostico[] = new Pronostico[2];
		Partido partido[] = new Partido[2];
		Resultado resultado[] = new Resultado[2];
		
		//leer el archivo pronostico linea por linea y asignación de variables equipo y resultado
		try {
            Scanner sc = new Scanner(archivoPronostico);
            int i=0;
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                equipoPronostico = separar[0];
                resultadoPronostico = separar[1];
                pronostico[i] = new Pronostico(equipoPronostico, resultadoPronostico);
                i++;
                
            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de pronosticos");
            e.printStackTrace();
        }
		
		//leer el archivo resultado linea por linea y asignación de variables equipos y goles
		try {
            Scanner sc = new Scanner(archivoResultado);
            int i = 0;
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                equipo1 = separar[0];
                goles1 = Integer.parseInt(separar[1]);
                goles2 = Integer.parseInt(separar[2]);
                equipo2 = separar[3];
                partido[i] = new Partido(equipo1, goles1, equipo2, goles2);
                i++;
            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados");
            e.printStackTrace();
        }
		

		//condicional para determinar resultado del partido
		for(int i=0;i<2;i++) {
			resultado[i] = new Resultado();
			if(partido[i].getGoles1()>partido[i].getGoles2()) {
				resultado[i].setResultado("G");
				resultado[i].setEquipo(partido[i].getEquipo1());
			} else if (partido[i].getGoles2()>partido[i].getGoles1()) {
				resultado[i].setResultado("G");
				resultado[i].setEquipo(partido[i].getEquipo2());
			} else if (partido[i].getGoles1() == partido[i].getGoles2()) {
				resultado[i].setResultado("E");
				resultado[i].setEquipo(pronostico[i].getEquipo());
			}
		}
		
		//contador de puntos
		int puntaje=0;
		for(int i=0; i<2; i++) {
			//condicional para determinar si el usuario acertó
			if(pronostico[i].getEquipo().equals(resultado[i].getEquipo()) && pronostico[i].getResultado().equals(resultado[i].getResultado())) {
				puntaje +=1;
				System.out.println("Obtiene " + puntaje + " punto");
			} else {
				System.out.println("No obtiene puntos");
			}
		}

	}

}
