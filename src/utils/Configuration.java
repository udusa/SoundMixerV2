package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Configuration {

	private ArrayList<File> wordsFileList, noiseFileList;
	private int initDb, initSNR, upDownSig;
	private boolean isRandom;

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
				if(header.equals(Consts.Init_Sig_dB)){
					
				}else if(header.equals(Consts.Init_Sig_dB)){
					
				}else if(header.equals(Consts.Init_SNR_dB)){
					
				}else if(header.equals(Consts.Up_Down_Sig)){
					
				}else if(header.equals(Consts.Noise_Files)){
					
				}else if(header.equals(Consts.List_of_Words_Files)){
					
				}else if(header.equals(Consts.Init_Sig_dB)){
					
				}else if(header.equals(Consts.Init_Sig_dB)){
					
				}
				System.out.println(header);
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

}
