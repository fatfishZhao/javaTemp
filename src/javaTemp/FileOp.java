package javaTemp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class FileOp {

	/*
	 * ȡ��NameData����
	 */
	public static ArrayList<ArrayList<ArrayList<String>>> ReadDataFromTextFile(String FilePath){
		
		String[] HumanList = (new File(FilePath)).list();
		ArrayList<ArrayList<ArrayList<String>>> HumanData = new ArrayList<ArrayList<ArrayList<String>>>();
		
		for(int i=0;i<HumanList.length;i++){
			String[] SectionList = (new File(FilePath+"\\"+HumanList[i])).list();
			for (String S : SectionList) {
				String extraStr = "\\"+S;
				String HumanPath = FilePath + "\\" + HumanList[i];
				
				String[] FileList = (new File(HumanPath+extraStr)).list();
				ArrayList<ArrayList<String>> SingleFileData = new ArrayList<ArrayList<String>>();
				for(int j=0;j<FileList.length;j++){
					File f = new File(HumanPath+extraStr+"\\"+FileList[j]);
					BufferedReader reader = null;
					try{
						reader = new BufferedReader(new FileReader(f));
						String tempString = null;
						ArrayList<String> lineList = new ArrayList<String>();
						//���ж�ȡ����
						while((tempString = reader.readLine()) != null) {
							lineList.add(tempString);
						}
						reader.close();
						SingleFileData.add(lineList);
					} catch (IOException e){
						e.printStackTrace();
					} finally {
						if(reader!=null){
							try{
								reader.close();
							} catch (IOException e1){
							}
						}
					}
				}//j��forѭ������
				HumanData.add(SingleFileData);
			}
		}
		return HumanData;
	}
}
