package net.menthor.editor.v2.ui.patternRecognition;

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
import net.menthor.editor.v2.ui.controller.ProjectUIController;
import net.menthor.patternRecognition.Pattern;
import net.menthor.patternRecognition.PatternInfo;
import net.menthor.patternRecognition.PatternList;
import net.menthor.patternRecognition.kindPattern.KindPattern;
import net.menthor.patternRecognition.phasePattern.PhasePattern;
import net.menthor.patternRecognition.rolePattern.RolePattern;
import net.menthor.patternRecognition.subKindPattern.SubKindPattern;
import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalPattern;
import net.menthor.swt.Util;

/**
 * @author Guylerme Figueiredo
 *
 */

public class PatternSearchDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private OntoUMLParser parser;

	private final JPanel contentPanel = new JPanel();

	private PatternTask KindPatternTask;
	private PatternTask SubstanceSortalPatternTask;
	private PatternTask SubKindPatternTask;
	private PatternTask PhasePatternTask;
	private PatternTask RolePatternTask;

	private ArrayList<PatternTask> allTasks = new ArrayList<PatternTask>();

	private JCheckBox cbxKindPattern;
	private JCheckBox cbxSubstanceSortalPattern;
	private JCheckBox cbxSubKindPattern;
	private JCheckBox cbxPhasePattern;
	private JCheckBox cbxRolePattern;

	ArrayList<JCheckBox> cbxList = new ArrayList<JCheckBox>();

	private JButton lblKindPatternIco;
	private JButton lblSubstanceSortalPatternIco;
	private JButton lblSubKindPatternIco;
	private JButton lblPhasePatternIco;
	private JButton lblRolePatternIco;

	ArrayList<JButton> lblIcoList = new ArrayList<JButton>();

	private JProgressBar progressBar;
	private JLabel progressBarDescr;

	private JLabel lblKindPatternRes;
	private JLabel lblSubstanceSortalPatternRes;
	private JLabel lblSubKindPatternRes;
	private JLabel lblPhasePatternRes;
	private JLabel lblRolePatternRes;

	ArrayList<JLabel> lblResultList = new ArrayList<JLabel>();

	private JButton identifyButton;
	private JButton closeButton;
	private JButton showButton;
	private JButton stopButton;

	@SuppressWarnings("unused")
	private String result = new String();
	private JPanel panel_1;

	private int incrementalValue;

	private ExecutorService executor;

	private CountDownLatch latch;

	private PatternList patternRecognitionList;

	private SwingWorker<Void, Void> preTask;

	/** transfer result of search to an application */
	public void transferResult(PatternList list) {
		ProjectUIController.get().getProject().setPatterns(list);
	}

	/** open the result which in turn can call the wizards */
	public void openResult(PatternList list, Display display) {
		PatternResultDialog.openDialog(list, frame, display);
	}

	/**
	 * Check if Pattern is selected.
	 */

	public Boolean kindPatternIsSelected() {
		return cbxKindPattern.isSelected();
	}

	public Boolean substanceSortalPatternIsSelected() {
		return cbxSubstanceSortalPattern.isSelected();
	}

	public Boolean subKindPatternIsSelected() {
		return cbxSubKindPattern.isSelected();
	}

	public Boolean phasePatternIsSelected() {
		return cbxPhasePattern.isSelected();
	}

	public Boolean rolePatternIsSelected() {
		return cbxRolePattern.isSelected();
	}

	/**
	 * Create the dialog.
	 */
	public PatternSearchDialog(JFrame parent, OntoUMLParser refparser) {
		super(parent);

		this.frame = parent;
		this.parser = refparser;

		setTitle("Pattern Identification");
		setBounds(100, 100, 854, 511);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setPreferredSize(new Dimension(180, 410));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.NORTH);

		JLabel lblChooseWhichAntipattern = new JLabel("    Choose which pattern do you want to search:");
		;

		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(BorderFactory.createTitledBorder(""));
		JPanel rightPanel = new JPanel();
		rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		rightPanel.setBorder(BorderFactory.createTitledBorder(""));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBorder(BorderFactory.createTitledBorder(""));

		panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder(""));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblChooseWhichAntipattern, GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING,
										gl_contentPanel.createSequentialGroup()
												.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, 391,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(rightPanel,
														GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE))))
						.addGap(3)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup().addContainerGap().addComponent(lblChooseWhichAntipattern)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
						.addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

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
						showResult(patternRecognitionList, display);
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

		lblKindPatternIco = new JButton();
		lblKindPatternIco.setPreferredSize(new Dimension(20, 20));
		lblKindPatternIco.setOpaque(false);
		lblKindPatternIco.setContentAreaFilled(false);
		lblKindPatternIco.setBorderPainted(false);
		leftPanel.add(lblKindPatternIco);
		cbxKindPattern = new JCheckBox(
				KindPattern.getPatternInfo().getAcronym() + ": " + KindPattern.getPatternInfo().getName());
		cbxKindPattern.setPreferredSize(new Dimension(230, 20));
		cbxKindPattern.setBackground(UIManager.getColor("Panel.background"));
		leftPanel.add(cbxKindPattern);
		lblKindPatternRes = new JLabel("");
		lblKindPatternRes.setPreferredSize(new Dimension(110, 20));
		lblKindPatternRes.setForeground(Color.BLUE);
		leftPanel.add(lblKindPatternRes);

		lblSubstanceSortalPatternIco = new JButton();
		lblSubstanceSortalPatternIco.setPreferredSize(new Dimension(20, 20));
		lblSubstanceSortalPatternIco.setOpaque(false);
		lblSubstanceSortalPatternIco.setContentAreaFilled(false);
		lblSubstanceSortalPatternIco.setBorderPainted(false);
		leftPanel.add(lblSubstanceSortalPatternIco);
		cbxSubstanceSortalPattern = new JCheckBox(SubstanceSortalPattern.getPatternInfo().getAcronym() + ": "
				+ SubstanceSortalPattern.getPatternInfo().getName());
		cbxSubstanceSortalPattern.setPreferredSize(new Dimension(230, 20));
		cbxSubstanceSortalPattern.setBackground(UIManager.getColor("Panel.background"));
		leftPanel.add(cbxSubstanceSortalPattern);
		lblSubstanceSortalPatternRes = new JLabel("");
		lblSubstanceSortalPatternRes.setPreferredSize(new Dimension(110, 20));
		lblSubstanceSortalPatternRes.setForeground(Color.BLUE);
		leftPanel.add(lblSubstanceSortalPatternRes);

		lblSubKindPatternIco = new JButton();
		lblSubKindPatternIco.setPreferredSize(new Dimension(20, 20));
		lblSubKindPatternIco.setOpaque(false);
		lblSubKindPatternIco.setContentAreaFilled(false);
		lblSubKindPatternIco.setBorderPainted(false);
		rightPanel.add(lblSubKindPatternIco);
		cbxSubKindPattern = new JCheckBox(
				SubKindPattern.getPatternInfo().getAcronym() + ": " + SubKindPattern.getPatternInfo().getName());
		cbxSubKindPattern.setPreferredSize(new Dimension(230, 20));
		cbxSubKindPattern.setBackground(UIManager.getColor("Panel.background"));
		rightPanel.add(cbxSubKindPattern);
		lblSubKindPatternRes = new JLabel("");
		lblSubKindPatternRes.setPreferredSize(new Dimension(110, 20));
		lblSubKindPatternRes.setForeground(Color.BLUE);
		rightPanel.add(lblSubKindPatternRes);

		lblPhasePatternIco = new JButton();
		lblPhasePatternIco.setPreferredSize(new Dimension(20, 20));
		lblPhasePatternIco.setOpaque(false);
		lblPhasePatternIco.setContentAreaFilled(false);
		lblPhasePatternIco.setBorderPainted(false);
		rightPanel.add(lblPhasePatternIco);
		cbxPhasePattern = new JCheckBox(
				PhasePattern.getPatternInfo().getAcronym() + ": " + PhasePattern.getPatternInfo().getName());
		cbxPhasePattern.setPreferredSize(new Dimension(230, 20));
		cbxPhasePattern.setBackground(UIManager.getColor("Panel.background"));
		rightPanel.add(cbxPhasePattern);
		lblPhasePatternRes = new JLabel("");
		lblPhasePatternRes.setPreferredSize(new Dimension(110, 20));
		lblPhasePatternRes.setForeground(Color.BLUE);
		rightPanel.add(lblPhasePatternRes);

		lblRolePatternIco = new JButton();
		lblRolePatternIco.setPreferredSize(new Dimension(20, 20));
		lblRolePatternIco.setOpaque(false);
		lblRolePatternIco.setContentAreaFilled(false);
		lblRolePatternIco.setBorderPainted(false);
		rightPanel.add(lblRolePatternIco);
		cbxRolePattern = new JCheckBox(
				RolePattern.getPatternInfo().getAcronym() + ": " + RolePattern.getPatternInfo().getName());
		cbxRolePattern.setPreferredSize(new Dimension(230, 20));
		cbxRolePattern.setBackground(UIManager.getColor("Panel.background"));
		rightPanel.add(cbxRolePattern);
		lblRolePatternRes = new JLabel("");
		lblRolePatternRes.setPreferredSize(new Dimension(110, 20));
		lblRolePatternRes.setForeground(Color.BLUE);
		rightPanel.add(lblRolePatternRes);

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

		cbxList.add(cbxKindPattern);
		cbxList.add(cbxSubstanceSortalPattern);
		cbxList.add(cbxSubKindPattern);
		cbxList.add(cbxPhasePattern);
		cbxList.add(cbxRolePattern);

		lblIcoList.add(lblKindPatternIco);
		lblIcoList.add(lblSubstanceSortalPatternIco);
		lblIcoList.add(lblSubKindPatternIco);
		lblIcoList.add(lblPhasePatternIco);
		lblIcoList.add(lblRolePatternIco);

		lblResultList.add(lblKindPatternRes);
		lblResultList.add(lblSubstanceSortalPatternRes);
		lblResultList.add(lblSubKindPatternRes);
		lblResultList.add(lblPhasePatternRes);
		lblResultList.add(lblRolePatternRes);

		setIcons();
		showAllPatternIconLabels(true);
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

	/**
	 * Open the Dialog.
	 */
	public static void open(JFrame parent, OntoUMLParser refparser) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			PatternSearchDialog dialog = new PatternSearchDialog(parent, refparser);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(parent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean onMac() {
		return System.getProperty("mrj.version") != null
				|| System.getProperty("os.name").toLowerCase(Locale.US).startsWith("mac ");
	}

	/**
	 * Show Result
	 */
	public void showResult(final PatternList ptrnList, final Display display) {
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

	public void cleanResultlabels() {
		for (JLabel label : lblResultList)
			label.setText("");
	}

	public void showAllPatternIconLabels(boolean b) {
		for (JButton ico : lblIcoList)
			ico.setVisible(b);
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

	public int getTotalSelected() {
		int totalItemsSelected = 0;

		for (JCheckBox cbx : cbxList) {
			if (cbx.isSelected())
				totalItemsSelected++;
		}

		return totalItemsSelected;
	}

	public void interruptAll() {
		if (preTask != null && !preTask.isDone())
			preTask.cancel(true);

		for (PatternTask task : allTasks) {
			if (task != null && !task.isDone())
				task.cancel(true);
		}

		if (executor != null && !executor.isShutdown())
			executor.shutdownNow();

	}

	public void activateShowResult() {
		showButton.setEnabled(true);
	}

	private void updateStatus(String s) {
		progressBarDescr.setText(s);
		System.out.println(s);
	}

	private void executePattern(PatternTask task, Pattern<?> patternRecognition, PatternInfo info, JLabel label,
			JCheckBox checkBox, int incrementalValue, CountDownLatch latch, ExecutorService executor,
			CountDownLatch preLatch) {

		task = new PatternTask(patternRecognition, info, label, checkBox, progressBar, progressBarDescr,
				incrementalValue, latch, preLatch);
		allTasks.add(task);

		executor.execute(task);
	}

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

			updateStatus("Patterns: Completed! " + patternRecognitionList.getAll().size() + " occurrence(s) found");

		}
	}

	/**
	 * Identifying Patterns...
	 * 
	 * @param event
	 */
	public void IdentifyButtonActionPerformed(ActionEvent event) {
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

			KindPattern kindPattern = new KindPattern(parser);
			SubstanceSortalPattern substanceSortalPattern = new SubstanceSortalPattern(parser);
			SubKindPattern subKindPattern = new SubKindPattern(parser);
			PhasePattern phasePattern = new PhasePattern(parser);
			RolePattern rolePattern = new RolePattern(parser);

			incrementalValue = 100;

			if (selected > 1)
				incrementalValue = 100 / selected;

			allTasks.clear();

			latch = new CountDownLatch(selected);
			executor = Executors.newFixedThreadPool(4);

			if (kindPatternIsSelected())
				executePattern(KindPatternTask, kindPattern, KindPattern.getPatternInfo(), lblKindPatternRes,
						cbxKindPattern, incrementalValue, latch, executor, preLatch);

			if (substanceSortalPatternIsSelected())
				executePattern(SubstanceSortalPatternTask, substanceSortalPattern,
						SubstanceSortalPattern.getPatternInfo(), lblSubstanceSortalPatternRes,
						cbxSubstanceSortalPattern, incrementalValue, latch, executor, preLatch);

			if (subKindPatternIsSelected())
				executePattern(SubKindPatternTask, subKindPattern, SubKindPattern.getPatternInfo(),
						lblSubKindPatternRes, cbxSubKindPattern, incrementalValue, latch, executor, preLatch);

			if (phasePatternIsSelected())
				executePattern(PhasePatternTask, phasePattern, PhasePattern.getPatternInfo(), lblPhasePatternRes,
						cbxPhasePattern, incrementalValue, latch, executor, preLatch);

			if (rolePatternIsSelected())
				executePattern(RolePatternTask, rolePattern, RolePattern.getPatternInfo(), lblRolePatternRes,
						cbxRolePattern, incrementalValue, latch, executor, preLatch);

			patternRecognitionList = new PatternList(kindPattern, substanceSortalPattern, subKindPattern, phasePattern,
					rolePattern);

			transferResult(patternRecognitionList);

			new Supervisor(latch).execute();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Pattern Search", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
