package net.menthor.patternRecognition.wizard.modePattern;

import net.menthor.patternRecognition.modePattern.ModePattern;
import net.menthor.patternRecognition.modePattern.ModeOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class ModePatternPage extends PatternWizardPage<ModeOccurrence, ModePatternWizard> {

	protected ModeOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public ModePatternPage(ModeOccurrence mode) {
		super(mode);
		this.occurrence = mode;
		setTitle(ModePattern.getPatternInfo().getName());
	}

}
