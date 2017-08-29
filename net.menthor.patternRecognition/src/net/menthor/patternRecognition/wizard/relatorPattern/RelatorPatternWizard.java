package net.menthor.patternRecognition.wizard.relatorPattern;

import net.menthor.patternRecognition.relatorPattern.RelatorPattern;
import net.menthor.patternRecognition.relatorPattern.RelatorOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class RelatorPatternWizard extends PatternWizard {

	public RelatorPatternWizard(RelatorOccurrence ptrn) {
		super(ptrn, RelatorPattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(RelatorPattern.getPatternInfo().name, RelatorPattern.getPatternInfo().acronym,
				ptrn.toString(), RelatorPattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public RelatorOccurrence getPtrn() {
		return (RelatorOccurrence) ptrn;
	}

}