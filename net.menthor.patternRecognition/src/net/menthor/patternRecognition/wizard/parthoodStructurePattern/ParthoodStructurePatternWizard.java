package net.menthor.patternRecognition.wizard.parthoodStructurePattern;

import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructurePattern;
import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructureOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class ParthoodStructurePatternWizard extends PatternWizard {

	public ParthoodStructurePatternWizard(ParthoodStructureOccurrence ptrn) {
		super(ptrn, ParthoodStructurePattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(ParthoodStructurePattern.getPatternInfo().name,
				ParthoodStructurePattern.getPatternInfo().acronym, ptrn.toString(), ParthoodStructurePattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public ParthoodStructureOccurrence getPtrn() {
		return (ParthoodStructureOccurrence) ptrn;
	}

}