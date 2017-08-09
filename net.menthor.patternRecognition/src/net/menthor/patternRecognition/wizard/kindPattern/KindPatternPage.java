package net.menthor.patternRecognition.wizard.kindPattern;

import net.menthor.patternRecognition.kindPattern.KindPattern;
import net.menthor.patternRecognition.kindPattern.KindOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class KindPatternPage extends PatternWizardPage<KindOccurrence, KindPatternWizard> {

	protected KindOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public KindPatternPage(KindOccurrence kind) {
		super(kind);
		this.occurrence = kind;
		setTitle(KindPattern.getPatternInfo().getName());
	}

}
