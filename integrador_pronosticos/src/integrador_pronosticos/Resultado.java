package integrador_pronosticos;

public class Resultado {
	private int fase;
	private int ronda;
	private String equipo1;
	private String resultado1;
	private String equipo2;
	private String resultado2;
	
	public Resultado(int fase, int ronda,String equipo1, String resultado1, String equipo2, String resultado2) {
		this.fase = fase;
		this.ronda = ronda;
		this.equipo1 = equipo1;
		this.resultado1 = resultado1;
		this.equipo2 = equipo2;
		this.resultado2 = resultado2;
	}

	public Resultado() {
		// TODO Auto-generated constructor stub
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public int getRonda() {
		return ronda;
	}



	public void setRonda(int ronda) {
		this.ronda = ronda;
	}



	public String getEquipo1() {
		return equipo1;
	}
	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}
	public String getResultado1() {
		return resultado1;
	}
	public void setResultado1(String resultado1) {
		this.resultado1 = resultado1;
	}
	public String getEquipo2() {
		return equipo2;
	}
	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}
	public String getResultado2() {
		return resultado2;
	}
	public void setResultado2(String resultado2) {
		this.resultado2 = resultado2;
	}
	
	
	
}
