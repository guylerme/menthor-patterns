package net.menthor.patternRecognition.rolePattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.Role;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.PatternOccurrence;

/*Kind Pattern*/
public class RoleOccurrence extends PatternOccurrence {

	Role role;
	List<RefOntoUML.Class> generalizations;

	public static int OPEN = 0, CLOSED = 1;

	public RoleOccurrence(Role role, List<RefOntoUML.Class> generalizations, RolePattern ptn) throws Exception {
		super(ptn);
		this.role = role;
		this.generalizations = generalizations;
	}

	@Override
	public String toString() {
		String result = "Role: " + this.role.getName();

		return result;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();

		selection.add(role);

		parser.select(selection, true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);

		return parser;
	}

	@Override
	public String getShortName() {

		return parser.getStringRepresentation(role);
	}

	public List<Association> getOnlyAssociations() {
		List<Association> associations = new ArrayList<Association>();
		for (Association a : parser.getAllInstances(Association.class)) {

			for (Element e : a.getRelatedElement()) {
				if (e instanceof Role) {
					associations.add(a);

				}
			}

		}

		return associations;
	}

	@Override
	public List<Element> getAllElements() {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.role);
		for (Element e : generalizations)
			elements.add(e);
		return elements;
	}
}
