package net.menthor.patternRecognition.wizard.rolePattern;

import net.menthor.patternRecognition.rolePattern.RolePattern;
import net.menthor.patternRecognition.rolePattern.RoleOccurrence;
import net.menthor.patternRecognition.wizard.PatternWizardPage;

public abstract class RolePatternPage extends PatternWizardPage<RoleOccurrence, RolePatternWizard> {

	protected RoleOccurrence occurrence;

	/**
	 * Create the wizard.
	 */
	public RolePatternPage(RoleOccurrence role) {
		super(role);
		this.occurrence = role;
		setTitle(RolePattern.getPatternInfo().getName());
	}

}
