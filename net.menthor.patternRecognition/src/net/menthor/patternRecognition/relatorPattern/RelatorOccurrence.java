package net.menthor.patternRecognition.relatorPattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Classifier;
import RefOntoUML.Derivation;
import RefOntoUML.Element;
import RefOntoUML.Mediation;
import RefOntoUML.Relator;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Relator Pattern*/
public class RelatorOccurrence extends PatternOccurrence {

	Relator relator;
	List<Mediation> mediationsList;
	List<Derivation> derivationsList;
	List<Classifier> mediatedList;

	public static int OPEN = 0, CLOSED = 1;

	public RelatorOccurrence(Relator relator, List<Mediation> mediationsList, List<Derivation> derivationsList,
			List<Classifier> mediatedList, RelatorPattern ptn) throws Exception {
		super(ptn);
		this.relator = relator;
		this.mediatedList = mediatedList;
		this.derivationsList = derivationsList;
		this.mediationsList = mediationsList;

	}

	@Override
	public String toString() {
		String result = "Relator: " + this.relator.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(relator);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(relator);
	}

	public List<Association> getOnlyAssociations() {
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof Relator) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.relator);
		for (Element e : mediatedList)
			elements.add(e);
		return elements;
	}
}
