package net.menthor.editor.v2.ui.abstractionRecognition;

/**
 * ============================================================================================
 * Menthor Editor -- Copyright (c) 2015 
 *
 * This file is part of Menthor Editor. Menthor Editor is based on TinyUML and as so it is 
 * distributed under the same license terms.
 *
 * Menthor Editor is free software; you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation; either 
 * version 2 of the License, or (at your option) any later version.
 *
 * Menthor Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Menthor Editor; 
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * ============================================================================================
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

import net.menthor.abstractionRecognition.Abstraction;
import net.menthor.abstractionRecognition.AbstractionList;
import net.menthor.abstractionRecognition.AbstractionOccurrence;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstraction;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstractionOccurrence;
import net.menthor.abstractionRecognition.wizard.kindPattern.RelatorAbstractionWizard;
import net.menthor.editor.v2.ui.controller.ProjectUIController;

/**
 * @author Guylerme Figueiredo
 *
 */
public class AbstractionResultDialog extends Dialog {

	protected JFrame frame;
	protected Display display;

	protected ArrayList<AbstractionOccurrence> allOccurrences;
	protected ArrayList<AbstractionOccurrence> result;
	protected static TableViewer viewer;
	protected AbstractionResultFilter filter;
	protected Composite container;
	protected Label searchLabel;
	protected Text searchText;
	protected Button btnNewDiagram;
	protected Button btnGenerateHtml;
	protected Button btnGeneratePlantUML;

	protected Table table;

	protected Label feedBackLabel;

	public void showWizard(final AbstractionOccurrence absOccur, Display display) {
		WizardDialog wizardDialog = getWizardDialog(absOccur, display);
		if (wizardDialog != null && wizardDialog.open() == Window.OK) {

			ProjectUIController.get().addDiagram();

			refresh();
		}
	}

	public AbstractionResultDialog(Shell parentShell, List<AbstractionOccurrence> result, JFrame frame, Display display) {
		super(parentShell);
		this.result = new ArrayList<AbstractionOccurrence>(result);
		this.allOccurrences = new ArrayList<AbstractionOccurrence>(result);
		this.frame = frame;
		this.display = display;
	}

	@Override
	public void create() {
		super.create();
		setShellStyle(SWT.TITLE);
		bringToFront(getShell());
		getShell().setText("Pattern Result");
	}

	public void bringToFront(final Shell shell) {
		shell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				shell.forceActive();
			}
		});
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);

		createPartControl(container);

		GroupLayout gl_container = new GroupLayout(container);
		gl_container
				.setHorizontalGroup(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(gl_container.createSequentialGroup()
								.addContainerGap().add(
										gl_container
												.createParallelGroup(GroupLayout.LEADING).add(
														gl_container.createSequentialGroup().add(searchLabel)
																.addPreferredGap(LayoutStyle.RELATED)
																.add(searchText, GroupLayout.DEFAULT_SIZE, 285,
																		Short.MAX_VALUE)
																.add(66)
																.add(btnNewDiagram, GroupLayout.PREFERRED_SIZE, 120,
																		GroupLayout.PREFERRED_SIZE)
																.add(btnGenerateHtml, GroupLayout.PREFERRED_SIZE, 120,
																		GroupLayout.PREFERRED_SIZE)
																.add(btnGeneratePlantUML, GroupLayout.PREFERRED_SIZE,
																		120, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(LayoutStyle.RELATED))
												.add(table, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
												.add(feedBackLabel, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
								.addContainerGap()));
		gl_container
				.setVerticalGroup(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(gl_container.createSequentialGroup().addContainerGap()
								.add(gl_container.createParallelGroup(GroupLayout.LEADING, false)
										.add(searchLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(gl_container.createParallelGroup(GroupLayout.BASELINE)
												.add(searchText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)

												.add(btnNewDiagram, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.add(btnGenerateHtml, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(btnGeneratePlantUML, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addPreferredGap(LayoutStyle.UNRELATED)
								.add(table, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addPreferredGap(LayoutStyle.RELATED).add(feedBackLabel).add(15)));
		container.setLayout(gl_container);

		return container;
	}

	public static void openDialog(final AbstractionList apList, final JFrame frame, Display display) {
		if (apList != null && !apList.getAll().isEmpty()) {
			Shell shell = new Shell(display);
			AbstractionResultDialog resultDIalog = new AbstractionResultDialog(shell, apList.getAll(), frame, display);
			resultDIalog.create();
			resultDIalog.open();
		}
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(690, 518);
	}

	public void createPartControl(Composite parent) {

		searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Find: ");

		searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);

		btnNewDiagram = new Button(container, SWT.NONE);
		btnNewDiagram.setText("New Diagram");
		btnNewDiagram.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				feedBackLabel.setVisible(false);
				feedBackLabel.setText("Abstraction Wizard Open!");
				feedBackLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				feedBackLabel.setVisible(true);
				// showWizard((PatternOccurrence)
				// viewer.getElementAt(table.getSelectionIndex()), display);

				AbstractionProjectUIController c = new AbstractionProjectUIController();

				List<AbstractionOccurrence> occurrencies;

				for (int i : table.getSelectionIndices()) {

					occurrencies = new ArrayList<AbstractionOccurrence>();

					String padrao = ((AbstractionOccurrence) viewer.getElementAt(i)).getAbstraction().info().getAcronym();

					for (AbstractionOccurrence o : result) {
						if (o.getAbstraction().info().getAcronym().equalsIgnoreCase(padrao)) {
							occurrencies.add(o);
						}
					}

					c.createAbstractionDiagram(occurrencies, padrao);
				}

				AbstractionResultDialog.this.cancelPressed();
			}
		});

		btnGenerateHtml = new Button(container, SWT.NONE);
		btnGenerateHtml.setText("Generate HTML");
		btnGenerateHtml.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				feedBackLabel.setVisible(false);
				feedBackLabel.setText("HTML Generation Open!");
				feedBackLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				feedBackLabel.setVisible(true);

				AbstractionProjectUIController c = new AbstractionProjectUIController();

				List<AbstractionOccurrence> occurrencies;

				HashMap<String, List<AbstractionOccurrence>> occurrenciesAbstractions = new HashMap<String, List<AbstractionOccurrence>>();

				for (int i = 0; i < table.getItems().length; i++) {

					occurrencies = new ArrayList<AbstractionOccurrence>();

					String padrao = ((AbstractionOccurrence) viewer.getElementAt(i)).getAbstraction().info().getAcronym();

					for (AbstractionOccurrence o : result) {
						if (o.getAbstraction().info().getAcronym().equalsIgnoreCase(padrao)) {
							occurrencies.add(o);
						}
					}
					occurrenciesAbstractions.put(padrao, occurrencies);

				}
				c.createAbstractionsHTML(occurrenciesAbstractions);

				AbstractionResultDialog.this.cancelPressed();
			}
		});

		btnGeneratePlantUML = new Button(container, SWT.NONE);
		btnGeneratePlantUML.setText("Generate PlantUML");
		btnGeneratePlantUML.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				feedBackLabel.setVisible(false);
				feedBackLabel.setText("PlantUML Generation Open!");
				feedBackLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				feedBackLabel.setVisible(true);

				AbstractionProjectUIController c = new AbstractionProjectUIController();
				List<AbstractionOccurrence> occurrencies;

				HashMap<String, List<AbstractionOccurrence>> occurrenciesAbstractions = new HashMap<String, List<AbstractionOccurrence>>();

				for (int i = 0; i < table.getItems().length; i++) {

					occurrencies = new ArrayList<AbstractionOccurrence>();

					String padrao = ((AbstractionOccurrence) viewer.getElementAt(i)).getAbstraction().info().getAcronym();

					for (AbstractionOccurrence o : result) {
						if (o.getAbstraction().info().getAcronym().equalsIgnoreCase(padrao)) {
							occurrencies.add(o);
						}
					}
					occurrenciesAbstractions.put(padrao, occurrencies);

				}
				c.createAbstractionPlantUML(occurrenciesAbstractions);

				AbstractionResultDialog.this.cancelPressed();
			}
		});

		createViewer(parent);

		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
				feedBackLabel.setVisible(false);
				feedBackLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				feedBackLabel.setText(table.getItemCount() + " results were found");
				feedBackLabel.setVisible(true);
			}
		});

		feedBackLabel = new Label(container, SWT.NONE);
		feedBackLabel.setAlignment(SWT.RIGHT);
		feedBackLabel.setVisible(false);

		filter = new AbstractionResultFilter();
		viewer.addFilter(filter);
	}

	/**
	 * Create Table Viewer
	 * 
	 * @param parent
	 */
	private void createViewer(Composite parent) {

		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);

		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setContentProvider(new ArrayContentProvider());

		// eliminar valores repatidos na lista
		ArrayList<AbstractionOccurrence> resultNew = new ArrayList<AbstractionOccurrence>();

		for (AbstractionOccurrence p : result) {

			if (!existsInList(p.getAbstraction(), resultNew))
				resultNew.add(p);
		}
		viewer.setInput(resultNew);

		// Define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

	}

	private boolean existsInList(Abstraction<?> abstraction, ArrayList<AbstractionOccurrence> resultNew) {
		for (AbstractionOccurrence p : resultNew) {
			if (p.getAbstraction() == abstraction)
				return true;
		}
		return false;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	/**
	 * Create the columns for the table
	 */
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Type" };
		int[] bounds = { 350 };

		// // First column is for a short description of the pattern
		// TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0],
		// 0);
		//
		// col.setLabelProvider(new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// return ((PatternOccurrence) element).getShortName();
		// }
		//
		// @Override
		// public Image getImage(Object element) {
		// return super.getImage(element);
		// }
		// });

		// Sets the type of the pattern
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);

		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof RelatorAbstractionOccurrence)
					return RelatorAbstraction.getAbstractionInfo().getAcronym();
				
				return "<error>";
			}
		});

		// // Show the button to investigate the occurrence
		// col = createTableViewerColumn(titles[3], bounds[3], 3);
		//
		// col.setLabelProvider(new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// return "";
		// }
		// });
		//
		// // Show the button to remove the occurrence
		// col = createTableViewerColumn(titles[4], bounds[4], 4);
		//
		// col.setLabelProvider(new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// return "";
		// }
		// });
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public static void refresh() {
		viewer.refresh();
	}

	public WizardDialog getWizardDialog(final AbstractionOccurrence apOccur, Display d) {
		WizardDialog wizardDialog = null;

		if (apOccur instanceof RelatorAbstractionOccurrence)
			wizardDialog = new WizardDialog(new Shell(d), new RelatorAbstractionWizard((RelatorAbstractionOccurrence) apOccur));
		
		return wizardDialog;
	}
}
