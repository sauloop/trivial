package info.pablogiraldo.trivial;

public class Puntuacion implements Comparable<Puntuacion> {

	private String nombre;
	private int puntos;

	public Puntuacion() {
	}

	public Puntuacion(String nombre, int puntos) {
		this.nombre = nombre;
		this.puntos = puntos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	@Override
	public int compareTo(Puntuacion comparepuntuacion) {
		int comparepuntos = ((Puntuacion) comparepuntuacion).getPuntos();
		/* For Ascending order */
//		return this.puntos - comparepuntos;

		/* For Descending order do like this */
		// return compareage-this.studentage;
		return comparepuntos - this.puntos;
	}

	@Override
	public String toString() {
		return nombre + ": " + puntos;
	}

}
