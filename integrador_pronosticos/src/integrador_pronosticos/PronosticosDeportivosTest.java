package integrador_pronosticos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PronosticosDeportivosTest {

	@Test
	public void testCalcularPuntaje() {
		//se agregan al jugador a la lista
		Pronostico pro1 = new Pronostico("Sol","Chile","Gana","Bolivia","Pierde");
		Pronostico pro2 = new Pronostico("Sol","España","Empata","Argentina","Empata");
		PronosticosDeportivos.pronosticos.add(pro1);
		PronosticosDeportivos.pronosticos.add(pro2);
		//se agrega el partido a la lista
		Partido part1 = new Partido(1, "Chile", 2, "Bolivia", 1);
		Partido part2 = new Partido(1, "España", 0, "Argentina", 1);
		PronosticosDeportivos.partidos.add(part1);
		PronosticosDeportivos.partidos.add(part2);
		//metodos para determinar resultado y puntajes
		PronosticosDeportivos.determinarResultados();
		PronosticosDeportivos.calcularPuntos();
		//resultado esperado
		assertEquals(1, (int) PronosticosDeportivos.puntosPorParticipante.get("Sol"));
		assertEquals(1, (int) PronosticosDeportivos.puntosPorParticipante.get("Sol"));
	}

}
