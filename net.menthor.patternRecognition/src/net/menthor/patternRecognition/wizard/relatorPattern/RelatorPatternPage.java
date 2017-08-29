package net.menthor.patternRecognition.wizard.relatorPattern;

import net.menthor.patternRecognition.relatorPattern.RelatorPattern;
import net.menthor.patternRecognition.relatorPattern.RelatorOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class RelatorPatternPage extends PatternWizardPage<RelatorOccurrence, RelatorPatternWizard> {

	protected RelatorOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public RelatorPatternPage(RelatorOccurrence relator) {
		super(relator);
		this.occurrence = relator;
		setTitle(RelatorPattern.getPatternInfo().getName());
	}

}
