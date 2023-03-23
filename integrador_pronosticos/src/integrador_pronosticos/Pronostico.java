package integrador_pronosticos;

public class Pronostico {

	private String equipo;
	private String resultado;
	
	public Pronostico (String equipo, String resultado) {
		this.equipo = equipo;
		this.resultado = resultado;
	}
	
	public Pronostico () {
		this.equipo = "";
		this.resultado = "";
	}
	
	public String getEquipo() {
		return equipo;
	}
	
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	
	public String getResultado() {
		return resultado;
	}
	
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}


}
