package net.menthor.patternRecognition.wizard.parthoodStructurePattern;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;

import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructureOccurrence;
import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructurePattern;
import net.menthor.patternRecognition.wizard.RefactoringPage;

public class ParthoodStructurePatternRefactoringPage extends RefactoringPage {

	public ParthoodStructureOccurrence skOccur;
	private Label lblCreateOclDerivation;
	private Combo assocCombo;
	private Button btnEnforce;
	private Button btnForbid;
	private Button btnDerive;

	/**
	 * Create the wizard.
	 */
	public ParthoodStructurePatternRefactoringPage(ParthoodStructureOccurrence skOccur) {
		super();
		this.skOccur = skOccur;

		setTitle(ParthoodStructurePattern.getPatternInfo().acronym + " Refactoring Options");
		setDescription("The follwing options can be used to refactor the model.");
	}

	public ParthoodStructurePatternWizard getAssCycWizard() {
		return (ParthoodStructurePatternWizard) getWizard();
	}

	SelectionAdapter enableNextPageListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (btnDerive.getSelection())
				assocCombo.setEnabled(true);
			else
				assocCombo.setEnabled(false);

			if ((btnDerive.getSelection() && assocCombo.getSelectionIndex() != -1) || btnForbid.getSelection()
					|| btnEnforce.getSelection()) {
				if (!isPageComplete())
					setPageComplete(true);
			} else {
				if (isPageComplete())
					setPageComplete(false);
			}
		}
	};

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		lblCreateOclDerivation = new Label(container, SWT.NONE);
		lblCreateOclDerivation.setText("Please, choose the association to be derived:");

		assocCombo = new Combo(container, SWT.READ_ONLY);
		assocCombo.addSelectionListener(enableNextPageListener);

		btnDerive = new Button(container, SWT.RADIO);
		btnDerive.setText("Create OCL derivation rule for one of the associations");
		btnDerive.addSelectionListener(enableNextPageListener);

		btnForbid = new Button(container, SWT.RADIO);
		btnForbid.setText("Create OCL invariant FORBIDDING the instance level cycle");
		btnForbid.addSelectionListener(enableNextPageListener);

		btnEnforce = new Button(container, SWT.RADIO);
		btnEnforce.setText("Create OCL invariant ENFORCING the instance level cycle");
		btnEnforce.addSelectionListener(enableNextPageListener);

		Label lblPleaseChooseWhich = new Label(container, SWT.NONE);
		lblPleaseChooseWhich.setText("Please choose which action to perform on the model:");

		setPageComplete(false);
		assocCombo.setEnabled(false);
		GroupLayout gl_container = new GroupLayout(container);
		gl_container.setHorizontalGroup(gl_container.createParallelGroup(GroupLayout.LEADING).add(gl_container
				.createSequentialGroup().add(10)
				.add(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(lblPleaseChooseWhich, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
						.add(btnEnforce, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
						.add(btnForbid, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
						.add(btnDerive, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
						.add(lblCreateOclDerivation, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
						.add(assocCombo, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
				.add(10)));
		gl_container.setVerticalGroup(gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup().add(10).add(lblPleaseChooseWhich).add(18).add(btnEnforce)
						.add(6).add(btnForbid).add(6).add(btnDerive).add(16)
						.add(lblCreateOclDerivation, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE).add(6)
						.add(assocCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)));
		container.setLayout(gl_container);
	}

	@Override
	public IWizardPage getNextPage() {
		((ParthoodStructurePatternWizard) getWizard()).removeAllActions();

		if (btnEnforce.getSelection()) {
			// Action =============================
			ParthoodStructurePatternAction newAction = new ParthoodStructurePatternAction(skOccur);
			newAction.setCycleMandatory();
			getAssCycWizard().replaceAction(1, newAction);
			// ======================================
		}
		if (btnForbid.getSelection()) {
			// Action =============================
			ParthoodStructurePatternAction newAction = new ParthoodStructurePatternAction(skOccur);
			newAction.setCycleForbidden();
			getAssCycWizard().replaceAction(1, newAction);
			// ======================================
		}

		return ((ParthoodStructurePatternWizard) getWizard()).getFinishing();
	}
}
