/**
 * 
 */
package net.menthor.editor.v2.ui.patternRecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.draw.LineStyle;
import org.tinyuml.ui.diagram.OntoumlEditor;
import org.tinyuml.umldraw.AssociationElement;
import org.tinyuml.umldraw.AssociationElement.ReadingDesign;
import org.tinyuml.umldraw.ClassElement;
import org.tinyuml.umldraw.MenthorFactory;
import org.tinyuml.umldraw.OccurenceMap;
import org.tinyuml.umldraw.StructureDiagram;
import org.tinyuml.umldraw.shared.UmlConnection;
import org.tinyuml.umldraw.shared.UmlNode;

import RefOntoUML.Association;
import RefOntoUML.Classifier;
import RefOntoUML.Derivation;
import RefOntoUML.Element;
import RefOntoUML.Generalization;
import RefOntoUML.MaterialAssociation;
import RefOntoUML.Package;
import RefOntoUML.Type;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.editor.ui.UmlProject;
import net.menthor.editor.v2.OntoumlDiagram;
import net.menthor.editor.v2.ui.controller.BrowserUIController;
import net.menthor.editor.v2.ui.controller.ExportUIController;
import net.menthor.editor.v2.ui.controller.ProjectUIController;
import net.menthor.editor.v2.ui.controller.SplitPaneUIController;
import net.menthor.editor.v2.ui.controller.TabbedAreaUIController;
import net.menthor.editor.v2.ui.operation.diagram.AddNodeOperation;
import net.menthor.editor.v2.util.Util;
import net.menthor.patternRecognition.PatternOccurrence;

/**
 * @author Guylerme Figueiredo
 *
 */
public class PatternProjectUIController {

	public void createPatternDiagram(List<PatternOccurrence> occurrencies, String padrao) {
		UmlProject project = ProjectUIController.get().getProject();

		Package container = (Package) (BrowserUIController.get().root()).getUserObject();

		StructureDiagram diagram = new StructureDiagram(project);
		diagram.setContainer(container);
		setDefaultDiagramSize(diagram);
		diagram.setLabelText("Diagram" + padrao);
		project.addDiagram(diagram);
		project.saveDiagramNeeded(diagram, false);
		TabbedAreaUIController.get().add(diagram);
		BrowserUIController.get().add(diagram, container);

		List<Element> allOccurrenciesElements = new ArrayList<Element>();

		for (PatternOccurrence o : occurrencies) {
			allOccurrenciesElements.addAll(o.getAllElements());
		}

		this.addElementsToDiagram(diagram, allOccurrenciesElements,
				TabbedAreaUIController.get().getOntoumlEditor(diagram));

	}

	public void createPatternsHTML(HashMap<String, List<PatternOccurrence>> occurrenciesPatterns) {

		UmlProject project = ProjectUIController.get().getProject();
		StructureDiagram diagram;
		Package container;

		List<PatternOccurrence> occurrencies;
		String padrao;

		List<StructureDiagram> diagrams = new ArrayList<StructureDiagram>();

		Iterator<Entry<String, List<PatternOccurrence>>> it = occurrenciesPatterns.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, List<PatternOccurrence>> pair = it.next();
			padrao = (String) pair.getKey();
			occurrencies = (List<PatternOccurrence>) pair.getValue();

			container = (Package) (BrowserUIController.get().root()).getUserObject();

			diagram = new StructureDiagram(project);
			diagram.setContainer(container);
			setDefaultDiagramSize(diagram);
			diagram.setLabelText("HTMLDiagram" + padrao);
			project.addDiagram(diagram);
			project.saveDiagramNeeded(diagram, false);
			TabbedAreaUIController.get().add(diagram);
			BrowserUIController.get().add(diagram, container);

			List<Element> allOccurrenciesElements = new ArrayList<Element>();

			for (PatternOccurrence o : occurrencies) {
				allOccurrenciesElements.addAll(o.getAllElements());
			}

			this.addElementsToDiagram(diagram, allOccurrenciesElements,
					TabbedAreaUIController.get().getOntoumlEditor(diagram));

			diagrams.add(diagram);
		}

		ExportUIController.get().exportToHtml(diagrams);
	}

	public void createPlantUML(HashMap<String, List<PatternOccurrence>> occurrenciesPatterns) {

		List<PatternOccurrence> occurrencies = null;

		List<Element> allOccurrenciesElements;

		for (String chave : occurrenciesPatterns.keySet()) {
			occurrencies = new ArrayList<PatternOccurrence>();
			for (Map.Entry<String, List<PatternOccurrence>> par : occurrenciesPatterns.entrySet()) {
				if (par.getKey() == chave) {
					occurrencies.addAll(par.getValue());
				}
			}

			allOccurrenciesElements = new ArrayList<Element>();
			for (PatternOccurrence o : occurrencies) {
				allOccurrenciesElements.addAll(o.getAllElements());
			}

			this.removeEquals(allOccurrenciesElements);
			this.removeNulls(allOccurrenciesElements);

			ExportUIController.get().exportToPlantUML(chave, allOccurrenciesElements, occurrenciesPatterns.size());
		}

	}

	private void removeEquals(List<Element> lista) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i) != null) {
				// Comparando com os outros valores do vetor
				for (int j = 0; j < lista.size(); j++) {
					if ((i != j) && (lista.get(j) != null) && (lista.get(i).equals(lista.get(j)))) {
						lista.remove(j);
						j--;
					}
				}
			}
		}
	}

	private void removeNulls(List<Element> lista) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i) == null) {
				lista.remove(i);
			}
		}
	}

	private void addElementsToDiagram(StructureDiagram diagram, List<Element> allElements, OntoumlEditor d) {
		for (Element e : allElements) {
			if (diagram.containsChild(e)) {
				/*
				 * if (e instanceof NamedElement) {
				 * MessageUIController.get().showInfo("Move Element", e +
				 * "\" already exists in diagram " + diagram.getName()); } else
				 * if (e instanceof Generalization) {
				 * MessageUIController.get().showInfo("Move Generalization", e +
				 * " already exists in diagram " + diagram.getName()); }
				 * DiagramElement de = OccurenceMap.get().getDiagramElement(e,
				 * diagram); if (de != null)
				 * SelectCommanderMode.get().select(de); return;
				 */
			}
			if ((e instanceof RefOntoUML.Class) || (e instanceof RefOntoUML.DataType)) {
				addClassToDiagram(e, d);
			}
			if ((e instanceof RefOntoUML.Generalization)) {
				addGeneralizationToDiagram(d, (RefOntoUML.Generalization) e, false);
			}
			if ((e instanceof RefOntoUML.Association)) {
				addAssociationToDiagram((RefOntoUML.Association) e, d, false, true, true, true, false,
						ReadingDesign.UNDEFINED);
			}
		}

	}

	private void setDefaultDiagramSize(StructureDiagram diagram) {
		double waste = 0;
		if (SplitPaneUIController.get().isShowProjectBrowser())
			waste += 240;
		if (SplitPaneUIController.get().isShowPalette())
			waste += 240;
		diagram.setSize((Util.getScreenWorkingWidth() - waste + 100) * 3, (Util.getScreenWorkingHeight() - 100) * 3);
	}

	public void addGeneralizationToDiagram(OntoumlEditor d, Generalization gen, boolean isRectilinear) {
		if (d.getDiagram().containsChild(gen.getGeneral()) && d.getDiagram().containsChild(gen.getSpecific())) {
			UmlConnection conn = d.dragRelation(gen, (EObject) d.getDiagram().getContainer());
			if (gen.getGeneralizationSet().size() > 0) {
				Classifier general = gen.getGeneral();
				Classifier specific = gen.getSpecific();
				ClassElement generalElem = (ClassElement) OccurenceMap.get().getDiagramElement(general, d.getDiagram());
				ClassElement specificElem = (ClassElement) OccurenceMap.get().getDiagramElement(specific,
						d.getDiagram());
				if (generalElem.getAbsoluteY1() < specificElem.getAbsoluteY1())
					d.setLineStyle(conn, LineStyle.TREESTYLE_VERTICAL);
				else if (generalElem.getAbsoluteY1() > specificElem.getAbsoluteY1())
					d.setLineStyle(conn, LineStyle.TREESTYLE_VERTICAL);
				else
					d.setLineStyle(conn, LineStyle.TREESTYLE_HORIZONTAL);
			} else if (isRectilinear)
				d.setLineStyle(conn, LineStyle.RECTILINEAR);
			else
				d.setLineStyle(conn, LineStyle.DIRECT);
		}
	}

	/** Move association to a diagram. */
	public void addAssociationToDiagram(Association association, OntoumlEditor d, boolean isRectilinear,
			boolean showName, boolean showOntoUMLStereotype, boolean showMultiplicities, boolean showRoles,
			ReadingDesign direction) {
		Type src = ((Association) association).getMemberEnd().get(0).getType();
		Type tgt = ((Association) association).getMemberEnd().get(1).getType();
		if (d.getDiagram().containsChild(src) && d.getDiagram().containsChild(tgt)) {
			AssociationElement conn = (AssociationElement) d.dragRelation(association,
					(EObject) d.getDiagram().getContainer());
			if (isRectilinear)
				d.setLineStyle(conn, LineStyle.RECTILINEAR);
			else
				d.setLineStyle(conn, LineStyle.DIRECT);
			conn.setShowMultiplicities(showMultiplicities);
			conn.setShowName(showName);
			conn.setShowOntoUmlStereotype(showOntoUMLStereotype);
			conn.setShowRoles(showRoles);
			conn.setReadingDesign(direction);
			if (association instanceof MaterialAssociation) {
				OntoUMLParser refparser = ProjectUIController.get().getProject().getRefParser();
				Derivation deriv = refparser.getDerivation((MaterialAssociation) association);
				if (deriv != null)
					addAssociationToDiagram(deriv, d, false, false, false, true, false, direction);
			}
		}
	}

	/** Move associations of an element to the diagram. */
	public void addAssociationsToDiagram(RefOntoUML.Element element, OntoumlEditor d) {
		OntoUMLParser refparser = ProjectUIController.get().getProject().getRefParser();
		for (RefOntoUML.Association a : refparser.getDirectAssociations((RefOntoUML.Classifier) element)) {
			if (a instanceof MaterialAssociation) {
				Derivation deriv = refparser.getDerivation((MaterialAssociation) a);
				if (deriv != null)
					addAssociationToDiagram(deriv, d, false, false, false, true, false, ReadingDesign.UNDEFINED);
			}
			if (!d.getDiagram().containsChild(a))
				addAssociationToDiagram(a, d, false, true, true, true, false, ReadingDesign.UNDEFINED);
		}
	}

	/** Move generalizations of an element to the diagram. */
	public void addGeneralizationsToDiagram(RefOntoUML.Element element, OntoumlEditor d) {
		OntoUMLParser refparser = ProjectUIController.get().getProject().getRefParser();
		for (RefOntoUML.Generalization gen : refparser.getGeneralizations((RefOntoUML.Classifier) element)) {
			if (!d.getDiagram().containsChild(gen))
				addGeneralizationToDiagram(d, gen, false);
		}
	}

	/** Move class to a diagram */
	public void addClassToDiagram(RefOntoUML.Element element, OntoumlEditor d) {
		UmlNode node = MenthorFactory.get().createNode((RefOntoUML.Type) element,
				(RefOntoUML.Element) element.eContainer());
		// Find Diagram0
		OntoumlDiagram diagram0 = this.getDiagram0();

		double x = 0, y = 0;
		// Get the position of the element
		for (DiagramElement diagramElement : ((AbstractCompositeNode) diagram0).getChildren()) {
			if (diagramElement.toString().equalsIgnoreCase(element.toString())) {
				x = diagramElement.getAbsCenterX();
				y = diagramElement.getAbsCenterY();
			}
		}
		// Add the element to thw new diagram at the same position
		AddNodeOperation cmd = new AddNodeOperation(d, node, x, y);
		cmd.run();
		addGeneralizationsToDiagram(element, d);
		addAssociationsToDiagram(element, d);
	}

	private OntoumlDiagram getDiagram0() {
		for (OntoumlDiagram diagram0 : ProjectUIController.get().getProject().getDiagrams()) {
			if (diagram0.getName().equalsIgnoreCase("Diagram0"))
				return diagram0;
		}

		return null;
	}
}
