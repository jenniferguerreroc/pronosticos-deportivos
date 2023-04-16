package integrador_pronosticos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

class PronosticosDeportivosTest {

	@Test
	public void testCalcularPuntaje() {
		//se agregan al jugador a la lista
		List<Pronostico> pronosticos = new ArrayList<>();
		Pronostico pro1 = new Pronostico("Sol","Chile","Gana","Bolivia","Pierde");
		Pronostico pro2 = new Pronostico("Sol","España","Empata","Argentina","Empata");
		pronosticos.add(pro1);
		pronosticos.add(pro2);
		//se agrega el partido a la lista
		List<Partido> partidos = new ArrayList<>();
		Partido part1 = new Partido("Chile", 2, "Bolivia", 1);
		Partido part2 = new Partido("España", 0, "Argentina", 1);
		partidos.add(part1);
		partidos.add(part2);
		//se agrega el puntaje
		List<Puntajes> puntos = new ArrayList<>();
		Puntajes pun = new Puntajes(1,2,2,1,0);
		puntos.add(pun);
		//metodos para determinar resultado y puntajes
		List<Resultado> resultados = PronosticosDeportivos.determinarResultados(partidos);
		Map<String, Integer> puntosPorParticipantes = PronosticosDeportivos.calcularPuntos(pronosticos, resultados, puntos);
		//resultado esperado
		assertEquals(2, puntosPorParticipantes.get("Sol").intValue());
	}

}
