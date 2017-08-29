package net.menthor.editor.v2.managers;

import net.menthor.common.ontoumlfixer.Fix;
import net.menthor.editor.v2.OclDocument;
import net.menthor.editor.v2.commanders.UpdateCommander;
import net.menthor.editor.v2.ui.controller.ProjectUIController;
import net.menthor.editor.v2.ui.controller.TabbedAreaUIController;
import net.menthor.editor.v2.ui.patternRecognition.PatternSearchDialog;

public class PatternManager extends AbstractManager {

	// -------- Lazy Initialization

	private static class PatternLoader {
		private static final PatternManager INSTANCE = new PatternManager();
	}

	public static PatternManager get() {
		return PatternLoader.INSTANCE;
	}

	private PatternManager() {
		if (PatternLoader.INSTANCE != null)
			throw new IllegalStateException("PatternManager already instantiated");
	}

	// ----------------------------

	public void detectPatterns() {
		PatternSearchDialog.open(frame(), ProjectUIController.get().getProject().getRefParser());
	}

	/**
	 * Transfer fixes made on the model to an application. Users must override
	 * this method to get the modifications made by the patterns
	 */
	public void transferFix(Fix fix) {
		UpdateCommander.get().update(fix);

		// if there are rules, the update action opens a tab to show the ocl
		// document to the user;
		if (fix.getAddedRules().size() > 0) {
			OclDocument oclDoc = ProjectUIController.get().getProject().getOclDocList().get(0);

			if (TabbedAreaUIController.get().isOpen(oclDoc))
				TabbedAreaUIController.get().select(oclDoc);
			else
				TabbedAreaUIController.get().add(oclDoc);

			TabbedAreaUIController.get().getSelectedTopOclEditor().reloadText();
		}
	}

}
