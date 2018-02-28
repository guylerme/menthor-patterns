package net.menthor.patternRecognition.phasePattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.Phase;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class PhaseOccurrence extends PatternOccurrence {

	Phase phase;
	List<RefOntoUML.Class> generalizations;

	public static int OPEN = 0, CLOSED = 1;

	public PhaseOccurrence(Phase phase, List<RefOntoUML.Class> generalizations, PhasePattern ptn) throws Exception {
		super(ptn);
		this.phase = phase;
		this.generalizations = generalizations;
	}

	@Override
	public String toString() {
		String result = "Phase: " + this.phase.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(phase);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(phase);
	}

	public List<Association> getOnlyAssociations() {
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof Phase) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.phase);
		for (Element e : generalizations)
			elements.add(e);
		return elements;
	}
}
