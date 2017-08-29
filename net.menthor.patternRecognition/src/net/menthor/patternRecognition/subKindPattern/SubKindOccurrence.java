package net.menthor.patternRecognition.subKindPattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.Relationship;
import RefOntoUML.RigidSortalClass;
import RefOntoUML.SubKind;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class SubKindOccurrence extends PatternOccurrence {

	SubKind subKind;
	ArrayList<Relationship> relationshipList;

	public static int OPEN = 0, CLOSED = 1;

	public SubKindOccurrence(SubKind subKind, List<RigidSortalClass> generalizations, SubKindPattern ptn)
			throws Exception {
		super(ptn);
		this.subKind = subKind;

	}

	@Override
	public String toString() {
		String result = "SubKind: " + this.subKind.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(subKind);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(subKind);
	}

	public List<Association> getOnlyAssociations() {
		// TODO Implementar metodo para pegar as relacoes
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof SubKind) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

}
