package net.menthor.abstractionRecognition.wizard;

import net.menthor.abstractionRecognition.AbstractionOccurrence;

public abstract class AbstractionAction <T extends AbstractionOccurrence> {
	
	protected T ap;
	protected Enum<?> code;
	
	public abstract void run();	

	public AbstractionAction(T ap)
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
