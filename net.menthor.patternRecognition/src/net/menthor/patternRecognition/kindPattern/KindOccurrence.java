package net.menthor.patternRecognition.kindPattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.menthor.patternRecognition.PatternOccurrence;
import net.menthor.patternRecognition.util.AlloyConstructor;
import net.menthor.patternRecognition.util.SourceTargetAssociation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Class;
import RefOntoUML.Classifier;
import RefOntoUML.Element;
import RefOntoUML.Generalization;
import RefOntoUML.Kind;
import RefOntoUML.Property;
import RefOntoUML.Relationship;
import RefOntoUML.Type;
import RefOntoUML.parser.OntoUMLNameHelper;
import RefOntoUML.parser.OntoUMLParser;

/*Kind Pattern*/
public class KindOccurrence extends PatternOccurrence {

	Kind kind;
	ArrayList<Relationship> relationshipList;

	public static int OPEN = 0, CLOSED = 1;

	public KindOccurrence(Kind kind, KindPattern ptn) throws Exception {
		super(ptn);
		this.kind = kind;

	}

	@Override
	public String toString() {
		String result = "Kind: " + this.kind.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(kind);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(kind);
	}

	public List<Association> getOnlyAssociations() {
		// TODO Implementar metodo para pegar as relacoes
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof Kind) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

}
