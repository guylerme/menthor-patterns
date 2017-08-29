package net.menthor.patternRecognition.wizard.subKindPattern;

import net.menthor.patternRecognition.subKindPattern.SubKindPattern;
import net.menthor.patternRecognition.subKindPattern.SubKindOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class SubKindPatternWizard extends PatternWizard {

	public SubKindPatternWizard(SubKindOccurrence ptrn) {
		super(ptrn, SubKindPattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(SubKindPattern.getPatternInfo().name,
				SubKindPattern.getPatternInfo().acronym, ptrn.toString(), SubKindPattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public SubKindOccurrence getPtrn() {
		return (SubKindOccurrence) ptrn;
	}

}