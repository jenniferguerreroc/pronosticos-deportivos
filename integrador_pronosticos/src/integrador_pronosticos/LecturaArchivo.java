package integrador_pronosticos;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LecturaArchivo {

	public static void main(String[] args) {
		
		//variables de los archivos
		Path archivoPronostico = Paths.get("src\\integrador_pronosticos\\Pronosticos.txt");
		Path archivoResultado = Paths.get("src\\integrador_pronosticos\\Resultados.txt");

		//listas
		List<Pronostico> pronosticos = new ArrayList<>();
		List<Partido> partidos = new ArrayList<>();
		List<Resultado> resultados = new ArrayList<>();
		
		//mapa para llevar un conteo de los puntos de cada participante
		Map<String, Integer> puntosPorParticipante = new HashMap<>();
		
		//leer el archivo pronostico linea por linea y asignación de variables equipo y resultado
		try {
            Scanner sc = new Scanner(archivoPronostico);
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                String participante = separar[0];
                String equipo1 = separar[1];
                String resultado1 = separar[2];
                String equipo2 = separar[3];
                String resultado2 = separar[4];
                pronosticos.add(new Pronostico(participante, equipo1, resultado1, equipo2, resultado2));
            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de pronosticos");
            e.printStackTrace();
        }
		
		//leer el archivo resultado linea por linea y asignación de variables equipos y goles
		try {
            Scanner sc = new Scanner(archivoResultado);
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String [] separar = linea.split(",");
                int ronda = Integer.parseInt(separar[0]);
                String equipo1 = separar[1];
                int goles1 = Integer.parseInt(separar[2]);
                int goles2 = Integer.parseInt(separar[3]);
                String equipo2 = separar[4];
                partidos.add(new Partido(ronda, equipo1, goles1, equipo2, goles2));
            } 
		}   catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados");
            e.printStackTrace();
        }
		
		//condicional para determinar el resultado de cada partido
		for(Partido partido : partidos) {
			Resultado resultado = new Resultado();
			if(partido.getGoles1()>partido.getGoles2()) {
				resultado.setEquipo1(partido.getEquipo1());
				resultado.setResultado1("Gana");
				resultado.setEquipo2(partido.getEquipo2());
				resultado.setResultado2("Pierde");
			} else if (partido.getGoles2()>partido.getGoles1()) {
				resultado.setEquipo2(partido.getEquipo2());
				resultado.setResultado2("Gana");
				resultado.setEquipo1(partido.getEquipo1());
				resultado.setResultado1("Pierde");
			} else if (partido.getGoles1() == partido.getGoles2()) {
				resultado.setEquipo1(partido.getEquipo1());
				resultado.setResultado1("Empata");
				resultado.setEquipo2(partido.getEquipo2());
				resultado.setResultado2("Empata");
			}
			resultados.add(resultado);
		}
		
		//inicializamos el mapa con todos los participantes y 0 puntos
		for (Pronostico pronostico : pronosticos) {
		    puntosPorParticipante.put(pronostico.getParticipante(), 0);
		}
		
		//condicional para determinar si el usuario acertó
		for (Pronostico pronostico : pronosticos) {
			for (Resultado resultado : resultados) {
				if(pronostico.getEquipo1().equals(resultado.getEquipo1()) && pronostico.getResultado1().equals(resultado.getResultado1())) {
					int puntosActuales = puntosPorParticipante.get(pronostico.getParticipante());
	                puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + 1);
				}
			}
		}
		
		//imprimir el resultado dee cada participante
		for (String participante : puntosPorParticipante.keySet()) {
		    System.out.println(participante + ": " + puntosPorParticipante.get(participante) + " puntos");
		}
	}

}
