package net.menthor.patternRecognition.wizard;

import net.menthor.patternRecognition.PatternOccurrence;

public abstract class PatternAction <T extends PatternOccurrence> {
	
	protected T ap;
	protected Enum<?> code;
	
	public abstract void run();	

	public PatternAction(T ap)
	{
		this.ap = ap;
	}
	
	public T getAp()
	{
		return ap;
	}

	public Enum<?> getCode(){
		return code;
	}
}
