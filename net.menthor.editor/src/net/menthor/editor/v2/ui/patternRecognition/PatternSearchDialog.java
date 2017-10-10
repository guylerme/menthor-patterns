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
import net.menthor.patternRecognition.modePattern.ModePattern;
import net.menthor.patternRecognition.nonSortalPattern.NonSortalPattern;
import net.menthor.patternRecognition.parthoodStructurePattern.ParthoodStructurePattern;
import net.menthor.patternRecognition.phasePattern.PhasePattern;
import net.menthor.patternRecognition.relatorPattern.RelatorPattern;
import net.menthor.patternRecognition.rolePattern.RolePattern;
import net.menthor.patternRecognition.subKindPattern.SubKindPattern;
import net.menthor.patternRecognition.substanceSortalPattern.SubstanceSortalPattern;
import net.menthor.swt.Util;

/**
 * @author Guylerme Figueiredo
 *
 */

public class PatternSearchDialog extends JDialog {

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

			PatternSearchDialog dialog = new PatternSearchDialog(parent, refparser);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(parent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<PatternTask> allTasks = new ArrayList<PatternTask>();
	private JCheckBox cbxKindPattern;
	ArrayList<JCheckBox> cbxList = new ArrayList<JCheckBox>();
	private JCheckBox cbxParthoodStructurePattern;
	private JCheckBox cbxPhasePattern;
	private JCheckBox cbxRelatorPattern;
	private JCheckBox cbxRolePattern;

	private JCheckBox cbxSubKindPattern;

	private JCheckBox cbxNonSortalPattern;

	private JCheckBox cbxModePattern;

	private JCheckBox cbxSubstanceSortalPattern;
	private JButton closeButton;
	private final JPanel contentPanel = new JPanel();
	private ExecutorService executor;
	private JFrame frame;
	private JButton identifyButton;
	private int incrementalValue;

	private PatternTask KindPatternTask;

	private CountDownLatch latch;
	ArrayList<JButton> lblIcoList = new ArrayList<JButton>();
	private JButton lblKindPatternIco;
	private JLabel lblKindPatternRes;
	private JButton lblParthoodStructurePatternIco;
	private JLabel lblParthoodStructurePatternRes;
	private JButton lblPhasePatternIco;

	private JLabel lblPhasePatternRes;

	private JButton lblRelatorPatternIco;
	private JLabel lblRelatorPatternRes;

	ArrayList<JLabel> lblResultList = new ArrayList<JLabel>();
	private JButton lblRolePatternIco;
	private JLabel lblRolePatternRes;
	private JButton lblSubKindPatternIco;
	private JLabel lblSubKindPatternRes;
	private JButton lblSubstanceSortalPatternIco;
	private JLabel lblSubstanceSortalPatternRes;
	private JButton lblNonSortalPatternIco;
	private JLabel lblNonSortalPatternRes;
	private JButton lblModePatternIco;
	private JLabel lblModePatternRes;

	private JPanel panel_1;

	private OntoUMLParser parser;
	private PatternTask ParthoodStructurePatternTask;
	private PatternList patternRecognitionList;
	private PatternTask PhasePatternTask;

	private SwingWorker<Void, Void> preTask;
	private JProgressBar progressBar;

	private JLabel progressBarDescr;

	private PatternTask RelatorPatternTask;

	@SuppressWarnings("unused")
	private String result = new String();

	private PatternTask RolePatternTask;

	private JButton showButton;

	private JButton stopButton;

	private PatternTask SubKindPatternTask;

	private PatternTask SubstanceSortalPatternTask;

	private PatternTask NonSortalPatternTask;

	private PatternTask ModePatternTask;

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

		JLabel lblChooseWhichPattern = new JLabel("    Choose which pattern do you want to search:");
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
		lblKindPatternIco.setBounds(6, 23, 20, 17);
		lblKindPatternIco.setPreferredSize(new Dimension(20, 20));
		lblKindPatternIco.setOpaque(false);
		lblKindPatternIco.setContentAreaFilled(false);
		lblKindPatternIco.setBorderPainted(false);
		cbxKindPattern = new JCheckBox(
				KindPattern.getPatternInfo().getAcronym() + ": " + KindPattern.getPatternInfo().getName());
		cbxKindPattern.setBounds(33, 23, 195, 23);
		cbxKindPattern.setPreferredSize(new Dimension(230, 20));
		cbxKindPattern.setBackground(UIManager.getColor("Panel.background"));
		lblKindPatternRes = new JLabel("");
		lblKindPatternRes.setBounds(240, 20, 150, 20);
		lblKindPatternRes.setPreferredSize(new Dimension(110, 20));
		lblKindPatternRes.setForeground(Color.BLUE);

		lblSubstanceSortalPatternIco = new JButton();
		lblSubstanceSortalPatternIco.setBounds(6, 69, 20, 29);
		lblSubstanceSortalPatternIco.setPreferredSize(new Dimension(20, 20));
		lblSubstanceSortalPatternIco.setOpaque(false);
		lblSubstanceSortalPatternIco.setContentAreaFilled(false);
		lblSubstanceSortalPatternIco.setBorderPainted(false);
		cbxSubstanceSortalPattern = new JCheckBox(SubstanceSortalPattern.getPatternInfo().getAcronym() + ": "
				+ SubstanceSortalPattern.getPatternInfo().getName());
		cbxSubstanceSortalPattern.setBounds(33, 69, 302, 23);
		cbxSubstanceSortalPattern.setPreferredSize(new Dimension(230, 20));
		cbxSubstanceSortalPattern.setBackground(UIManager.getColor("Panel.background"));
		lblSubstanceSortalPatternRes = new JLabel("");
		lblSubstanceSortalPatternRes.setBounds(354, 65, 38, 29);
		lblSubstanceSortalPatternRes.setPreferredSize(new Dimension(110, 20));
		lblSubstanceSortalPatternRes.setForeground(Color.BLUE);
		rightPanel.setLayout(null);
		lblRelatorPatternRes = new JLabel("");
		lblRelatorPatternRes.setBounds(266, 213, 146, -18);
		lblRelatorPatternRes.setPreferredSize(new Dimension(115, 20));
		lblRelatorPatternRes.setForeground(Color.BLUE);
		lblResultList.add(lblRelatorPatternRes);
		rightPanel.add(lblRelatorPatternRes);

		lblSubKindPatternIco = new JButton();
		lblSubKindPatternIco.setBounds(33, 57, -21, -29);
		lblSubKindPatternIco.setPreferredSize(new Dimension(20, 20));
		lblSubKindPatternIco.setOpaque(false);
		lblSubKindPatternIco.setContentAreaFilled(false);
		lblSubKindPatternIco.setBorderPainted(false);
		rightPanel.add(lblSubKindPatternIco);
		cbxSubKindPattern = new JCheckBox(
				SubKindPattern.getPatternInfo().getAcronym() + ": " + SubKindPattern.getPatternInfo().getName());
		cbxSubKindPattern.setBounds(33, 33, 246, 20);
		cbxSubKindPattern.setPreferredSize(new Dimension(230, 20));
		cbxSubKindPattern.setBackground(UIManager.getColor("Panel.background"));
		rightPanel.add(cbxSubKindPattern);

		lblPhasePatternIco = new JButton();
		lblPhasePatternIco.setBounds(16, 192, 20, 20);
		lblPhasePatternIco.setPreferredSize(new Dimension(20, 20));
		lblPhasePatternIco.setOpaque(false);
		lblPhasePatternIco.setContentAreaFilled(false);
		lblPhasePatternIco.setBorderPainted(false);
		rightPanel.add(lblPhasePatternIco);
		cbxPhasePattern = new JCheckBox(
				PhasePattern.getPatternInfo().getAcronym() + ": " + PhasePattern.getPatternInfo().getName());
		cbxPhasePattern.setBounds(33, 86, 230, 20);
		cbxPhasePattern.setPreferredSize(new Dimension(230, 20));
		cbxPhasePattern.setBackground(UIManager.getColor("Panel.background"));
		rightPanel.add(cbxPhasePattern);
		lblPhasePatternRes = new JLabel("");
		lblPhasePatternRes.setBounds(240, 86, 174, 20);
		lblPhasePatternRes.setPreferredSize(new Dimension(110, 20));
		lblPhasePatternRes.setForeground(Color.BLUE);
		rightPanel.add(lblPhasePatternRes);

		lblRolePatternIco = new JButton();
		lblRolePatternIco.setBounds(16, 139, 20, 20);
		lblRolePatternIco.setPreferredSize(new Dimension(20, 20));
		lblRolePatternIco.setOpaque(false);
		lblRolePatternIco.setContentAreaFilled(false);
		lblRolePatternIco.setBorderPainted(false);
		rightPanel.add(lblRolePatternIco);
		cbxRolePattern = new JCheckBox(
				RolePattern.getPatternInfo().getAcronym() + ": " + RolePattern.getPatternInfo().getName());
		cbxRolePattern.setBounds(33, 139, 230, 20);
		cbxRolePattern.setPreferredSize(new Dimension(230, 20));
		cbxRolePattern.setBackground(UIManager.getColor("Panel.background"));
		rightPanel.add(cbxRolePattern);
		lblRolePatternRes = new JLabel("");
		lblRolePatternRes.setBounds(240, 139, 161, 20);
		lblRolePatternRes.setPreferredSize(new Dimension(110, 20));
		lblRolePatternRes.setForeground(Color.BLUE);
		rightPanel.add(lblRolePatternRes);
		cbxParthoodStructurePattern = new JCheckBox(ParthoodStructurePattern.getPatternInfo().getAcronym() + ": "
				+ ParthoodStructurePattern.getPatternInfo().getName());
		cbxParthoodStructurePattern.setBounds(33, 115, 302, 23);
		cbxParthoodStructurePattern.setPreferredSize(new Dimension(230, 20));
		cbxParthoodStructurePattern.setBackground(UIManager.getColor("Panel.background"));
		lblParthoodStructurePatternRes = new JLabel("");
		lblParthoodStructurePatternRes.setPreferredSize(new Dimension(110, 20));
		lblParthoodStructurePatternRes.setForeground(Color.BLUE);

		cbxRelatorPattern = new JCheckBox(
				RelatorPattern.getPatternInfo().getAcronym() + ": " + RelatorPattern.getPatternInfo().getName());
		cbxRelatorPattern.setBounds(33, 192, 230, 20);
		cbxRelatorPattern.setPreferredSize(new Dimension(225, 20));
		cbxRelatorPattern.setBackground(UIManager.getColor("Panel.background"));
		lblRelatorPatternIco = new JButton();
		lblRelatorPatternIco.setBounds(33, 95, -21, -18);
		lblRelatorPatternIco.setPreferredSize(new Dimension(20, 20));
		lblRelatorPatternIco.setOpaque(false);
		lblRelatorPatternIco.setContentAreaFilled(false);
		lblRelatorPatternIco.setBorderPainted(false);
		lblNonSortalPatternRes = new JLabel("");
		lblNonSortalPatternRes.setBounds(299, 161, 93, 20);
		lblNonSortalPatternRes.setPreferredSize(new Dimension(115, 20));
		lblNonSortalPatternRes.setForeground(Color.BLUE);

		cbxModePattern = new JCheckBox(
				ModePattern.getPatternInfo().getAcronym() + ": " + ModePattern.getPatternInfo().getName());
		cbxModePattern.setBounds(33, 204, 303, 20);
		cbxModePattern.setPreferredSize(new Dimension(225, 20));
		cbxModePattern.setBackground(UIManager.getColor("Panel.background"));
		lblModePatternRes = new JLabel("");
		lblModePatternRes.setBounds(250, 207, 142, 17);
		lblModePatternRes.setPreferredSize(new Dimension(115, 20));
		lblModePatternRes.setForeground(Color.BLUE);

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
		cbxList.add(cbxParthoodStructurePattern);
		cbxList.add(cbxRelatorPattern);
		cbxList.add(cbxModePattern);

		lblIcoList.add(lblKindPatternIco);
		lblIcoList.add(lblSubstanceSortalPatternIco);
		lblIcoList.add(lblSubKindPatternIco);
		lblIcoList.add(lblPhasePatternIco);
		lblIcoList.add(lblRolePatternIco);
		lblIcoList.add(lblRelatorPatternIco);

		lblResultList.add(lblKindPatternRes);
		lblResultList.add(lblSubstanceSortalPatternRes);
		lblResultList.add(lblPhasePatternRes);
		lblResultList.add(lblRolePatternRes);
		lblResultList.add(lblParthoodStructurePatternRes);
		lblResultList.add(lblNonSortalPatternRes);
		lblResultList.add(lblModePatternRes);

		contentPanel.setLayout(null);
		contentPanel.add(lblChooseWhichPattern);
		contentPanel.add(panel);
		contentPanel.add(leftPanel);
		leftPanel.setLayout(null);
		lblNonSortalPatternIco = new JButton();
		lblNonSortalPatternIco.setBounds(28, 179, -21, -17);
		lblNonSortalPatternIco.setPreferredSize(new Dimension(20, 20));
		lblNonSortalPatternIco.setOpaque(false);
		lblNonSortalPatternIco.setContentAreaFilled(false);
		lblNonSortalPatternIco.setBorderPainted(false);
		lblIcoList.add(lblNonSortalPatternIco);

		leftPanel.add(lblNonSortalPatternIco);
		leftPanel.add(lblSubstanceSortalPatternIco);
		leftPanel.add(lblKindPatternIco);

		lblParthoodStructurePatternIco = new JButton();
		lblParthoodStructurePatternIco.setBounds(6, 122, 20, 17);
		lblParthoodStructurePatternIco.setPreferredSize(new Dimension(20, 20));
		lblParthoodStructurePatternIco.setOpaque(false);
		lblParthoodStructurePatternIco.setContentAreaFilled(false);
		lblParthoodStructurePatternIco.setBorderPainted(false);
		lblIcoList.add(lblParthoodStructurePatternIco);
		leftPanel.add(lblParthoodStructurePatternIco);
		lblModePatternIco = new JButton();
		lblModePatternIco.setBounds(36, 221, -29, -17);
		lblModePatternIco.setPreferredSize(new Dimension(20, 20));
		lblModePatternIco.setOpaque(false);
		lblModePatternIco.setContentAreaFilled(false);
		lblModePatternIco.setBorderPainted(false);
		lblIcoList.add(lblModePatternIco);

		leftPanel.add(lblModePatternIco);
		leftPanel.add(cbxSubstanceSortalPattern);
		leftPanel.add(cbxKindPattern);
		leftPanel.add(cbxParthoodStructurePattern);

		cbxNonSortalPattern = new JCheckBox(
				NonSortalPattern.getPatternInfo().getAcronym() + ": " + NonSortalPattern.getPatternInfo().getName());
		cbxNonSortalPattern.setBounds(33, 161, 303, 20);
		cbxNonSortalPattern.setPreferredSize(new Dimension(225, 20));
		cbxNonSortalPattern.setBackground(UIManager.getColor("Panel.background"));
		cbxList.add(cbxNonSortalPattern);
		leftPanel.add(cbxNonSortalPattern);
		leftPanel.add(lblSubstanceSortalPatternRes);
		leftPanel.add(lblKindPatternRes);
		contentPanel.add(rightPanel);
		contentPanel.add(panel_1);

		rightPanel.add(lblRelatorPatternIco);
		rightPanel.add(cbxRelatorPattern);
		lblSubKindPatternRes = new JLabel("");
		lblSubKindPatternRes.setBounds(276, 33, 98, 20);
		rightPanel.add(lblSubKindPatternRes);
		lblSubKindPatternRes.setPreferredSize(new Dimension(110, 20));
		lblSubKindPatternRes.setForeground(Color.BLUE);
		lblResultList.add(lblSubKindPatternRes);
		leftPanel.add(lblNonSortalPatternRes);
		leftPanel.add(cbxModePattern);
		leftPanel.add(lblModePatternRes);

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

	private void executePattern(PatternTask task, Pattern<?> patternRecognition, PatternInfo info, JLabel label,
			JCheckBox checkBox, int incrementalValue, CountDownLatch latch, ExecutorService executor,
			CountDownLatch preLatch) {

		task = new PatternTask(patternRecognition, info, label, checkBox, progressBar, progressBarDescr,
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
			ParthoodStructurePattern parthoodStructurePattern = new ParthoodStructurePattern(parser);
			RelatorPattern relatorPattern = new RelatorPattern(parser);
			NonSortalPattern nonSortalPattern = new NonSortalPattern(parser);
			ModePattern modePattern = new ModePattern(parser);

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

			if (parthoodStructurePatternIsSelected())
				executePattern(ParthoodStructurePatternTask, parthoodStructurePattern,
						ParthoodStructurePattern.getPatternInfo(), lblParthoodStructurePatternRes,
						cbxParthoodStructurePattern, incrementalValue, latch, executor, preLatch);

			if (relatorPatternIsSelected())
				executePattern(RelatorPatternTask, relatorPattern, RelatorPattern.getPatternInfo(),
						lblRelatorPatternRes, cbxRelatorPattern, incrementalValue, latch, executor, preLatch);

			if (nonSortalPatternIsSelected())
				executePattern(NonSortalPatternTask, nonSortalPattern, NonSortalPattern.getPatternInfo(),
						lblNonSortalPatternRes, cbxNonSortalPattern, incrementalValue, latch, executor, preLatch);

			if (modePatternIsSelected())
				executePattern(ModePatternTask, modePattern, ModePattern.getPatternInfo(), lblModePatternRes,
						cbxModePattern, incrementalValue, latch, executor, preLatch);

			patternRecognitionList = new PatternList(kindPattern, substanceSortalPattern, subKindPattern, phasePattern,
					rolePattern, parthoodStructurePattern, relatorPattern, nonSortalPattern, modePattern);

			transferResult(patternRecognitionList);

			new Supervisor(latch).execute();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Pattern Search", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
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

	/**
	 * Check if Pattern is selected.
	 */

	public Boolean kindPatternIsSelected() {
		return cbxKindPattern.isSelected();
	}

	/** open the result which in turn can call the wizards */
	public void openResult(PatternList list, Display display) {
		PatternResultDialog.openDialog(list, frame, display);
	}

	public Boolean parthoodStructurePatternIsSelected() {
		return cbxParthoodStructurePattern.isSelected();
	}

	public Boolean phasePatternIsSelected() {
		return cbxPhasePattern.isSelected();
	}

	public Boolean relatorPatternIsSelected() {
		return cbxRelatorPattern.isSelected();
	}

	public Boolean rolePatternIsSelected() {
		return cbxRolePattern.isSelected();
	}

	public Boolean nonSortalPatternIsSelected() {
		return cbxNonSortalPattern.isSelected();
	}

	public Boolean modePatternIsSelected() {
		return cbxModePattern.isSelected();
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

	public Boolean subKindPatternIsSelected() {
		return cbxSubKindPattern.isSelected();
	}

	public Boolean substanceSortalPatternIsSelected() {
		return cbxSubstanceSortalPattern.isSelected();
	}

	/** transfer result of search to an application */
	public void transferResult(PatternList list) {
		ProjectUIController.get().getProject().setPatterns(list);
	}

	private void updateStatus(String s) {
		progressBarDescr.setText(s);
		System.out.println(s);
	}

}
