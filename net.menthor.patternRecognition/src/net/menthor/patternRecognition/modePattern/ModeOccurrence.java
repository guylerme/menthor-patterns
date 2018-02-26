package net.menthor.patternRecognition.modePattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Classifier;
import RefOntoUML.Element;
import RefOntoUML.Mode;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Mode Pattern*/
public class ModeOccurrence extends PatternOccurrence {

	Mode mode;
	Classifier characterized;

	public static int OPEN = 0, CLOSED = 1;

	public ModeOccurrence(Mode mode, Classifier characterized, ModePattern ptn) throws Exception {
		super(ptn);
		this.mode = mode;
		this.characterized = characterized;

	}

	public ModeOccurrence(Mode mode, ModePattern ptn) throws Exception {
		super(ptn);
		this.mode = mode;

	}

	@Override
	public String toString() {
		String result = "Mode: " + this.mode.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(mode);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(mode);
	}

	public List<Association> getOnlyAssociations() {
		// TODO Implementar metodo para pegar as relacoes
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof Mode) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.mode);
		elements.add(characterized);
		return elements;
	}

}
