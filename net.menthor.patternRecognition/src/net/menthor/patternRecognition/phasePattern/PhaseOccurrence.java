package net.menthor.patternRecognition.phasePattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.ObjectClass;
import RefOntoUML.Phase;
import RefOntoUML.Relationship;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class PhaseOccurrence extends PatternOccurrence {

	Phase phase;
	ArrayList<Relationship> relationshipList;

	public static int OPEN = 0, CLOSED = 1;

	public PhaseOccurrence(Phase phase, List<ObjectClass> generalizations, PhasePattern ptn) throws Exception {
		super(ptn);
		this.phase = phase;

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
		// TODO Implementar metodo para pegar as relacoes
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

}
