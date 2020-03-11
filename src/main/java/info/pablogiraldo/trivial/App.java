package info.pablogiraldo.trivial;

public class App {
	public static void main(String[] args) {
		JuegoPreguntas juegoPreguntas = new JuegoPreguntas();

		juegoPreguntas.cargarDatos();

//		juegoPreguntas.importarExcel();

//		juegoPreguntas.exportarPdf();

		juegoPreguntas.menu();

	}
}
