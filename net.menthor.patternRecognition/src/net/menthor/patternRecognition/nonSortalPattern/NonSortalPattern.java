package net.menthor.patternRecognition.nonSortalPattern;

import java.util.ArrayList;
import java.util.List;

import RefOntoUML.Classifier;
import RefOntoUML.Element;
import RefOntoUML.Generalization;
import RefOntoUML.MixinClass;
import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;
import net.menthor.patternRecognition.Pattern;
import net.menthor.patternRecognition.PatternInfo;

public class NonSortalPattern extends Pattern<NonSortalOccurrence> {

	public NonSortalPattern(OntoUMLParser parser) throws NullPointerException {
		super(parser);
	}

	public NonSortalPattern(Package pack) throws NullPointerException {
		this(new OntoUMLParser(pack));
	}

	private static final PatternInfo info = new PatternInfo("Non Sortal Pattern", "NonSortalPattern",
			"This pattern occurs when represents all Non Sortals in the model.", null);

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
	public ArrayList<NonSortalOccurrence> identify() {

		for (MixinClass m : parser.getAllInstances(MixinClass.class)) {

			// Aqui já tenho a lista de Kinds.
			// Preciso gerar as ocorrências
			// Retornar para a tela de resultados
			// Arrumar um jeito de separar isto em outro diagrama

			try {
				occurrence.add(new NonSortalOccurrence(m, this.getSubtypes(m), this));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return super.getOccurrences();
	}

	private List<Classifier> getSubtypes(MixinClass m) {

		List<Classifier> subtypesList = new ArrayList<Classifier>();

		for (Generalization g : parser.getAllInstances(Generalization.class)) {
			if (g.getGeneral() == m) {
				for (Element e : g.getSource()) {
					subtypesList.add((Classifier) e);
				}
			}
		}

		return subtypesList;
	}

}
