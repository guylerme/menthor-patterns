package net.menthor.editor.v2.ui.controller;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;

/**
 * ============================================================================================
 * Menthor Editor -- Copyright (c) 2015 
 *
 * This file is part of Menthor Editor. Menthor Editor is based on TinyUML and as so it is 
 * distributed under the same license terms.
 *
 * Menthor Editor is free software; you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation; either 
 * version 2 of the License, or (at your option) any later version.
 *
 * Menthor Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Menthor Editor; 
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * ============================================================================================
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.tinyuml.ui.diagram.OntoumlEditor;
import org.tinyuml.umldraw.StructureDiagram;

import RefOntoUML.Association;
import RefOntoUML.Classifier;
import RefOntoUML.Derivation;
import RefOntoUML.Element;
import RefOntoUML.Meronymic;
import RefOntoUML.Package;
import RefOntoUML.PackageableElement;
import RefOntoUML.Relationship;
import net.menthor.editor.v2.MenthorDomain;
import net.menthor.editor.v2.ui.FrameUI;
import net.menthor.editor.v2.util.Util;
import net.menthor.ontouml2ecore.OntoUML2Ecore;
import net.menthor.ontouml2ecore.OntoUML2EcoreOption;
import net.menthor.ontouml2uml.OntoUML2UML;
import net.menthor.ontouml2uml.OntoUML2UMLOption;

public class ExportUIController {

	FrameUI frame = FrameUI.get();

	// -------- Lazy Initialization

	private static class ExportLoader {
		private static final ExportUIController INSTANCE = new ExportUIController();
	}

	public static ExportUIController get() {
		return ExportLoader.INSTANCE;
	}

	private ExportUIController() {
		if (ExportLoader.INSTANCE != null)
			throw new IllegalStateException(this.getClass().getName() + " already instantiated");
	}

	// ----------------------------

	public String lastRefOntoPath = new String();
	public String lastUmlPath = new String();
	public String lastEcorePath = new String();
	public String lastPngPath = new String();
	public String lastHtmlPath = new String();

	public File chooseRefOntoumlFile() throws IOException {
		return Util.chooseFile(frame, lastRefOntoPath, "Export - RefOntouml", "Reference OntoUML (*.refontouml)",
				"refontouml", true);
	}

	public File chooseEcoreFile() throws IOException {
		return Util.chooseFile(frame, lastEcorePath, "Export - Ecore", "Ecore (*.ecore)", "ecore", true);
	}

	public File chooseUMLFile() throws IOException {
		return Util.chooseFile(frame, lastUmlPath, "Export - UML", "UML2 (*.uml)", "uml", true);
	}

	public File choosePNGFile() throws IOException {
		return Util.chooseFile(frame, lastPngPath, "Export - PNG", "Portable Network Graphics (*.png)", "png", true);
	}

	public File chooseHTMLFile() throws IOException {
		return Util.chooseFile(frame, lastHtmlPath, "Export - HTML", "HyperText Markup Language  (*.html)", "html",
				true);
	}

	public void exportToReferenceOntouml() {
		try {
			File file = chooseRefOntoumlFile();
			if (file == null)
				return;
			CursorUIController.get().waitCursor();
			RefOntoUML.Package model = ProjectUIController.get().getProject().getModel();
			// save to an external resource
			ResourceSet rset = MenthorDomain.get().getResourceSet();
			Resource r = rset.createResource(URI.createFileURI(file.getAbsolutePath()));
			r.getContents().add(ProjectUIController.get().getProject().getModel());
			try {
				r.save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			// bring back reference in memory to the menthor resource
			ProjectUIController.get().getProject().getResource().getContents().add(model);
			lastRefOntoPath = file.getAbsolutePath();
			MessageUIController.get().showSuccess("Export - RefOntouml",
					"Project successfully exported to Reference OntoUML.\nLocation: " + lastRefOntoPath);
		} catch (Exception ex) {
			MessageUIController.get().showError(ex, "Export - RefOntouml",
					"Current project could not be exported to Reference OntoUML.");
		}
		CursorUIController.get().defaultCursor();
	}

	public void exportToEcore() {
		try {
			File file = chooseEcoreFile();
			if (file == null)
				return;
			CursorUIController.get().waitCursor();
			OntoUML2EcoreOption opt = new OntoUML2EcoreOption(false, false);
			OntoUML2Ecore.convertToEcore(ProjectUIController.get().getProject().getRefParser(), file.getAbsolutePath(),
					opt);
			lastEcorePath = file.getAbsolutePath();
			MessageUIController.get().showSuccess("Export - Ecore",
					"Project successfully exported to Ecore.\nLocation: " + lastEcorePath);
		} catch (Exception ex) {
			MessageUIController.get().showError(ex, "Export - Ecore", "Current project could not be exported to Ecore");
		}
		CursorUIController.get().defaultCursor();
	}

	public void exportToProfileUML() {
		try {
			File file = chooseUMLFile();
			if (file == null)
				return;
			CursorUIController.get().waitCursor();
			OntoUML2UMLOption opt = new OntoUML2UMLOption(false, false);
			OntoUML2UML.convertToUMLProfile(ProjectUIController.get().getProject().getRefParser(),
					file.getAbsolutePath(), opt);
			lastUmlPath = file.getAbsolutePath();
			MessageUIController.get().showSuccess("Export - UML Profile",
					"Project successfully exported to Profile UML.\nLocation: " + lastUmlPath);
		} catch (Exception ex) {
			MessageUIController.get().showError(ex, "Export - UML Profile",
					"Current project could not be exported to UML Profile");
		}
		CursorUIController.get().defaultCursor();
	}

	public void exportToUML() {
		try {
			File file = chooseUMLFile();
			if (file == null)
				return;
			CursorUIController.get().waitCursor();
			OntoUML2UMLOption opt = new OntoUML2UMLOption(false, false);
			OntoUML2UML.convertToUML(ProjectUIController.get().getProject().getRefParser(), file.getAbsolutePath(),
					opt);
			lastUmlPath = file.getAbsolutePath();
			MessageUIController.get().showSuccess("Export - UML",
					"Project successfully exported to UML.\nLocation: " + lastUmlPath);
		} catch (Exception ex) {
			MessageUIController.get().showError(ex, "Export - UML", "Current project could not be exported to UML.");
		}
		CursorUIController.get().defaultCursor();
	}

	public void exportToPng() {
		try {
			File file = choosePNGFile();
			if (file == null)
				return;
			OntoumlEditor editor = TabbedAreaUIController.get().getSelectedTopOntoumlEditor();
			List<Point> points = editor.getUsedCanvasSize();
			Point origin = points.get(0);
			Point end = points.get(1);
			BufferedImage image = new BufferedImage((int) end.x + 20, (int) end.y + 20, BufferedImage.TYPE_INT_RGB);
			editor.paintComponentNonScreen(image.getGraphics());
			BufferedImage croped = image.getSubimage(origin.x - 20, origin.y - 20, (end.x + 40 - origin.x),
					(end.y + 40 - origin.y));
			ImageIO.write(croped, "png", file);
		} catch (IOException ex) {
			MessageUIController.get().showError(ex, "Export - PNG",
					"Could not export current diagram into a PNG image.");
		}
	}

	public String exportToPng(StructureDiagram diagram) {
		String path;
		if (lastHtmlPath.equalsIgnoreCase(""))
			path = diagram.getName() + ".jpg";
		else
			path = lastHtmlPath + '\\' + diagram.getName() + ".jpg";

		try {
			File file = new File(path);
			OntoumlEditor editor = TabbedAreaUIController.get().getOntoumlEditor(diagram);
			List<Point> points = editor.getUsedCanvasSize();
			Point origin = points.get(0);
			Point end = points.get(1);
			BufferedImage image = new BufferedImage((int) end.x + 20, (int) end.y + 20, BufferedImage.TYPE_INT_RGB);
			editor.paintComponentNonScreen(image.getGraphics());

			BufferedImage croped;

			croped = image.getSubimage(origin.x, origin.y, (end.x + 2 - origin.x), (end.y + 2 - origin.y));

			ImageIO.write(croped, "png", file);
		} catch (IOException ex) {
			MessageUIController.get().showError(ex, "Export - PNG",
					"Could not export current diagram into a PNG image.");
		}
		return path;
	}

	public void exportToHtml(List<StructureDiagram> diagrams) {
		String path;
		String pattern;

		try {
			String htmlPath;
			if (lastHtmlPath.equalsIgnoreCase(""))
				htmlPath = "patterns.html";
			else
				htmlPath = lastHtmlPath + '\\' + "patterns.html";

			File file = new File(htmlPath);
			FileWriter fWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fWriter);

			for (StructureDiagram diagram : diagrams) {
				pattern = diagram.getName().replaceAll("HTMLDiagram", "");
				writer.write("<h1>" + pattern + "</h1>");
				writer.newLine();

				path = this.exportToPng(diagram);

				writer.write("<img src=\"" + path + "\">");
				writer.newLine();

			}

			// this is not actually needed for html files
			// -
			// can make your code more readable though

			writer.close(); // make sure you close the writer object
		} catch (IOException ex) {
			MessageUIController.get().showError(ex, "Export - HTML",
					"Could not export current diagram into a HTML page.");
		}
	}

	public void exportToPlantUML() {

		Package container = (Package) (BrowserUIController.get().root()).getUserObject();
		try {
			String plantUMLPath;
			if (lastHtmlPath.equalsIgnoreCase(""))
				plantUMLPath = "plantuml-complete" + ".txt";
			else
				plantUMLPath = lastHtmlPath + '\\' + "plantuml-complete" + ".txt";

			File file = new File(plantUMLPath);
			FileWriter fWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fWriter);

			writer.write("@startuml");

			this.exportAllClassesToPlantUML(container, writer);

			this.identifyRelationships(container.getPackagedElement(), writer);

			writer.newLine();
			writer.write("@enduml");

			// this is not actually needed for html files
			// -
			// can make your code more readable though

			writer.close(); // make sure you close the writer object
		} catch (

		IOException ex) {
			MessageUIController.get().showError(ex, "Export - PlantUML",
					"Could not export current diagram into a PlantUML File.");
		}
	}

	private void exportAllClassesToPlantUML(Package container, BufferedWriter writer) throws IOException {
		for (PackageableElement p : container.getPackagedElement()) {
			if (!(p instanceof Package)) {

				writer.newLine();
				writer.write(this.parseToPlantUML(p));

				try {
					for (Classifier c : ((Classifier) p).parents()) {

						writer.newLine();
						writer.write(this.parseGeneralizationToPlantUML(c, p));

					}
				} catch (ClassCastException ex) {
					System.out
							.println("Export - PlantUML" + "Could not export Generalization Set into a PlantUML File.");
				}

			} else {
				this.exportAllClassesToPlantUML((Package) p, writer);
			}

		}

	}

	public void exportToPlantUML(String padrao, List<Element> occurrencies, int numberOccurrencies) {

		Package container = (Package) (BrowserUIController.get().root()).getUserObject();

		try {
			String plantUMLPath;
			if (lastHtmlPath.equalsIgnoreCase(""))
				plantUMLPath = "plantuml-" + padrao + ".txt";
			else
				plantUMLPath = lastHtmlPath + '\\' + "plantuml-" + padrao + ".txt";

			File file = new File(plantUMLPath);
			FileWriter fWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fWriter);

			writer.write("@startuml");
			writer.newLine();
			writer.write("\'" + numberOccurrencies + " occurrencies");
			writer.newLine();
			writer.write("\'" + occurrencies.size() + " elements");
			writer.newLine();
			writer.write("skinparam class {");
			writer.newLine();

			if ((padrao.replaceAll("Pattern", "").equalsIgnoreCase("SubstanceSortal"))
					|| (padrao.replaceAll("Pattern", "").equalsIgnoreCase("ParthoodStructure"))
					|| (padrao.replaceAll("Pattern", "").equalsIgnoreCase("NonSortal"))) {
				writer.write("BackgroundColor LightGreen");
				writer.newLine();

			} else {
				writer.write("BackgroundColor White");
				writer.newLine();
				writer.write("BackgroundColor<<" + padrao.replaceAll("Pattern", "") + ">> LightGreen");
				writer.newLine();
			}

			writer.write("BorderColor Black");
			writer.newLine();
			writer.write("ArrowColor Black");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("hide circle");
			writer.newLine();
			writer.write("hide empty members  ");
			writer.newLine();

			for (Element o : occurrencies) {
				if (o != null) {
					writer.newLine();
					writer.write(this.parseToPlantUML(o));

					for (Classifier c : ((Classifier) o).parents()) {
						if (occurrencies.contains(c)) {
							writer.newLine();
							writer.write(this.parseGeneralizationToPlantUML(c, o));
						}
					}
				}

				// Pegar a lista de todas as relacoes e filtrar pelos elementos
				// que existem na ocorrencies
				/*
				 * for (Classifier c : ((Classifier) o).getAssociations()) { if
				 * (occurrencies.contains(c)) { writer.newLine();
				 * writer.write(this.parseAssociationToPlantUML(c, o)); } }
				 */

			}

			this.identifyRelationships(container.getPackagedElement(), occurrencies, writer);

			writer.newLine();
			writer.write("@enduml");

			// this is not actually needed for html files
			// -
			// can make your code more readable though

			writer.close(); // make sure you close the writer object
		} catch (

		IOException ex) {
			MessageUIController.get().showError(ex, "Export - PlantUML",
					"Could not export current diagram into a PlantUML File.");
		}
	}

	private void identifyRelationships(EList<PackageableElement> packagedElement, List<Element> occurrencies,
			BufferedWriter writer) throws IOException {
		for (Element r : packagedElement) {
			if (r instanceof Package) {
				this.identifyRelationships(((Package) r).getPackagedElement(), occurrencies, writer);
			}
			if (r instanceof Relationship) {
				{
					if ((((Relationship) r).getRelatedElement().size() == 2) && !(r instanceof Derivation)) {
						if ((occurrencies.contains(((Relationship) r).getRelatedElement().get(0)))
								&& (occurrencies.contains(((Relationship) r).getRelatedElement().get(1)))) {
							writer.newLine();
							writer.write(this.parseAssociationToPlantUML((Relationship) r));
						}
					}
				}
			}

		}

	}

	private void identifyRelationships(EList<PackageableElement> packagedElement, BufferedWriter writer)
			throws IOException {
		for (Element r : packagedElement) {
			if (r instanceof Package) {
				this.identifyRelationships(((Package) r).getPackagedElement(), writer);
			}
			if (r instanceof Relationship) {
				{
					if ((((Relationship) r).getRelatedElement().size() == 2) && !(r instanceof Derivation)) {

						writer.newLine();
						writer.write(this.parseAssociationToPlantUML((Relationship) r));

					}
				}
			}

		}

	}

	private String parseAssociationToPlantUML(Relationship r) {

		String sharable = "";

		if (r instanceof Meronymic) {
			if (((Meronymic) r).isIsShareable())
				sharable = "o";
			else
				sharable = "*";
		}

		String[] parsing_source = ((Relationship) r).getRelatedElement().get(0).toString().split("»");
		String[] parsing_target = ((Relationship) r).getRelatedElement().get(1).toString().split("»");
		String cardinality_source = ((Association) r).getMemberEnd().get(0).getLower() + ".."
				+ ((Association) r).getMemberEnd().get(0).getUpper();
		String cardinality_target = ((Association) r).getMemberEnd().get(1).getLower() + ".."
				+ ((Association) r).getMemberEnd().get(1).getUpper();

		String[] name = ((Relationship) r).toString().split("»");
		String parsed = parsing_source[1].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-",
				"_") + " \"" + cardinality_source.replaceAll("-1", "*") + "\" " + sharable + "-- \""
				+ cardinality_target.replaceAll("-1", "*") + "\" "
				+ parsing_target[1].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-", "_")
				+ ((name[1].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-", "_") == "")
						? ""
						: " : " + name[1].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-",
								"_"));
		return parsed;
	}

	private String parseGeneralizationToPlantUML(Classifier c, Element o) {
		String[] parsing_parent = c.toString().split("»");
		String[] parsing_child = o.toString().split("»");
		String parsed = parsing_parent[1].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-",
				"_") + " <|-- "
				+ parsing_child[1].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-", "_");
		return parsed;
	}

	private String parseToPlantUML(Element o) {
		String parsed = "";

		if (o != null) {
			String[] parsing = o.toString().split("»");

			if (parsing.length == 2)
				parsed = "class " + parsing[1].trim().replaceAll(" ", "_").replaceAll("-", "_") + " <<"
						+ parsing[0].trim().replace("«", "").replace("»", "").replaceAll(" ", "_").replaceAll("-", "_")
						+ ">>";
			else
				System.out.println("Erro");
		}

		return parsed;
	}

}
