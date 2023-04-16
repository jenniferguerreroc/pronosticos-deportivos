package integrador_pronosticos;

import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class PronosticosDeportivos {
	public static void main(String[] args) {

		Path archivoResultado = Paths.get("src\\integrador_pronosticos\\Resultados.txt");
		Path archivoPuntos = Paths.get("src\\integrador_pronosticos\\PuntajePartidos.txt");
		List<Partido> partidos = cargarResultados(archivoResultado);
		List<Puntajes> puntos = cargarPuntos(archivoPuntos);
		List<Pronostico> pronosticos = cargarPronosticosBD();
		List<Resultado> resultados = determinarResultados(partidos);
		Map<String, Integer> puntosPorParticipantes = calcularPuntos(pronosticos, resultados, puntos);

	}

	public static List<Partido> cargarResultados(Path archivoResultado) {
		// leer el archivo resultado y asignación de variables
		List<Partido> partidos = new ArrayList<>();
		try {
			for (String linea : Files.readAllLines(archivoResultado)) {
				String[] separar = linea.split(",");
				// si la cantidad de datos es distinta a 5 lanzar error
				if (separar.length != 6) {
					throw new IllegalArgumentException("El archivo no contiene la cantidad de datos requeridos.");
				}
				int fase = Integer.parseInt(separar[0]);
				int ronda = Integer.parseInt(separar[1]);
				String equipo1 = separar[2];
				int goles1 = Integer.parseInt(separar[3]);
				int goles2 = Integer.parseInt(separar[4]);
				String equipo2 = separar[5];
				partidos.add(new Partido(equipo1, goles1, equipo2, goles2));
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados.");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("El tipo de dato ingresado en goles debe ser numérico.");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
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

	public static List<Puntajes> cargarPuntos(Path archivoPuntos) {
		// leer el archivo de puntajes
		List<Puntajes> puntos = new ArrayList<>();
		try {
			for (String linea : Files.readAllLines(archivoPuntos)) {
				String[] separar = linea.split(",");
				int extraRonda = Integer.parseInt(separar[1]);
				int extraFase = Integer.parseInt(separar[3]);
				int gana = Integer.parseInt(separar[5]);
				int empata = Integer.parseInt(separar[7]);
				int pierde = Integer.parseInt(separar[9]);
				puntos.add(new Puntajes(extraRonda, extraFase, gana, empata, pierde));
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo de resultados.");
			e.printStackTrace();
		}
		return puntos;
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

	public static Map<String, Integer> calcularPuntos(List<Pronostico> pronosticos, List<Resultado> resultados, List<Puntajes> puntos) {
		Map<String, Integer> puntosPorParticipante = new HashMap<>();

		for (Pronostico pronostico : pronosticos) {
			puntosPorParticipante.put(pronostico.getParticipante(), 0);
		}

		for (Puntajes punto : puntos) {
			for (Pronostico pronostico : pronosticos) {
				int puntosActuales = puntosPorParticipante.get(pronostico.getParticipante());
				for (Resultado resultado : resultados) {
					// condicional que los equipos de pronostico y resultados coincidan
					if (pronostico.getEquipo1().equals(resultado.getEquipo1())
							&& pronostico.getEquipo2().equals(resultado.getEquipo2())) {
						// conficional para determinar los puntos
						if (pronostico.getResultado1().equals(resultado.getResultado1())
								&& resultado.getResultado1() == "Gana") {
							puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + punto.getGana());
							;
							System.out.println(pronostico.getParticipante() + " acertó el pronostico de "
									+ resultado.getEquipo1() + " vs " + resultado.getEquipo2());
						} else if (pronostico.getResultado1().equals(resultado.getResultado1())
								&& resultado.getResultado1() == "Empata") {
							puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + punto.getEmpata());
							System.out.println(pronostico.getParticipante() + " acertó el pronostico de "
									+ resultado.getEquipo1() + " vs " + resultado.getEquipo2());
						} else if (pronostico.getEquipo1().equals(resultado.getEquipo1())) {
							puntosPorParticipante.put(pronostico.getParticipante(), puntosActuales + punto.getPierde());
							System.out.println(pronostico.getParticipante() + " no acertó el pronostico de "
									+ resultado.getEquipo1() + " vs " + resultado.getEquipo2());
						}

					}

				}
			}
		}
		// imprimir el resultado de cada participante
		for (String participante : puntosPorParticipante.keySet()) {
			System.out.println(
					participante + " obtuvo un total de: " + puntosPorParticipante.get(participante) + " puntos");
		}
		return puntosPorParticipante;
	}

}
