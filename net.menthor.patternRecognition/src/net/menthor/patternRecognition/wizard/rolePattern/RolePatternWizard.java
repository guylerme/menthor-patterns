package net.menthor.patternRecognition.wizard.rolePattern;

import net.menthor.patternRecognition.rolePattern.RolePattern;
import net.menthor.patternRecognition.rolePattern.RoleOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizard;
import net.menthor.patternRecognition.wizard.FinishingPage;
import net.menthor.patternRecognition.wizard.PresentationPage;

public class RolePatternWizard extends PatternWizard {

	public RolePatternWizard(RoleOccurrence ptrn) {
		super(ptrn, RolePattern.getPatternInfo().name);
	}

	@Override
	public void addPages() {
		finishing = new FinishingPage();

		presentation = new PresentationPage(RolePattern.getPatternInfo().name, RolePattern.getPatternInfo().acronym,
				ptrn.toString(), RolePattern.getPatternInfo().description);

		addPage(presentation);
		addPage(finishing);
	}

	public RoleOccurrence getPtrn() {
		return (RoleOccurrence) ptrn;
	}

}