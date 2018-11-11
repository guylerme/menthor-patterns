package net.menthor.abstractionRecognition.wizard.nonSortalAbstraction;

import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstractionOccurrence;
import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstraction;
import net.menthor.abstractionRecognition.wizard.FinishingPage;
import net.menthor.abstractionRecognition.wizard.AbstractionWizard;
import net.menthor.abstractionRecognition.wizard.PresentationPage;

public class NonSortalAbstractionWizard extends AbstractionWizard {

	public NonSortalAbstractionWizard(NonSortalAbstractionOccurrence ptrn) {
		super(ptrn, NonSortalAbstraction.getAbstractionInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(NonSortalAbstraction.getAbstractionInfo().name,
				NonSortalAbstraction.getAbstractionInfo().acronym, abs.toString(),
				NonSortalAbstraction.getAbstractionInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public NonSortalAbstractionOccurrence getAbs() {
		return (NonSortalAbstractionOccurrence) abs;
	}

}