package net.menthor.patternRecognition.nonSortalPattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Classifier;
import RefOntoUML.MixinClass;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class NonSortalOccurrence extends PatternOccurrence {

	MixinClass nonSortal;
	List<Classifier> subTypes;

	public static int OPEN = 0, CLOSED = 1;

	public NonSortalOccurrence(MixinClass nonSortal, List<Classifier> subTypes, NonSortalPattern ptn) throws Exception {
		super(ptn);
		this.nonSortal = nonSortal;
		this.subTypes = subTypes;

	}

	@Override
	public String toString() {
		String result = "Non Sortal: " + this.nonSortal.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(nonSortal);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(nonSortal);
	}

}
