package net.menthor.patternRecognition.kindPattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.Kind;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class KindOccurrence extends PatternOccurrence {

	Kind kind;

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

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.kind);
		return elements;
	}

}
