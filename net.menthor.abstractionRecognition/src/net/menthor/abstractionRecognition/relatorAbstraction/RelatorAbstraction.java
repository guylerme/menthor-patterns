package net.menthor.abstractionRecognition.relatorAbstraction;

import java.util.ArrayList;

import RefOntoUML.Package;
import RefOntoUML.Relator;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.abstractionRecognition.Abstraction;
import net.menthor.abstractionRecognition.AbstractionInfo;

public class RelatorAbstraction extends Abstraction<RelatorAbstractionOccurrence> {

	public RelatorAbstraction(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public RelatorAbstraction(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final AbstractionInfo info = new AbstractionInfo("Relator Abstraction", "RelatorAbstraction",
			"This view abstracts all relators in the model.", null);

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
	public ArrayList<RelatorAbstractionOccurrence> identify() {

		for (Relator r : parser.getAllInstances(Relator.class)) {

			// Aqui já tenho a lista de Kinds.
			// Preciso gerar as ocorrências
			// Retornar para a tela de resultados
			// Arrumar um jeito de separar isto em outro diagrama

			try {
				occurrence.add(new RelatorAbstractionOccurrence(r, this));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

}
