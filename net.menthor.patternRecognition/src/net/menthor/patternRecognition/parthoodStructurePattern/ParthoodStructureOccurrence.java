package net.menthor.patternRecognition.parthoodStructurePattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Classifier;
import RefOntoUML.Element;
import RefOntoUML.Meronymic;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class ParthoodStructureOccurrence extends PatternOccurrence {

	Meronymic parthoodRelation;
	Classifier whole;
	Classifier part;

	public static int OPEN = 0, CLOSED = 1;

	public ParthoodStructureOccurrence(Meronymic parthoodRelation, Classifier whole, Classifier part,
			ParthoodStructurePattern ptn) throws Exception {
		super(ptn);
		this.parthoodRelation = parthoodRelation;
		this.whole = whole;
		this.part = part;

	}

	@Override
	public String toString() {
		String result = "ParthoodStructure: " + this.parthoodRelation.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(parthoodRelation);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(parthoodRelation);
	}

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.whole);
		elements.add(this.part);
		return elements;
	}
}
