package net.menthor.abstractionRecognition.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.wizard.Wizard;

import net.menthor.abstractionRecognition.AbstractionOccurrence;

/**
 * @author Guylerme Figueiredo
 *
 */

public abstract class AbstractionWizard extends Wizard {

	protected boolean canFinish = true;

	protected AbstractionOccurrence abs;
	protected HashMap<Integer, ArrayList<AbstractionAction<?>>> actions = new HashMap<Integer, ArrayList<AbstractionAction<?>>>();

	// GUI
	protected PresentationPage presentation;
	protected FinishingPage finishing;
	protected RefactoringPage options;

	public AbstractionWizard(AbstractionOccurrence abs, String windowTitle) {
		this.abs = abs;
		canFinish = false;
		setNeedsProgressMonitor(true);
		setWindowTitle(windowTitle);
	}

	public Collection<AbstractionAction<?>> getAllActions() {

		ArrayList<AbstractionAction<?>> result = new ArrayList<AbstractionAction<?>>();

		for (Integer i : actions.keySet()) {
			result.addAll(actions.get(i));
		}

		return result;
	}

	public ArrayList<AbstractionAction<?>> getAction(int pos) {
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
			Iterator<AbstractionAction<?>> iterator = actions.get(pos).iterator();
			while (iterator.hasNext()) {
				AbstractionAction<?> action = iterator.next();
				if (action.getCode().equals(e))
					iterator.remove();
			}
		}
	}

	public void replaceAction(int pos, AbstractionAction<?> action) {
		ArrayList<AbstractionAction<?>> indexedActions = new ArrayList<AbstractionAction<?>>();
		indexedActions.add(action);
		actions.put(pos, indexedActions);
	}

	public void addAction(int pos, AbstractionAction<?> action) {

		if (actions.get(pos) != null)
			actions.get(pos).add(action);
		else {
			ArrayList<AbstractionAction<?>> indexedActions = new ArrayList<AbstractionAction<?>>();
			indexedActions.add(action);
			actions.put(pos, indexedActions);
		}

	}

	@Override
	public boolean canFinish() {
		return canFinish;
	};

	public AbstractionOccurrence getAbs() {
		return abs;
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
		for (AbstractionAction<?> action : getAllActions()) {
			action.run();
		}
	}

	public HashMap<Integer, ArrayList<AbstractionAction<?>>> getActions() {
		return actions;
	}

	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	@Override
	public boolean performFinish() {
		for (AbstractionAction<?> action : this.getAllActions())
			action.run();
		return true;
	}

}
