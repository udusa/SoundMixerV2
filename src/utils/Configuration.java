package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Configuration {

	private ArrayList<File> wordsFileList, noiseFileList;
	private int initDb, initSNR, upDownSig;
	private boolean isRandom;
	private double[] SNR_levels;
	private String testerName, testedName;
	private final String PARTICIPATES_HEADER = "Tester Name:,Tested Name:";
	private final String RECORD_HEADER = "Sign,SNR,NoiseTimestamp,Signaltimestamp,NoiseDb,SignalDb,Noise File,Sound File";

	public Configuration(File file) throws WrongFileExeption {

		wordsFileList = new ArrayList<>();
		noiseFileList = new ArrayList<>();

		readData(file);

	}

	private void readData(File file) throws WrongFileExeption {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			br.readLine();
			br.readLine();
			String line = br.readLine();
			while (line != null) {
				String[] data = line.split(",");
				data = Arrays.copyOfRange(data, 2, data.length);
				String header = data[0];
				if (header.equals(Consts.Init_Sig_dB)) {
					initDb = Integer.parseInt(data[1]);
				} else if (header.equals(Consts.Init_SNR_dB)) {
					initSNR = Integer.parseInt(data[1]);
				} else if (header.equals(Consts.Up_Down_Sig)) {
					upDownSig = Integer.parseInt(data[1]);
				} else if (header.equals(Consts.Noise_Files)) {
					int count = Integer.parseInt(data[1]);
					noiseFileList = getSoundFiles(count, Arrays.copyOfRange(data, 2, data.length));
				} else if (header.equals(Consts.List_of_Words_Files)) {
					int count = Integer.parseInt(data[1]);
					wordsFileList = getSoundFiles(count, Arrays.copyOfRange(data, 2, data.length));
				} else if (header.equals(Consts.Order_of_lists)) {
					isRandom = Integer.parseInt(data[1]) == 0;
					if (isRandom) {
						wordsFileList = shuffle(wordsFileList);
						noiseFileList = shuffle(noiseFileList);
					}
				} else if (header.equals(Consts.Levels_of_SNR)) {
					int size = Integer.parseInt(data[1]);
					SNR_levels = new double[size];
					for (int i = 0; i < SNR_levels.length; i++) {
						SNR_levels[i] = Double.parseDouble(data[2 + i]);
					}
				} else if (header.equals(Consts.Tester_name)) {
					testerName = data[1];
				} else if (header.equals(Consts.Tested_name)) {
					testedName = data[1];
				}
				// System.out.println(header);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new WrongFileExeption();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private ArrayList<File> getSoundFiles(int amount, String[] names) {
		// String workingDir = System.getProperty("user.dir");
		// System.out.println("Current working directory : " + workingDir);
		ArrayList<File> list = new ArrayList<>(amount);
		for (int i = 0; i < amount; i++) {
			String fileName = names[i];
			File soundFile = new File(Consts.SOUND_PATH_PREFIX + fileName);
			list.add(soundFile);
		}
		return list;
	}

	private ArrayList<File> shuffle(ArrayList<File> list) {
		Collections.shuffle(list);
		return list;
	}

	public ArrayList<File> getNoiseFiles() {
		// TODO Auto-generated method stub
		return noiseFileList;
	}

	public ArrayList<File> getWordsFiles() {
		// TODO Auto-generated method stub
		return wordsFileList;
	}

	public double[] getSNR_levels() {
		return SNR_levels;
	}

	public void saveRecord(File file, List<Record> records) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(PARTICIPATES_HEADER+"\n");
			bw.write(testerName+","+testedName+"\n");
			bw.write(RECORD_HEADER +"\n");
			for(Record r : records){
				bw.write(r.toString()+"\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
