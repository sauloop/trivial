package info.pablogiraldo.trivial;

public class Pregunta {

	private String pregunta;
	private String opcion1;
	private String opcion2;
	private String opcion3;
	private int respuesta;

	public Pregunta() {

	}

	public Pregunta(String pregunta, String opcion1, String opcion2, String opcion3, int respuesta) {
		this.pregunta = pregunta;
		this.opcion1 = opcion1;
		this.opcion2 = opcion2;
		this.opcion3 = opcion3;
		this.respuesta = respuesta;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getOpcion1() {
		return opcion1;
	}

	public void setOpcion1(String opcion1) {
		this.opcion1 = opcion1;
	}

	public String getOpcion2() {
		return opcion2;
	}

	public void setOpcion2(String opcion2) {
		this.opcion2 = opcion2;
	}

	public String getOpcion3() {
		return opcion3;
	}

	public void setOpcion3(String opcion3) {
		this.opcion3 = opcion3;
	}

	public int getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(int respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public String toString() {
		return "Pregunta [pregunta=" + pregunta + ", opcion1=" + opcion1 + ", opcion2=" + opcion2 + ", opcion3="
				+ opcion3 + ", respuesta=" + respuesta + "]";
	}

}
