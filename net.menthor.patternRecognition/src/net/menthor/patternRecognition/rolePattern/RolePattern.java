package net.menthor.patternRecognition.rolePattern;

import java.util.ArrayList;
import java.util.List;

import RefOntoUML.Generalization;
import RefOntoUML.ObjectClass;
import RefOntoUML.Package;
import RefOntoUML.Role;
import RefOntoUML.RigidSortalClass;
import RefOntoUML.SubstanceSortal;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.Pattern;
import net.menthor.patternRecognition.PatternInfo;

public class RolePattern extends Pattern<RoleOccurrence> {

	public RolePattern(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public RolePattern(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final PatternInfo info = new PatternInfo("Role Pattern", "RolePattern",
			"This pattern occurs when represents all Role in the model.", null);

	public static PatternInfo getPatternInfo() {
		return info;
	}

	public PatternInfo info() {
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
	public ArrayList<RoleOccurrence> identify() {

		for (Role k : parser.getAllInstances(Role.class)) {

			// Aqui já tenho a lista de Kinds.
			// Preciso gerar as ocorrências
			// Retornar para a tela de resultados
			// Arrumar um jeito de separar isto em outro diagrama

			try {
				occurrence.add(new RoleOccurrence(k, this.getGeneralizations(k), this));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

	private List<ObjectClass> getGeneralizations(ObjectClass k) {
		List<ObjectClass> generalizations = new ArrayList<ObjectClass>();

		for (Generalization g : k.getGeneralization()) {
			if (g.getGeneral() instanceof SubstanceSortal)
				generalizations.add((RigidSortalClass) g.getGeneral());
			else
				generalizations.addAll(this.getGeneralizations((ObjectClass) g.getGeneral()));
		}
		return generalizations;
	}

}
