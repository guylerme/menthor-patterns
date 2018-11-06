package net.menthor.abstractionRecognition.relatorAbstraction;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.Relator;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.abstractionRecognition.AbstractionOccurrence;

/*Kind Pattern*/
public class RelatorAbstractionOccurrence extends AbstractionOccurrence {

	Relator relator;

	public static int OPEN = 0, CLOSED = 1;

	public RelatorAbstractionOccurrence(Relator relator, RelatorAbstraction abs) throws Exception {
		super(abs);
		this.relator = relator;

	}

	@Override
	public String toString() {
		String result = "Relator Abstraction: " + this.relator.getName();

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
		return elements;
	}

}
