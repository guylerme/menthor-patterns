package net.menthor.patternRecognition.wizard.modePattern;

import net.menthor.patternRecognition.modePattern.ModePattern;
import net.menthor.patternRecognition.modePattern.ModeOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class ModePatternWizard extends PatternWizard {

	public ModePatternWizard(ModeOccurrence ptrn) {
		super(ptrn, ModePattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(ModePattern.getPatternInfo().name, ModePattern.getPatternInfo().acronym,
				ptrn.toString(), ModePattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public ModeOccurrence getPtrn() {
		return (ModeOccurrence) ptrn;
	}

}