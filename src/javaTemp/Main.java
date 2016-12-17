package javaTemp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {


	public static void main(String[] args) {
		String modetype = "login";
		String HumanDataPath = "g:\\work\\HWbot4\\retree\\retree\\"+modetype+"\\";
		//String BotDataPath = "g:\\work\\HWbot4\\logintql\\login\\";
		/*
		String ss="G:/work/HWbot4/logintql/login";
		ArrayList<ArrayList<ArrayList<String>>> result = FileOp.ReadDataFromTextFile("G:\\work\\HWbot4\\retree\\retree\\login\\");
		System.out.println(result.get(0).get(1).get(15));
		System.out.println("hello");*/
		ArrayList<ArrayList<Float>> MouseFeaHuman = MousefeaGet.HumanSectionData(modetype, HumanDataPath);
		System.out.println(MouseFeaHuman.get(0));
		
		
	}
	
	

	public static ArrayList<ArrayList<ArrayList<String>>> getData(String FilePath){

		ArrayList<ArrayList<ArrayList<String>>> res = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> HumanData = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> NameData = new ArrayList<ArrayList<String>>();


		String extraStr = "";
		File dir=new File(FilePath);
		File[] files = dir.listFiles();  
		for(File fOrd : files) {
			
			ArrayList<String> SingleFileData = new ArrayList<String>();
			ArrayList<String> SingleName = new ArrayList<String>();
			
			String fileName=FilePath + extraStr + '\\' + fOrd.getName().toString();
			File file = new File(fileName);
			BufferedReader reader = null;
			try {

				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					SingleFileData.add(tempString);
					SingleName.add(fOrd.getName().toString());
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();				
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}

			HumanData.add(SingleFileData);
			NameData.add(SingleName);

		}

		res.add(HumanData);
		res.add(NameData);


		return res;
	}


}



