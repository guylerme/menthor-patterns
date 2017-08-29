package net.menthor.patternRecognition.wizard.phasePattern;

import net.menthor.patternRecognition.phasePattern.PhasePattern;
import net.menthor.patternRecognition.phasePattern.PhaseOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class PhasePatternWizard extends PatternWizard {

	public PhasePatternWizard(PhaseOccurrence ptrn) {
		super(ptrn, PhasePattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(PhasePattern.getPatternInfo().name, PhasePattern.getPatternInfo().acronym,
				ptrn.toString(), PhasePattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public PhaseOccurrence getPtrn() {
		return (PhaseOccurrence) ptrn;
	}

}