package net.menthor.abstractionRecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.eclipse.emf.ecore.resource.Resource;

import RefOntoUML.Model;
import RefOntoUML.Package;
import RefOntoUML.PackageableElement;
import RefOntoUML.RefOntoUMLFactory;
import RefOntoUML.parser.OntoUMLParser;
import RefOntoUML.util.RefOntoUMLResourceUtil;
import net.menthor.abstractionRecognition.nonSortalAbstraction.NonSortalAbstraction;
import net.menthor.abstractionRecognition.relatorAbstraction.RelatorAbstraction;
import net.menthor.common.file.TimeHelper;
import net.menthor.common.ontoumlparser.OntoUMLModelStatistic;
import net.menthor.common.ontoumlparser.OntoUMLModelStatistic.InfoType;
import net.menthor.common.ontoumlparser.OntoUMLModelStatistic.LineType;

public class MultipleModelProcesser {

	private static final String SEPARATOR = ";";
	private static final String DIR = "models/";
	private static final String CSV = ".csv";
	private static final String REFONTO = ".refontouml";

	public static void main(String[] args) {

		System.out.println(TimeHelper.getTime() + " - Started !");

		// breakModelByPackage(DIR, "allModels.refontouml");

		// WorksheetGenerator gen = new WorksheetGenerator(DIR);
		// gen.run();
		// gen.createFile();

		generatePatternCSVFile();

		System.out.println(TimeHelper.getTime() + " - Finished!");

	}

	public static void generatePatternCSVFile() {
		ArrayList<String> fileList = getFileList(DIR);
		PrintWriter fileWriter;

		try {
			fileWriter = new PrintWriter(DIR + "Patterns" + CSV);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		setPatternHeaders(fileWriter);

		for (String fileName : fileList) {
			runPatterns(DIR + fileName, fileWriter);
		}

		fileWriter.close();

	}

	private static void setPatternHeaders(PrintWriter fileWriter) {
		RefOntoUMLFactory factory = RefOntoUMLFactory.eINSTANCE;

		ArrayList<Abstraction<?>> apList = createApList(new OntoUMLParser(factory.createModel()));
		fileWriter.print("Model" + SEPARATOR + " ");
		for (Abstraction<?> ap : apList) {
			fileWriter.print(ap.info().acronym + SEPARATOR + " ");
		}

		fileWriter.println();

	}

	private static void runPatterns(String fileName, PrintWriter fileWriter) {
		OntoUMLParser parser;
		System.out.println(TimeHelper.getTime() + " - " + fileName + ": Loading parser...");

		try {
			parser = new OntoUMLParser(fileName);

		} catch (Exception e) {
			System.out.println(TimeHelper.getTime() + " - " + fileName + ": Parser not loaded!");
			fileWriter.close();
			return;
		}
		ArrayList<Abstraction<?>> apList = createApList(parser);

		fileWriter.print(parser.getModelName() + SEPARATOR + " ");
		for (Abstraction<?> ap : apList) {
			System.out.println(TimeHelper.getTime() + " - " + fileName + ": identifying " + ap.info().acronym + "...");
			ap.identify();
			fileWriter.print(ap.getOccurrences().size() + SEPARATOR + " ");
			System.out.println(TimeHelper.getTime() + " - " + fileName + ": " + ap.getOccurrences().size()
					+ " occurrences of " + ap.info().acronym + " identified!");
		}

		fileWriter.println();
		return;
	}

	private static ArrayList<Abstraction<?>> createApList(OntoUMLParser parser) {
		ArrayList<Abstraction<?>> apList = new ArrayList<Abstraction<?>>();
		apList.add(new RelatorAbstraction(parser));
		apList.add(new NonSortalAbstraction(parser));


		return apList;
	}

	public static void generateModelStructureCSVFiles() {
		ArrayList<String> fileList = getFileList(DIR);

		String fileName;
		OntoUMLParser parser;
		OntoUMLModelStatistic diagnostic;
		if (fileList.size() == 0)
			return;

		ArrayList<PrintWriter> countWriters = new ArrayList<PrintWriter>();
		ArrayList<PrintWriter> percentageWriters = new ArrayList<PrintWriter>();
		ArrayList<PrintWriter> allPercentageWriters = new ArrayList<PrintWriter>();
		ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

		try {

			for (LineType lt : LineType.values()) {
				countWriters.add(new PrintWriter(DIR + lt + "_Count" + CSV));
				percentageWriters.add(new PrintWriter(DIR + lt + "_Percentage" + CSV));
				allPercentageWriters.add(new PrintWriter(DIR + lt + "_AllPercentage" + CSV));
			}

			writers.addAll(countWriters);
			writers.addAll(percentageWriters);
			writers.addAll(allPercentageWriters);

		} catch (Exception e) {
			System.out.println("Failed to create file!");
			closeAllWriters(writers);
			return;
		}

		fileName = DIR + fileList.get(0);
		System.out.println(TimeHelper.getTime() + " - Loading: '" + DIR + fileName + "'");

		try {
			parser = new OntoUMLParser(fileName);
		} catch (Exception e) {
			System.out.println(TimeHelper.getTime() + " - Loading: failed");
			closeAllWriters(writers);
			return;
		}

		diagnostic = new OntoUMLModelStatistic(parser);
		System.out.println(TimeHelper.getTime() + " - Diagnostic: running for model '" + DIR + fileName + "'...");
		diagnostic.run();
		System.out.println(TimeHelper.getTime() + " - Diagnostic: Setting headers...");
		setHeaders(diagnostic, countWriters, percentageWriters, allPercentageWriters);
		System.out.println(
				TimeHelper.getTime() + " - Diagnostic: writing CSV line for model '" + DIR + fileName + "'...");
		setLines(diagnostic, countWriters, percentageWriters, allPercentageWriters);

		for (int i = 1; i < fileList.size(); i++) {
			fileName = fileList.get(i);
			System.out.println(TimeHelper.getTime() + " - Loading: '" + DIR + fileName + "'");
			try {
				parser = new OntoUMLParser(DIR + fileName);
			} catch (IOException e) {
				System.out.println(TimeHelper.getTime() + " - Loading: failed");
				continue;
			}

			diagnostic = new OntoUMLModelStatistic(parser);
			System.out.println(TimeHelper.getTime() + " - Diagnostic: running for model '" + DIR + fileName + "'...");
			diagnostic.run();
			System.out.println(
					TimeHelper.getTime() + " - Diagnostic: writing CSV line for model '" + DIR + fileName + "'...");
			setLines(diagnostic, countWriters, percentageWriters, allPercentageWriters);

		}
		System.out.println(TimeHelper.getTime() + " - Closing files...");
		closeAllWriters(writers);
	}

	private static void setLines(OntoUMLModelStatistic diagnostic, ArrayList<PrintWriter> countWriters,
			ArrayList<PrintWriter> percentageWriters, ArrayList<PrintWriter> allPercentageWriters) {

		for (int i = 0; i < LineType.values().length; i++) {
			countWriters.get(i).println(diagnostic.getCSVLine(LineType.values()[i], InfoType.COUNT, ";", true));
			percentageWriters.get(i)
					.println(diagnostic.getCSVLine(LineType.values()[i], InfoType.TYPE_PERCENTAGE, ";", true));
			allPercentageWriters.get(i)
					.println(diagnostic.getCSVLine(LineType.values()[i], InfoType.ALL_PERCENTAGE, ";", true));
		}

	}

	private static void setHeaders(OntoUMLModelStatistic diagnostic, ArrayList<PrintWriter> countWriters,
			ArrayList<PrintWriter> percentageWriters, ArrayList<PrintWriter> allPercentageWriters) {

		for (int i = 0; i < LineType.values().length; i++) {
			countWriters.get(i).println(diagnostic.getCSVLine(LineType.values()[i], InfoType.MEASURE, ";", true));
			percentageWriters.get(i).println(diagnostic.getCSVLine(LineType.values()[i], InfoType.MEASURE, ";", true));
			allPercentageWriters.get(i)
					.println(diagnostic.getCSVLine(LineType.values()[i], InfoType.MEASURE, ";", true));
		}

	}

	private static void closeAllWriters(ArrayList<PrintWriter> writers) {

		for (PrintWriter w : writers) {
			if (w != null)
				w.close();
		}

	}

	public static ArrayList<String> getFileList(String directory) {
		ArrayList<String> textFiles = new ArrayList<String>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((REFONTO)))
				textFiles.add(file.getName());
		}

		return textFiles;
	}

	public static void breakModelByPackage(String folderPath, String fileName) {
		Resource resource;
		try {
			String path = folderPath + fileName;
			System.out.println(TimeHelper.getTime() + " - Loading: '" + path + "'");
			resource = RefOntoUMLResourceUtil.loadModel(path);
			System.out.println(TimeHelper.getTime() + " - Loading: successful!");

		} catch (IOException e) {
			System.out.println(TimeHelper.getTime() + " - Loading: failed!");
			return;
		}

		@SuppressWarnings("unused")
		RefOntoUMLFactory factory = RefOntoUMLFactory.eINSTANCE;
		Model m = (Model) resource.getContents().get(0);

		breakIntoFiles(m, folderPath, m.getName(), 0);

	}

	public static void breakIntoFiles(Package m, String folderPath, String modelPath, int depth) {

		if (depth >= 6)
			return;

		for (PackageableElement pe : m.getPackagedElement()) {
			if (pe instanceof Package) {
				modelPath += pe.getName();

				String fileName = folderPath + pe.getName() + REFONTO;

				System.out.println(TimeHelper.getTime() + " - Saving: " + fileName);
				RefOntoUMLResourceUtil.saveModel(fileName, (Package) pe);
				breakIntoFiles((Package) pe, folderPath, modelPath, depth + 1);
			}
		}

	}

}
