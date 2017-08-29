package net.menthor.patternRecognition.wizard.substanceSortalPattern;

import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalPattern;
import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class SubstanceSortalPatternPage
		extends PatternWizardPage<SubstanceSortalOccurrence, SubstanceSortalPatternWizard> {

	protected SubstanceSortalOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public SubstanceSortalPatternPage(SubstanceSortalOccurrence substanceSortal) {
		super(substanceSortal);
		this.occurrence = substanceSortal;
		setTitle(SubstanceSortalPattern.getPatternInfo().getName());
	}

}
