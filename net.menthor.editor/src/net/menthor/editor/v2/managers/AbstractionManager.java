package net.menthor.editor.v2.managers;

import net.menthor.common.ontoumlfixer.Fix;
import net.menthor.editor.v2.OclDocument;
import net.menthor.editor.v2.commanders.UpdateCommander;
import net.menthor.editor.v2.ui.abstractionRecognition.AbstractionSearchDialog;
import net.menthor.editor.v2.ui.controller.ProjectUIController;
import net.menthor.editor.v2.ui.controller.TabbedAreaUIController;

public class AbstractionManager extends AbstractManager {

	// -------- Lazy Initialization

	private static class PatternLoader {
		private static final AbstractionManager INSTANCE = new AbstractionManager();
	}

	public static AbstractionManager get() {
		return PatternLoader.INSTANCE;
	}

	private AbstractionManager() {
		if (PatternLoader.INSTANCE != null)
			throw new IllegalStateException("AbstractionManager already instantiated");
	}

	// ----------------------------

	public void detectAbstractions() {
		AbstractionSearchDialog.open(frame(), ProjectUIController.get().getProject().getRefParser());
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
