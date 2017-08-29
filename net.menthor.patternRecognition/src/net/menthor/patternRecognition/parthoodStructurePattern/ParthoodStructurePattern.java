package net.menthor.patternRecognition.parthoodStructurePattern;

import java.util.ArrayList;

import RefOntoUML.Meronymic;
import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.Pattern;
import net.menthor.patternRecognition.PatternInfo;

public class ParthoodStructurePattern extends Pattern<ParthoodStructureOccurrence> {

	public ParthoodStructurePattern(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public ParthoodStructurePattern(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final PatternInfo info = new PatternInfo("ParthoodStructure Pattern", "ParthoodStructurePattern",
			"This pattern occurs when represents all Kinds in the model.", null);

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
	public ArrayList<ParthoodStructureOccurrence> identify() {

		for (Meronymic m : parser.getAllInstances(Meronymic.class)) {

			// Aqui já tenho a lista de Kinds.
			// Preciso gerar as ocorrências
			// Retornar para a tela de resultados
			// Arrumar um jeito de separar isto em outro diagrama

			try {
				occurrence.add(new ParthoodStructureOccurrence(m, m.whole(), m.part(), this));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

}
