package org.usfirst.frc.team2485.robot.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

import org.usfirst.frc.team2485.robot.Robot;

/**
 * Static class to interface IO between the RoboRio and the Driver Station. Used
 * to save constants to a file rather than being hard coded.
 * 
 * @author Ben Clark
 * @author Jeremy McCulloch
 */
public class ConstantsIO {
	public static final String ROBO_RIO_CONSTANTS_FILE_PATH = "/home/lvuser/constants.txt";

	public static HashMap<String, String> data;

	public static double kP_Shooter;
	public static double kI_Shooter;
	public static double kD_Shooter;
	public static double kF_Shooter;
	public static double kShotRPS;
	
	public static double kP_IntakeArm;
	public static double kI_IntakeArm;
	public static double kD_IntakeArm;
	

	public static void init() {


		System.out.println("ConstantsIO .class file loc: " + ConstantsIO.class.getResource("").getPath());

		if (Robot.isSimulation()) {

			String constantsFile = findConstantsFile();

			try {
				data = parseLoadFile(readLocalFile(constantsFile));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				data = parseLoadFile(readLocalFile(ROBO_RIO_CONSTANTS_FILE_PATH));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		//		createUnMatchedConstants();
		kP_Shooter = Double.parseDouble(data.get("kP_Shooter"));
		kI_Shooter = Double.parseDouble(data.get("kI_Shooter"));
		kD_Shooter = Double.parseDouble(data.get("kD_Shooter"));
		kF_Shooter = Double.parseDouble(data.get("kF_Shooter"));
		kShotRPS = Double.parseDouble(data.get("kShotRPS"));
		
		kP_IntakeArm  = Double.parseDouble(data.get("kP_IntakeArm"));
		kI_IntakeArm = Double.parseDouble(data.get("kI_IntakeArm"));
		kD_IntakeArm = Double.parseDouble(data.get("kD_IntakeArm"));

		
	}

	@SuppressWarnings("unused")
	private static void createUnMatchedConstants() {
		Field[] fields = ConstantsIO.class.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			fields[i].getName().startsWith("k");

			if (!data.containsKey(fields[i].getName())) {

			}
		}
	}

	/**
	 * I'm so sorry Jeremy
	 */
	private static String findConstantsFile() {
		File curFolder = new File(ConstantsIO.class.getResource("").getPath());

		while (!curFolder.getName().equals("frc-2017")) {
			curFolder = curFolder.getParentFile();
			System.out.println("Backed out to: " + curFolder.getPath());
		}

		return new File(curFolder, "Constants.txt").getPath().substring(5);
	}

	/**
	 * Used to read a file locally.
	 * 
	 * @param filePath
	 */
	public static String readLocalFile(String filePath) throws IOException {
		File file = new File(filePath);

		System.out.println("Resolved file path: " + file.getPath());

		String fileString;

		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeperator = "\n";

		try {
			while (scanner.hasNextLine())
				fileContents.append(scanner.nextLine() + lineSeperator);
			fileString = fileContents.toString();
			// remove the added "\n"
			fileString = fileString.substring(0, fileString.length() - 1);
		} finally {
			scanner.close();
		}
		return fileString;
	}

	/**
	 * @param loadFileContents
	 * @return HashMap containing constant names and their values as declared in
	 *         the load file.
	 */
	public static HashMap<String, String> parseLoadFile(String fileContents) {

		HashMap<String, String> constantsMap = new HashMap<String, String>();
		Scanner scanner = new Scanner(fileContents);

		while (scanner.hasNextLine()) {

			String currLine = scanner.nextLine().trim();

			if (currLine.contains("=")) {

				String constantName = currLine.substring(0, currLine.indexOf("=")).trim();
				String constantValue = currLine.substring(currLine.indexOf("=") + 1).trim();

				constantsMap.put(constantName, constantValue);
			}
		}
		scanner.close();
		return constantsMap;
	}

	/**
	 * NEEDS TO BE WRITTEN AND DEPLOTED FROM ELSE WHERE: WIDGITS?
	 */
	public static void writeConstantsToRoboRio(String loadFileContents) {

		PrintWriter printWriter = null;

		try {
			printWriter = new PrintWriter(
					new FileOutputStream("ftp://roborio-2485-frc.local" + ROBO_RIO_CONSTANTS_FILE_PATH)); // definitely
			// won't
			// work
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (printWriter != null) {
			printWriter.write(loadFileContents);
			printWriter.flush();
			printWriter.close();
		} else {
			System.err.println("PrintWriting failed to init, unable to write constants.");
		}

	}

}
