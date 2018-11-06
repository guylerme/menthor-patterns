package net.menthor.abstractionRecognition;

import java.util.ArrayList;

import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;


public abstract class Abstraction<T extends AbstractionOccurrence> {
	
	protected OntoUMLParser parser;
	protected ArrayList<T> occurrence;
	
	/*basic constructors*/
	public Abstraction (OntoUMLParser parser) throws NullPointerException{
		if (parser == null)
			throw new NullPointerException("Abstraction.java: Null OntoUML parser!");
		
		this.parser = parser;
		occurrence = new ArrayList<T>();
	}
	
	public Abstraction (Package pack) throws NullPointerException{
		this(new OntoUMLParser(pack));
	}
	
	public static AbstractionInfo getAbstractionInfo(){
		throw new IllegalStateException("Abstraction info hasn't been set up in the subclass");
	}
	
	public abstract AbstractionInfo info();
	
	public abstract ArrayList<T> identify();
	
	public ArrayList<T> getOccurrences(){
		return occurrence;
	}
	
	public OntoUMLParser getParser(){
		return parser;
	}

	
}
