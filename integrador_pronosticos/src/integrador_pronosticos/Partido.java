package integrador_pronosticos;

public class Partido {
	private int ronda;
	private String equipo1;
	private String equipo2;
	private int goles1;
	private int goles2;
	
	public Partido (int ronda, String equipo1, int goles1, String equipo2, int goles2) {
		this.ronda = ronda;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.goles1 = goles1;
		this.goles2 = goles2;
	}

	public Partido() {
		this.ronda = 0;
		this.equipo1 = "";
		this.equipo2 = "";
		this.goles1 = 0;
		this.goles2 = 0;
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
	
	public String getEquipo2() {
		return equipo2;
	}
	
	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}
	
	public int getGoles1() {
		return goles1;
	}
	
	public void setGole1(int goles1) {
		this.goles1 = goles1;
	}
	
	public int getGoles2() {
		return goles2;
	}
	
	public void setGole2(int goles2) {
		this.goles2 = goles2;
	}

	@Override
	public String toString() {
		return "Partido [ronda=" + ronda + ", equipo1=" + equipo1 + ", equipo2=" + equipo2 + ", goles1=" + goles1
				+ ", goles2=" + goles2 + "]";
	}

}
