package javaTemp;

import java.util.ArrayList;

/*
 * 经典bug总结:
 * 1.int型即时转化为float型
 * 2.新的ArrayList要初始化，不要直接引用已有的ArrayList，这样在改变新的ArrayList会将原始的ArrayList改变，引起不必要麻烦
 * 3.+1-1一定要看清楚
 * 4.自定义矩阵函数的起始位置和终止位置要注意写对
 * 5.java静态方法不能重载
 * 感想：
 * 妈蛋实验室写过的程序一定要集成啊，每次都造轮子谁受得了
 * 开发的时候就要考虑到最后的使用平台，不要到最后再找人翻译，别人重新学习你的逻辑思路然后翻译然后调bug浪费多少人力成本
 * 以后我写过的程序会放在这里https://github.com/fatfishZhao/
 * 先休息了
 * 2016.12.16
 */


public class MousefeaGet {
	/*
	 * 取消HumanNamesChosen参数，传入参数modetype已经没有用了
	 */
	public static ArrayList<ArrayList<Float>> HumanSectionData(String modetype,String FilePath){
		ArrayList<ArrayList<ArrayList<String>>> AllUsers = FileOp.ReadDataFromTextFile(FilePath);
		ArrayList<ArrayList<String>> HumanData = new ArrayList<ArrayList<String>>();
		//降维操作
		int firstLength = AllUsers.size();
		for(int i=0;i<firstLength;i++){
			int secondLength = AllUsers.get(i).size();
			for(int j=0;j<secondLength;j++){
				HumanData.add(AllUsers.get(i).get(j));
			}
		}
		//对CalFeature.DataFormation 函数进行修改，方法1只返回评分为2的数据
		ArrayList<ArrayList<ArrayList<Integer>>> HumanOperatingChosen = CalFeature.DataFormation(HumanData,1);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> SectionDataHuman = CalFeature.SectionProc(HumanOperatingChosen);
		System.out.println(SectionDataHuman.get(0));
		ArrayList<ArrayList<Float>> SectionData = CalFeaFromSectionData(SectionDataHuman);
		return SectionData;
	}
	
	public static ArrayList<ArrayList<Float>> BotSectionData(String modetype,String FilePathBot){
		ArrayList<ArrayList<ArrayList<String>>> AllBots = FileOp.ReadDataFromTextFile(FilePathBot);
		ArrayList<ArrayList<String>> BotData = new ArrayList<ArrayList<String>>();
		//降维操作
		int firstLength = AllBots.size();
		for(int i=0;i<firstLength;i++){
			int secondLength = AllBots.get(i).size();
			for(int j=0;j<secondLength;j++){
				BotData.add(AllBots.get(i).get(j));
			}
		}
		//对CalFeature.DataFormation 函数进行修改，方法0只返回所有的数据
		ArrayList<ArrayList<ArrayList<Integer>>> BotOperatingChosen = CalFeature.DataFormation(BotData,0);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> SectionDataBot = CalFeature.SectionProc(BotOperatingChosen);
		ArrayList<ArrayList<Float>> SectionData = CalFeaFromSectionData(SectionDataBot);
		return SectionData;
	}
	
	public static ArrayList<ArrayList<Float>> CalFeaFromSectionData(ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> DataProc){
		ArrayList<ArrayList<Float>> featureHuman = new ArrayList<ArrayList<Float>>();
		
		int firstLength = DataProc.size();
		for (int i=0;i<firstLength;i++){
			ArrayList<Float> RatioStraiIndividual = new ArrayList<Float>();
			ArrayList<Float> MoveDistanceIndividual = new ArrayList<Float>();
			ArrayList<Float> MoveEfficiencyIndividual = new ArrayList<Float>();
			ArrayList<Float> MoveTimeIndividual = new ArrayList<Float>();
			ArrayList<Float> tmpMov = new ArrayList<Float>();
			ArrayList<Float> VeloSeries = new ArrayList<Float>();
			ArrayList<Float> Vvari = new ArrayList<Float>();
			ArrayList<ArrayList<Float>> VelocityVarIndividual = new ArrayList<ArrayList<Float>>();
			ArrayList<Float> AccSeries = new ArrayList<Float>();
			ArrayList<Float> Accvari = new ArrayList<Float>();
			ArrayList<Float> AccVarIndividual = new ArrayList<Float>();
			ArrayList<Float> WholeDuationIndividual = new ArrayList<Float>();
			ArrayList<Float> tmpList = new ArrayList<Float>();
			ArrayList<Float> Theta = new ArrayList<Float>();
			ArrayList<Float> ThetaIndividual = new ArrayList<Float>();
			ArrayList<Float> Omiga = new ArrayList<Float>();
			ArrayList<Float> OmigaIndividual = new ArrayList<Float>();
			int secondLength = DataProc.get(i).size();
			for(int j=0;j<secondLength;j++){
				
				ArrayList<ArrayList<Integer>> SingleSectionData = DataProc.get(i).get(j);
			
				ArrayList<Float> CurV = CalFeature.CalCurvature(SingleSectionData);
				float RBT = CalFeature.RatioBelowTh(CurV, (float)-0.95);
				if(RBT==0){}
				else RatioStraiIndividual.add(RBT);
				tmpMov = CalFeature.CalMovementEfficiency(SingleSectionData);
				if(tmpMov.isEmpty()){}
				else{
					MoveDistanceIndividual.add(tmpMov.get(0));
					MoveEfficiencyIndividual.add(tmpMov.get(1));
					MoveTimeIndividual.add(tmpMov.get(2));
				}
				VeloSeries = CalFeature.GetVeloSeries(SingleSectionData);
				Vvari = CalFeature.CalVeloVariation(VeloSeries);
				if(Vvari.isEmpty()){}
				else{
					VelocityVarIndividual.add(Vvari);
				}
				AccSeries = CalFeature.CalAcelaration(SingleSectionData);
				Accvari = CalFeature.CalAccVariation(AccSeries);
				if(Accvari.isEmpty()){}
				else
					AccVarIndividual.addAll(Accvari);
				float WholeTime = CalFeature.CalWholeDuation(SingleSectionData);
				WholeDuationIndividual.add(WholeTime);
				Theta = CalFeature.CalTheta(SingleSectionData);
				if(Theta.size()==0){}
				else
					ThetaIndividual.add(numJv.Mean(Theta, 0, Theta.size()));
				Omiga = CalFeature.CalOmiga(SingleSectionData, Theta);
				if(Omiga.size()==0){}
				else
					OmigaIndividual.add(numJv.Mean(Omiga, 0, Omiga.size()));
			}
			
			//这里插入方式与原始py代码不一样，原代码是“竖向”，这里是”横向“，因为单个样本是横向的，横向插入用的时候更自然（ArrayList不能像py矩阵一样双向插入提取）
			tmpList.add(numJv.Mean(RatioStraiIndividual,0,RatioStraiIndividual.size()));			//1
			tmpList.addAll(numJv.Mean2d(VelocityVarIndividual,1));									//2,3
			tmpList.add(numJv.Mean(MoveEfficiencyIndividual,0,MoveEfficiencyIndividual.size()));	//4
			tmpList.add(numJv.Mean(MoveTimeIndividual,0,MoveTimeIndividual.size()));				//5
			tmpList.add(numJv.Sum1dFloat(WholeDuationIndividual));									//6
			tmpList.add(numJv.Std(MoveEfficiencyIndividual,0,MoveEfficiencyIndividual.size()));		//7
			tmpList.add(numJv.Mean(AccVarIndividual,0,AccVarIndividual.size()));					//8
			tmpList.add(numJv.Mean(ThetaIndividual, 0, ThetaIndividual.size()));					//9
			tmpList.add(numJv.Mean(OmigaIndividual, 0, OmigaIndividual.size()));					//10
			featureHuman.add(tmpList);
		}
		return featureHuman;
		
	}
}
