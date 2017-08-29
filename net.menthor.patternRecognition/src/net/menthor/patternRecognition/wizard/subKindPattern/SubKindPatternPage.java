package net.menthor.patternRecognition.wizard.subKindPattern;

import net.menthor.patternRecognition.subKindPattern.SubKindPattern;
import net.menthor.patternRecognition.subKindPattern.SubKindOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class SubKindPatternPage extends PatternWizardPage<SubKindOccurrence, SubKindPatternWizard> {

	protected SubKindOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public SubKindPatternPage(SubKindOccurrence subKind) {
		super(subKind);
		this.occurrence = subKind;
		setTitle(SubKindPattern.getPatternInfo().getName());
	}

}
