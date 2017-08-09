package net.menthor.patternRecognition.wizard.kindPattern;

import net.menthor.patternRecognition.kindPattern.KindPattern;
import net.menthor.patternRecognition.kindPattern.KindOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class KindPatternWizard extends PatternWizard {

	public KindPatternWizard(KindOccurrence ptrn) {
		super(ptrn, KindPattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(KindPattern.getPatternInfo().name, KindPattern.getPatternInfo().acronym,
				ptrn.toString(), KindPattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public KindOccurrence getPtrn() {
		return (KindOccurrence) ptrn;
	}

}