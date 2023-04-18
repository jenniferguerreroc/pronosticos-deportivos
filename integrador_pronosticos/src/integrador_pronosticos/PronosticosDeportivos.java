package integrador_pronosticos;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class PronosticosDeportivos {
	public static void main(String[] args) {
		Path archivoResultado = Paths.get("src\\integrador_pronosticos\\Resultados.txt");
		Path archivoPuntos = Paths.get("src\\integrador_pronosticos\\PuntajePartidos.txt");
		List<Partido> partidos = cargarResultados(archivoResultado);
		int[] puntos = cargarPuntos(archivoPuntos);
		List<Pronostico> pronosticos = cargarPronosticosBD();
		List<Resultado> resultados = determinarResultados(partidos);
		Map<String, Integer> puntosPorParticipante = calcularPuntos(pronosticos, resultados, puntos);
		imprimirParticipantes(puntosPorParticipante);
		buscarParticipante(puntosPorParticipante,"Maria"); //opcional
	}

	public static void procesarDatos(String[] datos) throws CantidadDatosException {
		// lanzar error si la cantidad de datos no es correcta
		if (datos.length != 6) {
			throw new CantidadDatosException("El archivo no contiene la cantidad de datos requeridos.");
		}
	}

	public static List<Partido> cargarResultados(Path archivoResultado) {
		// leer el archivo resultado y asignación de variables
		List<Partido> partidos = new ArrayList<>();
		try {
			for (String linea : Files.readAllLines(archivoResultado)) {
				String[] datos = linea.split(",");
				procesarDatos(datos);
				int fase = Integer.parseInt(datos[0]);
				int ronda = Integer.parseInt(datos[1]);
				String equipo1 = datos[2];
				int goles1 = Integer.parseInt(datos[3]);
				int goles2 = Integer.parseInt(datos[4]);
				String equipo2 = datos[5];
				partidos.add(new Partido(equipo1, goles1, equipo2, goles2));
			}

		} catch (NumberFormatException e) {
			System.out.println("Goles debe ser un dato númerico");
		} catch (CantidadDatosException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados.");
			e.printStackTrace();
		}
		return partidos;
	}

	public static List<Pronostico> cargarPronosticosBD() {
		// leer los pronosticos desde una base de datos
		List<Pronostico> pronosticos = new ArrayList<>();
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://192.168.0.15:5432/PronosticosDeportivos",
					"postgres", "jenny");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from pronosticos");

			while (rs.next()) {
				String participante = rs.getString(2);
				String equipo1 = rs.getString(3);
				String resultado1 = rs.getString(4);
				String equipo2 = rs.getString(5);
				String resultado2 = rs.getString(6);
				pronosticos.add(new Pronostico(participante, equipo1, resultado1, equipo2, resultado2));
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return pronosticos;
	}

	public static int[] cargarPuntos(Path archivoPuntos) {
		// leer el archivo de puntajes
		int[] cantPuntos = new int[5];
		int i = 0;
		try {
			for (String linea : Files.readAllLines(archivoPuntos)) {
				String[] datos = linea.split(",");
				cantPuntos[i] = Integer.parseInt(datos[1]);
				i++;
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados.");
			e.printStackTrace();
		}
		return cantPuntos;
	}

	public static List<Resultado> determinarResultados(List<Partido> partidos) {
		// condicional para determinar el resultado de cada partido
		List<Resultado> resultados = new ArrayList<>();
		for (Partido partido : partidos) {
			Resultado resultado = new Resultado();
			if (partido.getGoles1() > partido.getGoles2()) {
				resultado.setEquipo1(partido.getEquipo1());
				resultado.setResultado1("Gana");
				resultado.setEquipo2(partido.getEquipo2());
				resultado.setResultado2("Pierde");
			} else if (partido.getGoles2() > partido.getGoles1()) {
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
		return resultados;
	}

	public static Map<String, Integer> calcularPuntos(List<Pronostico> pronosticos, List<Resultado> resultados, int[] puntos) {
		Map<String, Integer> puntosPorParticipante = new HashMap<>();

		for (Pronostico pronostico : pronosticos) {
			puntosPorParticipante.put(pronostico.getParticipante(), 0);
		}

		for (Pronostico pronostico : pronosticos) {
			int puntosActuales = puntosPorParticipante.get(pronostico.getParticipante());
			for (Resultado resultado : resultados) {
				// condicional que los equipos de pronostico y resultados coincidan
				if (pronostico.getEquipo1().equals(resultado.getEquipo1())
						&& pronostico.getEquipo2().equals(resultado.getEquipo2())) {
					// conficional para determinar los puntos
					if (pronostico.getResultado1().equals(resultado.getResultado1())
							&& pronostico.getResultado2().equals(resultado.getResultado2())
							&& !pronostico.getResultado1().equals("Empata")) {
						puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + puntos[2]);
						System.out.println(pronostico.getParticipante() + " acertó el pronostico de "
								+ resultado.getEquipo1() + " vs " + resultado.getEquipo2());
					} else if (pronostico.getResultado1().equals(resultado.getResultado1())
							&& resultado.getResultado1().equals("Empata")) {
						puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + puntos[3]);
						System.out.println(pronostico.getParticipante() + " acertó el pronostico de "
								+ resultado.getEquipo1() + " vs " + resultado.getEquipo2());
					} else {
						puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + puntos[4]);
						System.out.println(pronostico.getParticipante() + " no acertó el pronostico de "
								+ resultado.getEquipo1() + " vs " + resultado.getEquipo2());
					}
				}
			}
		}
		return puntosPorParticipante;
	}
	
	public static void imprimirParticipantes (Map<String, Integer> puntosPorParticipante) {
		// imprimir el resultado de todos los participantes
		for (String participante : puntosPorParticipante.keySet()) {
			System.out.println(participante + " obtuvo un total de: " + puntosPorParticipante.get(participante) + " puntos");
		}
	}
	
	public static void buscarParticipante (Map<String, Integer> puntosPorParticipante, String nombre) {
		// buscar un participante en especifico
	    if (puntosPorParticipante.containsKey(nombre)) {
	        System.out.println("El/la participante " + nombre + " obtuvo un total de " + puntosPorParticipante.get(nombre));
	    } else {
	        System.out.println("El participante no existe.");
	    }
	}

}
