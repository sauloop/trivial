package info.pablogiraldo.trivial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.io.IOException;
//import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class JuegoPreguntas {

	private int puntos;

	private String rutaDatosPreguntas;
	private String rutaDatosPuntuaciones;

	private ArrayList<Pregunta> preguntas;

	private ArrayList<Puntuacion> puntuaciones;

	private ArrayList<String> nombresJugadores;

	public JuegoPreguntas() {
		this.puntos = 0;
		this.rutaDatosPreguntas = "txt/preguntas.txt";
		this.rutaDatosPuntuaciones = "txt/puntuaciones.txt";
		this.preguntas = new ArrayList<Pregunta>();
		this.puntuaciones = new ArrayList<Puntuacion>();
		this.nombresJugadores = new ArrayList<String>();
	}

	public String getRutaDatosPreguntas() {
		return rutaDatosPreguntas;
	}

	public void setRutaDatosPreguntas(String rutaDatosPreguntas) {
		this.rutaDatosPreguntas = rutaDatosPreguntas;
	}

	public ArrayList<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(ArrayList<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public void cargarDatos() {
		this.cargarPreguntas();
		this.cargarPuntuaciones();
	}

	public void menu() {
//		this.cargarDatos();

		Scanner sc = new Scanner(System.in);

		String usrIn = "";
		int opcionMenu = -1;

		do {

			System.out.println("\n");
			System.out.println("1.Jugar");
			System.out.println("2.Añadir pregunta");
			System.out.println("3.Exportar preguntas");
			System.out.println("4.Instrucciones");
			System.out.println("5.Ver puntuaciones");
			System.out.println("0.Salir");

			System.out.println("\n");
			System.out.println("Opción:");
			usrIn = sc.nextLine();

			opcionMenu = Integer.parseInt(usrIn);

			switch (opcionMenu) {
			case 1:
				this.jugar(sc);
				break;

			case 2:
				this.addPregunta(sc);
				break;

			case 3:
				this.exportarXml();
				;
				break;

			case 4:
				this.instrucciones();
				;
				break;

			case 5:
				this.listarPuntuaciones();
				break;

			}

		} while (opcionMenu != 0);

		sc.close();
	}

	public void cargarPreguntas() {

		try {
			FileReader fr = new FileReader(rutaDatosPreguntas);
			BufferedReader br = new BufferedReader(fr);

			String linea = br.readLine();

			while (linea != null) {
				String[] camposPregunta = linea.split("#");

				Pregunta pregunta = new Pregunta(camposPregunta[0], camposPregunta[1], camposPregunta[2],
						camposPregunta[3], Integer.parseInt(camposPregunta[4]));

				this.preguntas.add(pregunta);

				linea = br.readLine();
			}

			br.close();

		} catch (Exception e) {
			System.out.println("Problema al cargar preguntas: " + e.getMessage());
		}
	}

	public void cargarPuntuaciones() {

		try {
			FileReader fr = new FileReader(rutaDatosPuntuaciones);
			BufferedReader br = new BufferedReader(fr);

			String linea = br.readLine();

			while (linea != null) {
				String[] camposPuntuacion = linea.split("#");

				Puntuacion puntuacion = new Puntuacion(camposPuntuacion[0], Integer.parseInt(camposPuntuacion[1]));

				this.puntuaciones.add(puntuacion);

				this.nombresJugadores.add(puntuacion.getNombre());

				linea = br.readLine();
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Problema al cargar puntuaciones: " + e.getMessage());
		}
	}

	public void guardarPreguntas() {

		try {
			FileWriter fw = new FileWriter(rutaDatosPreguntas, false);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < this.preguntas.size(); i++) {

				bw.write(this.preguntas.get(i).getPregunta() + "#" + this.preguntas.get(i).getOpcion1() + "#"
						+ this.preguntas.get(i).getOpcion2() + "#" + this.preguntas.get(i).getOpcion3() + "#"
						+ this.preguntas.get(i).getRespuesta() + "\n");

			}

			bw.close();
		} catch (Exception e) {
			System.out.println("Problema al guardar preguntas: " + e.getMessage());
		}
	}

	public void guardarPuntuaciones() {

		try {
			FileWriter fw = new FileWriter(rutaDatosPuntuaciones, false);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < this.puntuaciones.size(); i++) {

				bw.write(this.puntuaciones.get(i).getNombre() + "#" + this.puntuaciones.get(i).getPuntos() + "\n");

			}

			bw.close();
		} catch (Exception e) {
			System.out.println("Problema al guardar puntuaciones: " + e.getMessage());
		}
	}

	public void addPregunta(Scanner sc) {

		Pregunta nuevaPregunta = new Pregunta();

		System.out.println("\n");
		System.out.println("Pregunta:");
		nuevaPregunta.setPregunta(sc.nextLine());
		System.out.println("Opción 1:");
		nuevaPregunta.setOpcion1(sc.nextLine());
		System.out.println("Opción 2:");
		nuevaPregunta.setOpcion2(sc.nextLine());
		System.out.println("Opción 3:");
		nuevaPregunta.setOpcion3(sc.nextLine());
		System.out.println("Respuesta:");
		nuevaPregunta.setRespuesta(Integer.parseInt(sc.nextLine()));

		this.preguntas.add(nuevaPregunta);

		this.guardarPreguntas();
	}

	public void jugar(Scanner sc) {

		this.puntos = 0;

		for (int i = 0; i < this.preguntas.size(); i++) {
			Pregunta pregunta = this.preguntas.get(i);
			int respuesta = 0;

			System.out.println("\n");
			System.out.println(pregunta.getPregunta());
			System.out.println(pregunta.getOpcion1());
			System.out.println(pregunta.getOpcion2());
			System.out.println(pregunta.getOpcion3());
			System.out.println("\n");
			System.out.println("Respuesta:");
			respuesta = Integer.parseInt(sc.nextLine());

			if (respuesta == pregunta.getRespuesta()) {
				this.puntos++;
			}
		}

		this.datosJugador(sc);

	}

	public void datosJugador(Scanner sc) {

		String nombre = "";

		System.out.println("\n");
		System.out.println("Nombre:");
		nombre = sc.nextLine();

		if (!this.nombresJugadores.contains(nombre)) {
			Puntuacion puntuacion = new Puntuacion(nombre, this.puntos);
			this.puntuaciones.add(puntuacion);

			this.guardarPuntuaciones();

		} else {
			for (int i = 0; i < this.puntuaciones.size(); i++) {
				if (puntuaciones.get(i).getNombre().equals(nombre) && puntuaciones.get(i).getPuntos() < this.puntos) {
					puntuaciones.get(i).setPuntos(this.puntos);
				}
			}

			this.guardarPuntuaciones();
		}

	}

	public void instrucciones() {
		System.out.println("\n");
		System.out.println("Introduce el número de opción para responder a las preguntas.");
	}

	public void listarPuntuaciones() {

		Collections.sort(this.puntuaciones);

		System.out.println("\n");
		System.out.println("Puntuaciones:");
		if (this.puntuaciones.size() > 0) {
			for (Puntuacion puntuacion : this.puntuaciones) {
				System.out.println(puntuacion);
			}

		} else {
			System.out.println("No hay puntuaciones guardadas.");
		}

	}

	public void exportarXml() {

		try {

			Element preguntas = new Element("preguntas");
			Document doc = new Document(preguntas);
			doc.setRootElement(preguntas);

			for (int i = 0; i < this.preguntas.size(); i++) {
				Pregunta preg = this.preguntas.get(i);

				Element pregunta = new Element("pregunta");

				pregunta.addContent(new Element("preguntar").setText(preg.getPregunta()));
				pregunta.addContent(new Element("opcion1").setText(preg.getOpcion1()));
				pregunta.addContent(new Element("opcion1").setText(preg.getOpcion2()));
				pregunta.addContent(new Element("opcion1").setText(preg.getOpcion3()));
				pregunta.addContent(new Element("respuesta").setText(Integer.toString(preg.getRespuesta())));

				doc.getRootElement().addContent(pregunta);
			}

			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("C:\\Users\\Public\\Desktop\\preguntas.xml"));

			System.out.println("\n");
			System.out.println("preguntas.xml exportado al escritorio.");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

}
