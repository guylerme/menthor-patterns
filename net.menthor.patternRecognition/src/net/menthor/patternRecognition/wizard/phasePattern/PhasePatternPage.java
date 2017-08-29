package net.menthor.patternRecognition.wizard.phasePattern;

import net.menthor.patternRecognition.phasePattern.PhasePattern;
import net.menthor.patternRecognition.phasePattern.PhaseOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class PhasePatternPage extends PatternWizardPage<PhaseOccurrence, PhasePatternWizard> {

	protected PhaseOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public PhasePatternPage(PhaseOccurrence phase) {
		super(phase);
		this.occurrence = phase;
		setTitle(PhasePattern.getPatternInfo().getName());
	}

}
