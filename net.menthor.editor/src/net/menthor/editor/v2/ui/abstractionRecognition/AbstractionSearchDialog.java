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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.eclipse.swt.widgets.Display;

import RefOntoUML.parser.OntoUMLParser;
import net.menthor.abstractionRecognition.Abstraction;
import net.menthor.abstractionRecognition.AbstractionInfo;
import net.menthor.abstractionRecognition.AbstractionList;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstraction;
import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstraction;
import net.menthor.editor.v2.ui.controller.ProjectUIController;
import net.menthor.swt.Util;

/**
 * @author Guylerme Figueiredo
 *
 */

public class AbstractionSearchDialog extends JDialog {
	private long tempoInicial = 0;

	private class Supervisor extends SwingWorker<Void, Void> {

		CountDownLatch latch;

		public Supervisor(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		protected Void doInBackground() throws Exception {
			latch.await();
			return null;
		}

		@Override
		protected void done() {
			progressBar.setValue(100);
			progressBar.setIndeterminate(false);

			identifyButton.setEnabled(true);
			showButton.setEnabled(true);
			stopButton.setEnabled(false);

			updateStatus("Abstractions: Completed! " + abstractionRecognitionList.getAll().size()
					+ " occurrence(s) found" + "| Executed in " + (System.currentTimeMillis() - tempoInicial) + " ms");

			// execução do método
			System.out.println("O metodo executou em " + (System.currentTimeMillis() - tempoInicial));
		}
	}

	private static final long serialVersionUID = 1L;

	public static boolean onMac() {
		return System.getProperty("mrj.version") != null
				|| System.getProperty("os.name").toLowerCase(Locale.US).startsWith("mac ");
	}

	/**
	 * Open the Dialog.
	 */
	public static void open(JFrame parent, OntoUMLParser refparser) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			AbstractionSearchDialog dialog = new AbstractionSearchDialog(parent, refparser);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(parent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<AbstractionTask> allTasks = new ArrayList<AbstractionTask>();
	private JCheckBox cbxRelatorAbstraction;
	private JCheckBox cbxNonSortalAbstraction;
	ArrayList<JCheckBox> cbxList = new ArrayList<JCheckBox>();

	private JButton closeButton;
	private final JPanel contentPanel = new JPanel();
	private ExecutorService executor;
	private JFrame frame;
	private JButton identifyButton;
	private int incrementalValue;

	private AbstractionTask RelatorAbstractionTask;
	private AbstractionTask NonSortalAbstractionTask;

	private CountDownLatch latch;
	ArrayList<JButton> lblIcoList = new ArrayList<JButton>();
	private JButton lblRelatorAbstractionIco;
	private JLabel lblRelatorAbstractionRes;

	private JButton lblNonSortalAbstractionIco;
	private JLabel lblNonSortalAbstractionRes;

	ArrayList<JLabel> lblResultList = new ArrayList<JLabel>();

	private JPanel panel_1;

	private OntoUMLParser parser;
	private AbstractionList abstractionRecognitionList;

	private SwingWorker<Void, Void> preTask;
	private JProgressBar progressBar;

	private JLabel progressBarDescr;

	@SuppressWarnings("unused")
	private String result = new String();

	private JButton showButton;

	private JButton stopButton;

	/**
	 * Create the dialog.
	 */
	public AbstractionSearchDialog(JFrame parent, OntoUMLParser refparser) {
		super(parent);

		this.frame = parent;
		this.parser = refparser;

		setTitle("Pattern Identification");
		setBounds(100, 100, 854, 511);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setPreferredSize(new Dimension(180, 410));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.NORTH);

		JLabel lblChooseWhichPattern = new JLabel("    Choose which abstraction rule do you want to execute:");
		lblChooseWhichPattern.setBounds(6, 25, 300, 16);

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(6, 100, 403, 249);
		leftPanel.setBorder(BorderFactory.createTitledBorder(""));
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(411, 100, 437, 249);
		rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		rightPanel.setBorder(BorderFactory.createTitledBorder(""));
		JPanel panel = new JPanel();
		panel.setBounds(6, 53, 842, 43);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBorder(BorderFactory.createTitledBorder(""));

		panel_1 = new JPanel();
		panel_1.setBounds(220, 361, 388, 43);
		panel_1.setBorder(BorderFactory.createTitledBorder(""));

		identifyButton = new JButton("Search");
		panel_1.add(identifyButton);
		identifyButton.setIcon(null);

		closeButton = new JButton("Close");
		panel_1.add(closeButton);

		stopButton = new JButton("Stop");
		stopButton.setEnabled(false);
		panel_1.add(stopButton);

		showButton = new JButton("Show Result");
		panel_1.add(showButton);
		showButton.setEnabled(false);
		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// dispose();
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						final Display display;
						if (Util.onWindows()) {
							display = new Display();
						} else {
							display = Display.getDefault();
						}
						showResult(abstractionRecognitionList, display);
					}
				});
				t.start();
			}
		});

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStatus("Pattern: analysis stopped by the user!");
				interruptAll();
			}
		});

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				interruptAll();
				dispose();
			}
		});

		identifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				IdentifyButtonActionPerformed(event);
			}
		});

		JButton btnEnableall = new JButton("Enable All");
		panel.add(btnEnableall);

		btnEnableall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSelectedCheckboxes(true);
			}
		});

		JButton btnDisableall = new JButton("Disable All");
		panel.add(btnDisableall);

		btnDisableall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSelectedCheckboxes(false);
			}
		});

		lblRelatorAbstractionIco = new JButton();
		lblRelatorAbstractionIco.setBounds(6, 23, 20, 17);
		lblRelatorAbstractionIco.setPreferredSize(new Dimension(20, 20));
		lblRelatorAbstractionIco.setOpaque(false);
		lblRelatorAbstractionIco.setContentAreaFilled(false);
		lblRelatorAbstractionIco.setBorderPainted(false);
		cbxRelatorAbstraction = new JCheckBox(RelatorAbstraction.getAbstractionInfo().getAcronym() + ": "
				+ RelatorAbstraction.getAbstractionInfo().getName());
		cbxRelatorAbstraction.setBounds(38, 23, 258, 23);
		cbxRelatorAbstraction.setPreferredSize(new Dimension(230, 20));
		cbxRelatorAbstraction.setBackground(UIManager.getColor("Panel.background"));
		lblRelatorAbstractionRes = new JLabel("");
		lblRelatorAbstractionRes.setBounds(324, 20, 66, 20);
		lblRelatorAbstractionRes.setPreferredSize(new Dimension(110, 20));
		lblRelatorAbstractionRes.setForeground(Color.BLUE);

		lblNonSortalAbstractionIco = new JButton();
		lblNonSortalAbstractionIco.setBounds(6, 52, 20, 17);
		lblNonSortalAbstractionIco.setPreferredSize(new Dimension(20, 20));
		lblNonSortalAbstractionIco.setOpaque(false);
		lblNonSortalAbstractionIco.setContentAreaFilled(false);
		lblNonSortalAbstractionIco.setBorderPainted(false);
		cbxNonSortalAbstraction = new JCheckBox(NonSortalAbstraction.getAbstractionInfo().getAcronym() + ": "
				+ NonSortalAbstraction.getAbstractionInfo().getName());
		cbxNonSortalAbstraction.setBounds(38, 52, 284, 23);
		cbxNonSortalAbstraction.setPreferredSize(new Dimension(230, 20));
		cbxNonSortalAbstraction.setBackground(UIManager.getColor("Panel.background"));
		lblNonSortalAbstractionRes = new JLabel("");
		lblNonSortalAbstractionRes.setBounds(334, 52, 56, 20);
		lblNonSortalAbstractionRes.setPreferredSize(new Dimension(110, 20));
		lblNonSortalAbstractionRes.setForeground(Color.BLUE);

		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.CENTER);
		buttonPane.setPreferredSize(new Dimension(60, 65));

		progressBarDescr = new JLabel("");
		progressBarDescr.setForeground(Color.BLUE);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
		gl_buttonPane.setHorizontalGroup(gl_buttonPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_buttonPane.createSequentialGroup().addGap(22)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(progressBarDescr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 789,
										Short.MAX_VALUE))
						.addContainerGap(18, Short.MAX_VALUE)));
		gl_buttonPane.setVerticalGroup(gl_buttonPane.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_buttonPane.createSequentialGroup().addContainerGap()
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(progressBarDescr)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		buttonPane.setLayout(gl_buttonPane);

		cbxList.add(cbxRelatorAbstraction);
		cbxList.add(cbxNonSortalAbstraction);

		lblIcoList.add(lblRelatorAbstractionIco);
		lblIcoList.add(lblNonSortalAbstractionIco);

		lblResultList.add(lblRelatorAbstractionRes);
		lblResultList.add(lblNonSortalAbstractionRes);

		contentPanel.setLayout(null);
		contentPanel.add(lblChooseWhichPattern);
		contentPanel.add(panel);
		contentPanel.add(leftPanel);
		leftPanel.setLayout(null);

		leftPanel.add(lblRelatorAbstractionIco);
		leftPanel.add(lblNonSortalAbstractionIco);

		leftPanel.add(cbxRelatorAbstraction);
		leftPanel.add(cbxNonSortalAbstraction);

		leftPanel.add(lblRelatorAbstractionRes);
		leftPanel.add(lblNonSortalAbstractionRes);
		contentPanel.add(rightPanel);
		contentPanel.add(panel_1);

		setIcons();
		showAllPatternIconLabels(true);
	}

	public void activateShowResult() {
		showButton.setEnabled(true);
	}

	public void cleanResultlabels() {
		for (JLabel label : lblResultList)
			label.setText("");
	}

	private void executeAbstraction(AbstractionTask task, Abstraction<?> abstractionRecognition, AbstractionInfo info,
			JLabel label, JCheckBox checkBox, int incrementalValue, CountDownLatch latch, ExecutorService executor,
			CountDownLatch preLatch) {

		task = new AbstractionTask(abstractionRecognition, info, label, checkBox, progressBar, progressBarDescr,
				incrementalValue, latch, preLatch);
		allTasks.add(task);

		executor.execute(task);
	}

	public int getTotalSelected() {
		int totalItemsSelected = 0;

		for (JCheckBox cbx : cbxList) {
			if (cbx.isSelected())
				totalItemsSelected++;
		}

		return totalItemsSelected;
	}

	/**
	 * Identifying Patterns...
	 * 
	 * @param event
	 */
	public void IdentifyButtonActionPerformed(ActionEvent event) {
		tempoInicial = System.currentTimeMillis();

		updateStatus("Patterns: Interrupting current tasks...");
		interruptAll();
		int selected = getTotalSelected();

		if (selected == 0) {
			updateStatus("Patterns: No patternRecognition selected!");
			return;
		} else {
			updateStatus("Patterns: " + selected + " patternRecognition(s) selected.");
		}

		try {

			identifyButton.setEnabled(false);
			showButton.setEnabled(false);
			stopButton.setEnabled(true);
			cleanResultlabels();
			setPlainFontOnCheckboxes();
			// frame.selectConsole();
			progressBar.setValue(0);
			progressBar.setIndeterminate(true);
			updateStatus("Patterns: Retrieving checked elements...");

			final CountDownLatch preLatch = new CountDownLatch(1);

			preTask = new SwingWorker<Void, Void>() {

				// private List<Object> checked;

				@Override
				protected Void doInBackground() throws Exception {
					// we dont work anymore with a check box on the application
					// checked = FilterManager.get().workingOnlyWithChecked();
					return null;
				}

				@Override
				protected void done() {
					// ProjectBrowser modeltree = frame.getProjectBrowser();
					// modeltree.getTree().check(checked);
					// modeltree.getTree().updateUI();
					preLatch.countDown();
				}
			};
			preTask.execute();

			if (parser.getElements() == null)
				return;

			RelatorAbstraction relatorAbstraction = new RelatorAbstraction(parser);
			NonSortalAbstraction nonSortalAbstraction = new NonSortalAbstraction(parser);

			incrementalValue = 100;

			if (selected > 1)
				incrementalValue = 100 / selected;

			allTasks.clear();

			latch = new CountDownLatch(selected);
			executor = Executors.newFixedThreadPool(4);

			if (relatorAbstractionIsSelected())
				executeAbstraction(RelatorAbstractionTask, relatorAbstraction, RelatorAbstraction.getAbstractionInfo(),
						lblRelatorAbstractionRes, cbxRelatorAbstraction, incrementalValue, latch, executor, preLatch);
			if (nonSortalAbstractionIsSelected())
				executeAbstraction(NonSortalAbstractionTask, nonSortalAbstraction,
						NonSortalAbstraction.getAbstractionInfo(), lblNonSortalAbstractionRes, cbxNonSortalAbstraction,
						incrementalValue, latch, executor, preLatch);

			abstractionRecognitionList = new AbstractionList(relatorAbstraction, nonSortalAbstraction);

			transferResult(abstractionRecognitionList);

			new Supervisor(latch).execute();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Abstraction Search", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void interruptAll() {
		if (preTask != null && !preTask.isDone())
			preTask.cancel(true);

		for (AbstractionTask task : allTasks) {
			if (task != null && !task.isDone())
				task.cancel(true);
		}

		if (executor != null && !executor.isShutdown())
			executor.shutdownNow();

	}

	/**
	 * Check if Pattern is selected.
	 */

	public Boolean relatorAbstractionIsSelected() {
		return cbxRelatorAbstraction.isSelected();
	}

	public Boolean nonSortalAbstractionIsSelected() {
		return cbxNonSortalAbstraction.isSelected();
	}

	/** open the result which in turn can call the wizards */
	public void openResult(AbstractionList list, Display display) {
		AbstractionResultDialog.openDialog(list, frame, display);
	}

	public void setIcons() {
		// lblUndefFormalIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblUndefFormalIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblUndefPhaseIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblUndefPhaseIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblWholeOverIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblWholeOverIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblAssCycIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblAssCycIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblBinOverIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblBinOverIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblDecIntIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblDecIntIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblDepPhaseIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblDepPhaseIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblFreeRoleIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblFreeRoleIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblGSRigIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblGSRigIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblHetCollIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblHetCollIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblHomoFuncIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblHomoFuncIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblImpAbsIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblImpAbsIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblMixIdenIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblMixIdenIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblMixRigIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblMixRigIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblRepRelIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblRepRelIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblRelSpecIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblRelSpecIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblRelRigIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblRelRigIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblRelOverIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblRelOverIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblRelCompIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblRelCompIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblPartOverIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblPartOverIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
		// lblMultiDepIco.setIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELP));
		// lblMultiDepIco.setRolloverIcon(IconMap.getInstance().getIcon(IconType.MENTHOR_HELPROLLOVER));
	}

	public void setPlainFontOnCheckboxes() {
		for (JCheckBox cbx : cbxList)
			cbx.setFont(new Font(cbx.getFont().getName(), Font.PLAIN, cbx.getFont().getSize()));
	}

	private void setSelectedCheckboxes(boolean b) {
		for (JCheckBox cbx : cbxList)
			if (cbx.isSelected() != b)
				cbx.setSelected(b);
	}

	public void showAllPatternIconLabels(boolean b) {
		for (JButton ico : lblIcoList)
			ico.setVisible(b);
	}

	/**
	 * Show Result
	 */
	public void showResult(final AbstractionList ptrnList, final Display display) {
		if (onMac()) {
			com.apple.concurrent.Dispatch.getInstance().getNonBlockingMainQueueExecutor().execute(new Runnable() {
				@Override
				public void run() {
					openResult(ptrnList, display);
				}
			});
		} else {
			openResult(ptrnList, display);
		}
	}

	/** transfer result of search to an application */
	public void transferResult(AbstractionList list) {
		ProjectUIController.get().getProject().setAbstractions(list);
	}

	private void updateStatus(String s) {
		progressBarDescr.setText(s);
		System.out.println(s);
	}

}
