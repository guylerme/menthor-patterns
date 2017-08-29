package net.menthor.patternRecognition.relatorPattern;

import java.util.ArrayList;
import java.util.List;

import RefOntoUML.Relator;
import RefOntoUML.Derivation;
import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.Pattern;
import net.menthor.patternRecognition.PatternInfo;

public class RelatorPattern extends Pattern<RelatorOccurrence> {

	public RelatorPattern(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public RelatorPattern(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final PatternInfo info = new PatternInfo("Relator Pattern", "RelatorPattern",
			"This pattern occurs when represents all Relators in the model.", null);

	public static PatternInfo getPatternInfo() {
		return info;
	}

	public PatternInfo info() {
		return info;
	}

	/*
	 * public ArrayList<RelatorOccurrence> identifyAlternative() {
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
	 * RelatorOccurrence(cycle.getNodeIdsOfType(RefOntoUML.Class.class,
	 * false),relCycle,this));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } return occurrence; }
	 */

	@Override
	public ArrayList<RelatorOccurrence> identify() {

		for (Relator r : parser.getAllInstances(Relator.class)) {

			// Aqui já tenho a lista de Relators.
			// Preciso gerar as ocorrências
			// Retornar para a tela de resultados
			// Arrumar um jeito de separar isto em outro diagrama

			try {
				occurrence.add(new RelatorOccurrence(r, r.mediations(), this.getDerivations(r), r.mediated(), this));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

	private List<Derivation> getDerivations(Relator r) {
		List<Derivation> derivationsList = new ArrayList<Derivation>();

		for (Derivation d : parser.getAllInstances(Derivation.class)) {
			if ((d.relator() == r))
				derivationsList.add(d);
		}
		return derivationsList;
	}

}
