package net.menthor.abstractionRecognition.nonSortalAbstraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.Generalization;
import RefOntoUML.MixinClass;
import RefOntoUML.NamedElement;
import RefOntoUML.Package;
import RefOntoUML.Property;
import RefOntoUML.Relationship;
import RefOntoUML.Relator;
import RefOntoUML.Type;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.abstractionRecognition.Abstraction;
import net.menthor.abstractionRecognition.AbstractionInfo;

public class NonSortalAbstraction extends Abstraction<NonSortalAbstractionOccurrence> {

	public NonSortalAbstraction(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public NonSortalAbstraction(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final AbstractionInfo info = new AbstractionInfo("Non-Sortal Abstraction", "NonSortalAbstraction",
			"This view abstracts all non-sortals in the model.", null);

	public static AbstractionInfo getAbstractionInfo() {
		return info;
	}

	public AbstractionInfo info() {
		return info;
	}

	/*
	 * public ArrayList<KindOccurrence> identifyAlternative() {
	 * 
	 * Graph genGraph = new Graph(parser); //creates directed graph with classes
	 * and meronymics genGraph.createBidirectionalRelationshipGraph(true,true);
	 * //get all paths in the graph ArrayList<EdgePath> allPaths =
	 * genGraph.getAllEdgePathsFromAllNodes(3); //only keep paths that are
	 * cycles Graph.retainCycles(allPaths); //remove cycles which contain the
	 * very same edges (ignoring the order of the edges)
	 * Graph.removeDuplicateEdgeCycles(allPaths, true);
	 * 
	 * //required for method isRelatorPattern to work createDerivationHash();
	 * 
	 * for (EdgePath cycle : allPaths) { try { ArrayList<Relationship> relCycle
	 * = cycle.getEdgeIdsOfType(Relationship.class);
	 * 
	 * if(relCycle.size()<=2) continue;
	 * 
	 * if(isRelatorPattern(relCycle)) continue;
	 * 
	 * occurrence.add(new
	 * KindOccurrence(cycle.getNodeIdsOfType(RefOntoUML.Class.class,
	 * false),relCycle,this));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } return occurrence; }
	 */

	@Override
	public ArrayList<NonSortalAbstractionOccurrence> identify() {

		Set<EObject> lista = parser.getAllInstancesExcept(MixinClass.class);
		// System.out.println("Teste");

		for (EObject elem : lista) {
			/*
			 * if ((elem instanceof Relationship) && (!(elem instanceof
			 * Generalization))) {
			 * 
			 * for (Element el : ((Relationship) elem).getRelatedElement()) { //
			 * System.out.println("Teste"); if (el instanceof MixinClass) { for
			 * (Relationship rel : el.getRelationships()){ if (rel instanceof
			 * Generalization){
			 * 
			 * } } } }
			 * 
			 * }
			 */

			try {
				occurrence.add(new NonSortalAbstractionOccurrence(elem, this));
				this.moveRelationships(occurrence);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

	private void moveRelationships(ArrayList<NonSortalAbstractionOccurrence> occurrence) throws Exception {
		Set<MixinClass> nonSortals = this.identifyNonSortalsToBeMoved(occurrence);
		Association clonedAssociation;

		for (MixinClass nonSortal : nonSortals) {
			// Obter as relacoes que faz parte
			for (Association a : this.getAssociations(nonSortal)) {

				// Clonar a relacao para cada filho
				for (Element e : this.getChildren(nonSortal)) {
					clonedAssociation = EcoreUtil.copy(a);

					for (int i = 0; i < clonedAssociation.getMemberEnd().size(); i++) {
						if (clonedAssociation.getMemberEnd().get(i).getType().equals((Type) nonSortal)) {
							// Atribuir o rolename da relacao como o nome do
							// NonSortal
							clonedAssociation.getMemberEnd().get(i).setName(nonSortal.getName());

							// Incluir o filho na relacao
							// Remover o NonSortal da Relacao
							clonedAssociation.getMemberEnd().get(i).setType((Type) e);
						}
					}
					occurrence.add(new NonSortalAbstractionOccurrence(clonedAssociation, this));
				}
			}
		}
	}

	private Set<Element> getChildren(MixinClass n) {
		Set<Generalization> generalizations = parser.getAllInstances(Generalization.class);
		Set<Element> children = new HashSet<Element>();

		for (Generalization g : generalizations) {
			for (Element e : g.getTarget()) {
				if (e instanceof MixinClass) {
					for (Element e2 : g.getSource()) {
						children.add(e2);
					}

				}
			}
		}

		return children;
	}

	private Set<Association> getAssociations(MixinClass n) {
		Set<Association> associations = parser.getAllInstances(Association.class);

		boolean associated = false;

		for (Association r : associations) {
			associated = false;
			for (Element e : r.getRelatedElement()) {
				if (e.equals(n)) {
					associated = true;
				}
			}
			if (!associated)
				associations.remove(r);
		}
		return associations;
	}

	private Set<MixinClass> identifyNonSortalsToBeMoved(ArrayList<NonSortalAbstractionOccurrence> occurrence) {

		Set<MixinClass> parentNonSortals = new HashSet<MixinClass>();
		Set<MixinClass> relatedNonSortals = new HashSet<MixinClass>();
		Set<MixinClass> parentRelatedNonSortals = new HashSet<MixinClass>();
		// = parser.getAllInstances(MixinClass.class);
		Set<Generalization> generalizations = parser.getAllInstances(Generalization.class);

		Set<Association> associations = parser.getAllInstances(Association.class);

		// Este for me retorna todos os NonSortals que contém filhos em uma
		// hierarquia
		for (Generalization g : generalizations) {
			for (Element e : g.getTarget()) {
				if (e instanceof MixinClass) {
					parentNonSortals.add((MixinClass) e);
				}
			}
		}

		// Este for me retorna todos os NonSortals que estao em alguma relacao
		// que nao seja especializacao
		for (Association r : associations) {

			for (Element e : r.getRelatedElement()) {
				if (e instanceof MixinClass) {
					relatedNonSortals.add((MixinClass) e);
				}
			}

		}

		boolean intersection = false;
		// Intercessao dos NonSortals que sao pais e estao em uma relacao
		for (MixinClass m : parentNonSortals) {
			intersection = false;
			for (MixinClass rel : relatedNonSortals) {
				if (m.equals(rel))
					intersection = true;
			}

			if (intersection)
				parentRelatedNonSortals.add((MixinClass) m);
		}
		return parentRelatedNonSortals;
	}

}
