
package net.menthor.patternRecognition;

import net.menthor.common.ontoumlfixer.Fix;
import net.menthor.common.ontoumlfixer.OutcomeFixer;
import RefOntoUML.parser.OntoUMLParser;

public abstract class PatternOccurrence {
	
	protected Pattern<?> pattern;
	protected OntoUMLParser parser; 
	

	
	public PatternOccurrence(Pattern<?> pattern){
		
		if (pattern==null)
			throw new NullPointerException("PatternOccurrence: pattern cannot be null.");
		
		if (pattern.getParser()==null)
			throw new NullPointerException("PatternOccurrence: parser cannot be null.");
		
		this.pattern = pattern;
		this.parser = pattern.getParser();
	
	}
	
	
	public Pattern<?> getPattern() {
		return pattern;
	}

	
	
	/**
	 * Sets the elements to be transformed to alloy on the provided parser
	 * 
	 * @param parser
	 */
	public abstract OntoUMLParser setSelected();
	
	public abstract String getShortName();
	
	public OntoUMLParser getParser(){
		return pattern.getParser();
	}
	
	public String addQuotes (String name){
		return "_'"+name+"'";
	}
}
