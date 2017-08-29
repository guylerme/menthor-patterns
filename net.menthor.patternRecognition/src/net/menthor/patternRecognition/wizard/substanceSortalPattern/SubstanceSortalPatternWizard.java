package net.menthor.patternRecognition.wizard.substanceSortalPattern;

import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalPattern;
import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class SubstanceSortalPatternWizard extends PatternWizard {

	public SubstanceSortalPatternWizard(SubstanceSortalOccurrence ptrn) {
		super(ptrn, SubstanceSortalPattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(SubstanceSortalPattern.getPatternInfo().name,
				SubstanceSortalPattern.getPatternInfo().acronym, ptrn.toString(),
				SubstanceSortalPattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public SubstanceSortalOccurrence getPtrn() {
		return (SubstanceSortalOccurrence) ptrn;
	}

}