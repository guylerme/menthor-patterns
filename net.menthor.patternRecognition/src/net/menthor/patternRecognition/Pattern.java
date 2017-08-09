package net.menthor.patternRecognition;

import java.util.ArrayList;

import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;


public abstract class Pattern<T extends PatternOccurrence> {
	
	protected OntoUMLParser parser;
	protected ArrayList<T> occurrence;
	
	/*basic constructors*/
	public Pattern (OntoUMLParser parser) throws NullPointerException{
		if (parser == null)
			throw new NullPointerException("Pattern.java: Null OntoUML parser!");
		
		this.parser = parser;
		occurrence = new ArrayList<T>();
	}
	
	public Pattern (Package pack) throws NullPointerException{
		this(new OntoUMLParser(pack));
	}
	
	public static PatternInfo getPatternInfo(){
		throw new IllegalStateException("Pattern info hasn't been set up in the subclass");
	}
	
	public abstract PatternInfo info();
	
	public abstract ArrayList<T> identify();
	
	public ArrayList<T> getOccurrences(){
		return occurrence;
	}
	
	public OntoUMLParser getParser(){
		return parser;
	}

	
}
