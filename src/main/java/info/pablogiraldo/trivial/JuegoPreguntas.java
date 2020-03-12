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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
//import java.io.IOException;

//import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;

public class JuegoPreguntas {

	private String jugadorActual;
	private int puntos;

	private String rutaDatosPreguntas;
	private String rutaDatosPuntuaciones;
	private String rutaExportarXml;
	private String rutaImportarExcel;
	private String rutaExportarPdf;

	private ArrayList<Pregunta> preguntas;

	private ArrayList<Puntuacion> puntuaciones;

	private ArrayList<String> nombresJugadores;

	public JuegoPreguntas() {
		this.jugadorActual = "";
		this.puntos = 0;
		this.rutaDatosPreguntas = "txt/preguntas.txt";
		this.rutaDatosPuntuaciones = "txt/puntuaciones.txt";
		this.rutaExportarXml = "C:\\Users\\Public\\Desktop\\preguntas.xml";
		this.rutaImportarExcel = "xls/preguntas.xls";
		this.rutaExportarPdf = "C:\\Users\\Public\\Desktop\\partida.pdf";
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
			System.out.println("5.Importar preguntas");
			System.out.println("6.Ver puntuaciones");
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
				this.importarExcel();
				break;

			case 6:
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

		this.jugadorActual = "";
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
		System.out.println("\n");
		System.out.println("Puntos conseguidos: " + this.puntos);
		this.datosJugador(sc);

	}

	public void datosJugador(Scanner sc) {

		String nombreJugador = "";
		String usrIn = "";
		int opcionPdf = -1;

		System.out.println("\n");
		System.out.println("Nombre:");
		nombreJugador = sc.nextLine();
		this.jugadorActual = nombreJugador;

		if (!this.nombresJugadores.contains(nombreJugador)) {
			Puntuacion puntuacion = new Puntuacion(nombreJugador, this.puntos);
			this.puntuaciones.add(puntuacion);

			this.guardarPuntuaciones();

		} else {
			for (int i = 0; i < this.puntuaciones.size(); i++) {
				if (puntuaciones.get(i).getNombre().equals(nombreJugador)
						&& puntuaciones.get(i).getPuntos() < this.puntos) {
					puntuaciones.get(i).setPuntos(this.puntos);
				}
			}

			this.guardarPuntuaciones();
		}

		System.out.println("\n");
		System.out.println("¿Quieres un informe de la partida?");
		System.out.println("1.Sí");
		System.out.println("2.No");
		System.out.println("Opción:");
		usrIn = sc.nextLine();
		opcionPdf = Integer.parseInt(usrIn);

		if (opcionPdf == 1) {
			this.exportarPdf();
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
				pregunta.addContent(new Element("opcion2").setText(preg.getOpcion2()));
				pregunta.addContent(new Element("opcion3").setText(preg.getOpcion3()));
				pregunta.addContent(new Element("respuesta").setText(Integer.toString(preg.getRespuesta())));

				doc.getRootElement().addContent(pregunta);
			}

			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(this.rutaExportarXml));

			System.out.println("\n");
			System.out.println("preguntas.xml exportado al escritorio.");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	public void importarExcel() {

		Workbook workbook = null;
		try {

			workbook = Workbook.getWorkbook(new File(this.rutaImportarExcel));

			Sheet sheet = workbook.getSheet(0);
			int numRows = sheet.getRows();

			for (int i = 1; i < numRows; i++) {

				Cell preguntar = sheet.getCell(0, i);
				String preg = "¿" + preguntar.getContents() + "?";
				Cell opcion1 = sheet.getCell(1, i);
				String op1 = opcion1.getContents();
				Cell opcion2 = sheet.getCell(2, i);
				String op2 = opcion2.getContents();
				Cell opcion3 = sheet.getCell(3, i);
				String op3 = opcion3.getContents();
				Cell respuesta = sheet.getCell(4, i);
				String res = respuesta.getContents();

				Pregunta nuevaPregunta = new Pregunta();

				nuevaPregunta.setPregunta(preg);
				nuevaPregunta.setOpcion1(op1);
				nuevaPregunta.setOpcion2(op2);
				nuevaPregunta.setOpcion3(op3);
				nuevaPregunta.setRespuesta(Integer.parseInt(res));

				this.preguntas.add(nuevaPregunta);

			}

			this.guardarPreguntas();
			System.out.println("\n");
			System.out.println("preguntas.xls importado.");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} finally {

			if (workbook != null) {
				workbook.close();
			}

		}
	}

	public void exportarPdf() {

		com.itextpdf.text.Document document = new com.itextpdf.text.Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream(new File(this.rutaExportarPdf)));

			document.open();

			float fntSize, lineSpacing;
			fntSize = 35.0f;
			lineSpacing = 10f;
			com.itextpdf.text.Paragraph p = new com.itextpdf.text.Paragraph(new Phrase(lineSpacing,
					"Informe " + this.jugadorActual, FontFactory.getFont(FontFactory.HELVETICA, fntSize)));

			p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			p.setSpacingBefore(50.0f);

			document.add(p);

			fntSize = 25.0f;
			lineSpacing = 10f;
			com.itextpdf.text.Paragraph p1 = new com.itextpdf.text.Paragraph(new Phrase(lineSpacing,
					"Puntos: " + this.puntos, FontFactory.getFont(FontFactory.HELVETICA, fntSize)));

			p1.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			p1.setSpacingBefore(50.0f);
			p1.setSpacingAfter(50.0f);

			document.add(p1);

			for (int i = 0; i < this.preguntas.size(); i++) {
				Pregunta preg = this.preguntas.get(i);

				fntSize = 20.0f;
				lineSpacing = 10f;
				com.itextpdf.text.Paragraph p3 = new com.itextpdf.text.Paragraph(new Phrase(lineSpacing,
						preg.getPregunta(), FontFactory.getFont(FontFactory.HELVETICA, fntSize)));

				p3.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				p3.setSpacingBefore(30.0f);

				document.add(p3);

				com.itextpdf.text.Paragraph p4 = new com.itextpdf.text.Paragraph(new Phrase(lineSpacing,
						preg.getOpcion1() + " | " + preg.getOpcion2() + " | " + preg.getOpcion3(),
						FontFactory.getFont(FontFactory.HELVETICA, fntSize)));

				p4.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				p4.setSpacingBefore(30.0f);

				document.add(p4);

				com.itextpdf.text.Paragraph p5 = new com.itextpdf.text.Paragraph(
						new Phrase(lineSpacing, "Respuesta correcta: " + Integer.toString(preg.getRespuesta()),
								FontFactory.getFont(FontFactory.HELVETICA, fntSize)));

				p5.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				p5.setSpacingBefore(30.0f);
				p5.setSpacingAfter(30.0f);

				document.add(p5);
			}

			document.close();

			System.out.println("\n");
			System.out.println("trivial.pdf exportado al escritorio.");

		} catch (FileNotFoundException | com.itextpdf.text.DocumentException e) {
			e.printStackTrace();
		}

	}

}
