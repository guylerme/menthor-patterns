package net.menthor.abstractionRecognition.wizard.relatorAbstraction;

import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstraction;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstractionOccurrence;
import net.menthor.abstractionRecognition.wizard.AbstractionWizardPage;

public abstract class RelatorAbstractionPage extends AbstractionWizardPage<RelatorAbstractionOccurrence, RelatorAbstractionWizard> {

	protected RelatorAbstractionOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public RelatorAbstractionPage(RelatorAbstractionOccurrence kind) {
		super(kind);
		this.occurrence = kind;
		setTitle(RelatorAbstraction.getAbstractionInfo().getName());
	}

}
