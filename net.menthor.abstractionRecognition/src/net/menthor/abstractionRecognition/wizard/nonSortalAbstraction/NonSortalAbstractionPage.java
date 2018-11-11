package net.menthor.abstractionRecognition.wizard.nonSortalAbstraction;

import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstraction;
import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstractionOccurrence;
import net.menthor.abstractionRecognition.wizard.AbstractionWizardPage;

public abstract class NonSortalAbstractionPage extends AbstractionWizardPage<NonSortalAbstractionOccurrence, NonSortalAbstractionWizard> {

	protected NonSortalAbstractionOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public NonSortalAbstractionPage(NonSortalAbstractionOccurrence kind) {
		super(kind);
		this.occurrence = kind;
		setTitle(NonSortalAbstraction.getAbstractionInfo().getName());
	}

}
