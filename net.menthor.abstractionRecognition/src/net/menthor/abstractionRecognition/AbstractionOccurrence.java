
package net.menthor.abstractionRecognition;

import java.util.List;

import RefOntoUML.Element;
import RefOntoUML.parser.OntoUMLParser;

public abstract class AbstractionOccurrence {

	protected Abstraction<?> abstraction;
	protected OntoUMLParser parser;

	public AbstractionOccurrence(Abstraction<?> abstraction) {

		if (abstraction == null)
			throw new NullPointerException("AbstractionOccurrence: abstraction cannot be null.");

		if (abstraction.getParser() == null)
			throw new NullPointerException("AbstractionOccurrence: abstraction cannot be null.");

		this.abstraction = abstraction;
		this.parser = abstraction.getParser();

	}

	public Abstraction<?> getAbstraction() {
		return abstraction;
	}

	/**
	 * Sets the elements to be transformed to alloy on the provided parser
	 * 
	 * @param parser
	 */
	public abstract OntoUMLParser setSelected();

	public abstract String getShortName();

	public OntoUMLParser getParser() {
		return abstraction.getParser();
	}

	public String addQuotes(String name) {
		return "_'" + name + "'";
	}

	public abstract List<Element> getAllElements();
}
