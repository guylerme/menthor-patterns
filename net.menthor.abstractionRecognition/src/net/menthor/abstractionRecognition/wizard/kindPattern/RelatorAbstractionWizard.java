package net.menthor.abstractionRecognition.wizard.kindPattern;

import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstractionOccurrence;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstraction;
import net.menthor.abstractionRecognition.wizard.FinishingPage;
import net.menthor.abstractionRecognition.wizard.AbstractionWizard;
import net.menthor.abstractionRecognition.wizard.PresentationPage;

public class RelatorAbstractionWizard extends AbstractionWizard {

	public RelatorAbstractionWizard(RelatorAbstractionOccurrence ptrn) {
		super(ptrn, RelatorAbstraction.getAbstractionInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(RelatorAbstraction.getAbstractionInfo().name,
				RelatorAbstraction.getAbstractionInfo().acronym, abs.toString(),
				RelatorAbstraction.getAbstractionInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public RelatorAbstractionOccurrence getAbs() {
		return (RelatorAbstractionOccurrence) abs;
	}

}