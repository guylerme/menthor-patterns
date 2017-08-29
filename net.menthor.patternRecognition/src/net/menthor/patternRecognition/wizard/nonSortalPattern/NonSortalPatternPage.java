package net.menthor.patternRecognition.wizard.nonSortalPattern;

import net.menthor.patternRecognition.nonSortalPattern.NonSortalPattern;
import net.menthor.patternRecognition.nonSortalPattern.NonSortalOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class NonSortalPatternPage
		extends PatternWizardPage<NonSortalOccurrence, NonSortalPatternWizard> {

	protected NonSortalOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public NonSortalPatternPage(NonSortalOccurrence nonSortal) {
		super(nonSortal);
		this.occurrence = nonSortal;
		setTitle(NonSortalPattern.getPatternInfo().getName());
	}

}
