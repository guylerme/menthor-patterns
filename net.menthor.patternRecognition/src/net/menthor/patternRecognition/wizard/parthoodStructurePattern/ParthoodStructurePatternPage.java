package net.menthor.patternRecognition.wizard.parthoodStructurePattern;

import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructurePattern;
import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructureOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class ParthoodStructurePatternPage extends PatternWizardPage<ParthoodStructureOccurrence, ParthoodStructurePatternWizard> {

	protected ParthoodStructureOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public ParthoodStructurePatternPage(ParthoodStructureOccurrence parthoodStructure) {
		super(parthoodStructure);
		this.occurrence = parthoodStructure;
		setTitle(ParthoodStructurePattern.getPatternInfo().getName());
	}

}
