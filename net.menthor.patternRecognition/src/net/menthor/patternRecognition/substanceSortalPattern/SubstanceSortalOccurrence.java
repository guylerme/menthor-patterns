package net.menthor.patternRecognition.substanceSortalPattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.SubstanceSortal;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class SubstanceSortalOccurrence extends PatternOccurrence {

	SubstanceSortal substanceSortal;

	public static int OPEN = 0, CLOSED = 1;

	public SubstanceSortalOccurrence(SubstanceSortal substanceSortal, SubstanceSortalPattern ptn) throws Exception {
		super(ptn);
		this.substanceSortal = substanceSortal;

	}

	@Override
	public String toString() {
		String result = "Substance Sortal: " + this.substanceSortal.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(substanceSortal);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(substanceSortal);
	}

	public List<Association> getOnlyAssociations() {
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof SubstanceSortal) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.substanceSortal);
		return elements;
	}
}
