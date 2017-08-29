package net.menthor.patternRecognition.wizard.nonSortalPattern;

import net.menthor.patternRecognition.nonSortalPattern.NonSortalPattern;
import net.menthor.patternRecognition.nonSortalPattern.NonSortalOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class NonSortalPatternWizard extends PatternWizard {

	public NonSortalPatternWizard(NonSortalOccurrence ptrn) {
		super(ptrn, NonSortalPattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(NonSortalPattern.getPatternInfo().name,
				NonSortalPattern.getPatternInfo().acronym, ptrn.toString(),
				NonSortalPattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public NonSortalOccurrence getPtrn() {
		return (NonSortalOccurrence) ptrn;
	}

}