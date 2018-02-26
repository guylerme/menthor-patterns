package net.menthor.patternRecognition.modePattern;

import java.util.ArrayList;

import RefOntoUML.Classifier;
import RefOntoUML.Mode;
import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.Pattern;
import net.menthor.patternRecognition.PatternInfo;

public class ModePattern extends Pattern<ModeOccurrence> {

	public ModePattern(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public ModePattern(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final PatternInfo info = new PatternInfo("Mode Pattern", "ModePattern",
			"This pattern occurs when represents all Modes in the model.", null);

	public static PatternInfo getPatternInfo() {
		return info;
	}

	public PatternInfo info() {
		return info;
	}

	/*
	 * public ArrayList<ModeOccurrence> identifyAlternative() {
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
	 * ModeOccurrence(cycle.getNodeIdsOfType(RefOntoUML.Class.class,
	 * false),relCycle,this));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } return occurrence; }
	 */

	@Override
	public ArrayList<ModeOccurrence> identify() {

		Classifier c;

		for (Mode k : parser.getAllInstances(Mode.class)) {

			// Aqui já tenho a lista de Modes.
			// Preciso gerar as ocorrências
			// Retornar para a tela de resultados
			// Arrumar um jeito de separar isto em outro diagrama
			try {
				c = k.characterized();
			} catch (ClassCastException e) {
				c = null;
			}

			try {
				occurrence.add(new ModeOccurrence(k, c, this));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

}
