package net.menthor.abstractionRecognition.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

/**
 * @author Guylerme Figueiredo
 *
 */

public class PresentationPage extends WizardPage {

	public String title;
	public String abs;
	public String elements;
	public String genericDescription;

	// GUI

	public Button btnNo;
	public Button btnYes;

	/**
	 * Create the wizard.
	 */
	public PresentationPage(String title, String abs, String elements, String genericDescription) {
		super(title);

		if (this.abs == null)
			this.abs = "";
		else
			this.abs = abs;

		this.elements = elements;

		this.genericDescription = genericDescription;

		setTitle(title);
		setDescription("This Wizard will help you create a new diagram to represent " + abs + " pattern.");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);

		Label lblWouldYouLike = new Label(container, SWT.NONE);
		lblWouldYouLike.setText("Would you like to create a new diagram?");

		btnYes = new Button(container, SWT.RADIO);
		btnYes.setText("Yes");

		btnNo = new Button(container, SWT.RADIO);
		btnNo.setText("No");
		btnNo.setSelection(true);
		GroupLayout gl_container = new GroupLayout(container);
		gl_container.setHorizontalGroup(gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup().add(11)
						.add(gl_container.createParallelGroup(GroupLayout.LEADING)).add(11))
				.add(gl_container.createSequentialGroup().addContainerGap())
				.add(gl_container.createSequentialGroup().addContainerGap().addContainerGap())
				.add(gl_container.createSequentialGroup().addContainerGap()
						.add(gl_container.createParallelGroup(GroupLayout.LEADING)
								.add(lblWouldYouLike, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
								.add(btnYes, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
								.add(btnNo, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
						.addContainerGap()));
		gl_container.setVerticalGroup(
				gl_container.createParallelGroup(GroupLayout.LEADING).add(gl_container.createSequentialGroup().add(13)

						.addPreferredGap(LayoutStyle.RELATED).addPreferredGap(LayoutStyle.RELATED)
						.addPreferredGap(LayoutStyle.RELATED).addPreferredGap(LayoutStyle.RELATED).add(lblWouldYouLike)
						.add(8).add(btnYes).add(8).add(btnNo).add(11)));
		container.setLayout(gl_container);
	}

	@Override
	public IWizardPage getNextPage() {
		return super.getNextPage();
	}
}
