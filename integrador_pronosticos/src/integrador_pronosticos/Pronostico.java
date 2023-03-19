package integrador_pronosticos;

public class Pronostico {

	private String equipoPronostico;
	private String resultadoPronostico;
	
	public Pronostico (String equipo, String resultado) {
		this.equipoPronostico = equipo;
		this.resultadoPronostico = resultado;
	}
	
	public Pronostico () {
		this.equipoPronostico = "";
		this.resultadoPronostico = "";
	}
	
	public String getEquipoPronostico() {
		return equipoPronostico;
	}
	
	public void setEquipoPronostico(String equipo) {
		this.equipoPronostico = equipo;
	}
	
	public String getResultadoPronostico() {
		return resultadoPronostico;
	}
	
	public void setResultadoPronostico(String resultado) {
		this.resultadoPronostico = resultado;
	}


}
