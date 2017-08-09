package net.menthor.patternRecognition.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.wizard.Wizard;

import net.menthor.patternRecognition.PatternOccurrence;

/**
 * @author Guylerme Figueiredo
 *
 */

public abstract class PatternWizard extends Wizard {

	protected boolean canFinish = true;

	protected PatternOccurrence ptrn;
	protected HashMap<Integer, ArrayList<PatternAction<?>>> actions = new HashMap<Integer, ArrayList<PatternAction<?>>>();

	// GUI
	protected PresentationPage presentation;
	protected FinishingPage finishing;
	protected RefactoringPage options;

	public PatternWizard(PatternOccurrence ptrn, String windowTitle) {
		this.ptrn = ptrn;
		canFinish = false;
		setNeedsProgressMonitor(true);
		setWindowTitle(windowTitle);
	}

	public Collection<PatternAction<?>> getAllActions() {

		ArrayList<PatternAction<?>> result = new ArrayList<PatternAction<?>>();

		for (Integer i : actions.keySet()) {
			result.addAll(actions.get(i));
		}

		return result;
	}

	public ArrayList<PatternAction<?>> getAction(int pos) {
		return actions.get(pos);
	}

	public void removeAllActions() {
		actions.clear();
	}

	public void removeAllActions(int pos) {
		actions.remove(pos);
	}

	public void removeAllActions(int pos, Enum<?> e) {

		if (actions.get(pos) != null) {
			Iterator<PatternAction<?>> iterator = actions.get(pos).iterator();
			while (iterator.hasNext()) {
				PatternAction<?> action = iterator.next();
				if (action.getCode().equals(e))
					iterator.remove();
			}
		}
	}

	public void replaceAction(int pos, PatternAction<?> action) {
		ArrayList<PatternAction<?>> indexedActions = new ArrayList<PatternAction<?>>();
		indexedActions.add(action);
		actions.put(pos, indexedActions);
	}

	public void addAction(int pos, PatternAction<?> action) {

		if (actions.get(pos) != null)
			actions.get(pos).add(action);
		else {
			ArrayList<PatternAction<?>> indexedActions = new ArrayList<PatternAction<?>>();
			indexedActions.add(action);
			actions.put(pos, indexedActions);
		}

	}

	@Override
	public boolean canFinish() {
		return canFinish;
	};

	public PatternOccurrence getPtrn() {
		return ptrn;
	}

	public PresentationPage getPresentation() {
		return presentation;
	}

	public FinishingPage getFinishing() {
		canFinish = true;

		// System.out.println("Finishing - Actions size:
		// "+getAllActions().size());
		// for (AntiPatternAction<?> action : getAllActions()) {
		// System.out.println(action);
		// }
		return finishing;
	}

	public RefactoringPage getOptions() {
		return options;
	}

	public void runAllActions() {
		for (PatternAction<?> action : getAllActions()) {
			action.run();
		}
	}

	public HashMap<Integer, ArrayList<PatternAction<?>>> getActions() {
		return actions;
	}

	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	@Override
	public boolean performFinish() {
		for (PatternAction<?> action : this.getAllActions())
			action.run();
		return true;
	}

}
