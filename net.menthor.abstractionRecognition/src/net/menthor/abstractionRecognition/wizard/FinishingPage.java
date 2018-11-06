package net.menthor.abstractionRecognition.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

/**
 * @author Guylerme Figueiredo
 *
 */

public class FinishingPage extends WizardPage {

	public Label rulesLabel;

	/**
	 * Create the wizard.
	 */
	public FinishingPage() {
		super("Finishing Page");
		setTitle("Finished.");
		setDescription("");
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

	}

	public void hideActionList() {

		rulesLabel.setText("Your model was already correct.");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Label introLabel = new Label(container, SWT.NONE);
		introLabel.setText("Congratulations! You successfully created a new model.");

		GroupLayout gl_container = new GroupLayout(container);
		gl_container.setHorizontalGroup(gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup().add(gl_container.createParallelGroup(GroupLayout.LEADING)

						.add(gl_container.createSequentialGroup().addContainerGap())
						.add(gl_container.createSequentialGroup().add(10).add(introLabel, GroupLayout.DEFAULT_SIZE, 564,
								Short.MAX_VALUE)))
						.addContainerGap()));
		gl_container.setVerticalGroup(
				gl_container.createParallelGroup(GroupLayout.LEADING).add(gl_container.createSequentialGroup().add(10)
						.add(introLabel).addPreferredGap(LayoutStyle.RELATED).addPreferredGap(LayoutStyle.RELATED)));
		container.setLayout(gl_container);
	}
}
