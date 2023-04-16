package integrador_pronosticos;

public class Puntajes {
    private int extraRonda;
    private int extraFase;
    private int gana;
    private int empata;
    private int pierde;
    
	public Puntajes(int extraRonda, int extraFase, int gana, int empata, int pierde) {
		this.extraRonda = extraRonda;
		this.extraFase = extraFase;
		this.gana = gana;
		this.empata = empata;
		this.pierde = pierde;
	}

	public int getExtraRonda() {
		return extraRonda;
	}

	public void setExtraRonda(int extraRonda) {
		this.extraRonda = extraRonda;
	}

	public int getExtraFase() {
		return extraFase;
	}

	public void setExtraFase(int extraFase) {
		this.extraFase = extraFase;
	}

	public int getGana() {
		return gana;
	}

	public void setGana(int gana) {
		this.gana = gana;
	}

	public int getEmpata() {
		return empata;
	}

	public void setEmpata(int empata) {
		this.empata = empata;
	}

	public int getPierde() {
		return pierde;
	}

	public void setPierde(int pierde) {
		this.pierde = pierde;
	}
    
    
    
}
