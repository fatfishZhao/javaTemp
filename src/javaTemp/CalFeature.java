package javaTemp;

import java.util.ArrayList;



public class CalFeature {
	
	/*
	 * 只返回一个参数：评分为2的原始数据
	 * method参数如果是0就返回所有评级的数据，如果是1就返回评级为2的数据
	 */
	public static ArrayList<ArrayList<ArrayList<Integer>>> DataFormation(ArrayList<ArrayList<String>> AllData,int method){
		ArrayList<ArrayList<ArrayList<Integer>>> RawData = new ArrayList<ArrayList<ArrayList<Integer>>>();
		int firstLength = AllData.size();
		for(int i=0;i<firstLength;i++){
			ArrayList<String> linesAll = AllData.get(i);
			ArrayList<String> lines = new ArrayList<String>();
			int secondLength = linesAll.size();
			for(int lineData=0;lineData<secondLength;lineData++){
				String[] tmp = linesAll.get(lineData).split("\\s+");
				if(tmp.length!=0 &&(tmp[0].equals("login")||tmp[0].equals("register")))
					lines.add(linesAll.get(lineData));
			}
			String[] tmp = lines.get(lines.size()-1).split("\\s+");
			if((tmp[1].equals("keydown")||tmp[1].equals("keyDown"))&&tmp[2].equals("13"))
				lines.remove(lines.size()-1);
			tmp = lines.get(0).split("\\s+");
			if((tmp[1].equals("keyup")||tmp[1].equals("keyUp"))&&tmp[2].equals("13"))
				lines.remove(0);
			ArrayList<ArrayList<Integer>> txtcont = new ArrayList<ArrayList<Integer>>();
			//源py代码在后面计算鼠标和键盘的次数信息，本程序直接在接下来的for循环进行统计
			int Dcnt=0,Ucnt=0,Mcnt=0,KeyDcnt=0,KeyUcnt=0;
			secondLength = lines.size();
			int timeFirst=0;
			for(int line=0;line<secondLength;line++){
				//temptxt是构造txtcont的暂时单一变量
				ArrayList<Integer> temptxt = new ArrayList<Integer>();
				tmp = lines.get(line).split("\\s+");
				
				if(line==0)
					timeFirst = Integer.parseInt(tmp[tmp.length-1].substring(tmp[tmp.length-1].length()-8));
				boolean keyFg = false;
				if(tmp[1].equals("mousedown")){
					temptxt.add(0);
					Dcnt++;
				}
				else if (tmp[1].equals("mouseup")){
					temptxt.add(1);
					Ucnt++;
				}
				else if (tmp[1].equals("mousemove")){
					temptxt.add(2);
					Mcnt++;
				}
				else if (tmp[1].equals("keyUp")||tmp[1].equals("keyup")){
					temptxt.add(4);
					KeyUcnt++;
					keyFg = true;
				}
				else if (tmp[1].equals("keydown")){
					temptxt.add(3);
					KeyDcnt++;
					keyFg =true;
				}
				if(keyFg){
					temptxt.add(Integer.parseInt(tmp[2]));
					temptxt.add(0);
					temptxt.add(Integer.parseInt(tmp[3].substring(tmp[3].length()-8))-timeFirst);
				}else{
					temptxt.add(Integer.parseInt(tmp[2]));
					temptxt.add(Integer.parseInt(tmp[3]));
					temptxt.add(Integer.parseInt(tmp[4].substring(tmp[4].length()-8))-timeFirst);
				}
				txtcont.add(temptxt);
			}
			if(method==1){
				if (Dcnt==0 ||Ucnt==0){}
				else if (Dcnt!=Ucnt){}
				else if (Mcnt<6){}
				else if (KeyDcnt!=KeyUcnt){}
				else RawData.add(txtcont);
			}
			if(method==0)
				RawData.add(txtcont);
			
		}
		return RawData;
	}
	/*
	 * 将三层数据拆解为4层
	 */
	public static ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> SectionProc(ArrayList<ArrayList<ArrayList<Integer>>> FormatData){
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> SectionRawData = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
		int firstLength = FormatData.size();
		for(int SampleId=0;SampleId<firstLength;SampleId++){
			ArrayList<ArrayList<ArrayList<Integer>>> SingleSampleRawData = new ArrayList<ArrayList<ArrayList<Integer>>>();
			ArrayList<ArrayList<Integer>> thisSampleData = new ArrayList<ArrayList<Integer>>();
			thisSampleData = FormatData.get(SampleId);
			ArrayList<Integer> MouseUpIdx = new ArrayList<Integer>();
			int secondLength = thisSampleData.size();
			for(int i=0;i<secondLength;i++){
				if(thisSampleData.get(i).get(0)==1)
					MouseUpIdx.add(i);
			}
			if(MouseUpIdx.isEmpty())
				SingleSampleRawData.add(thisSampleData);
			else{
				ArrayList<Integer> SecIdx = new ArrayList<Integer>();
				SecIdx.add(-1);
				SecIdx.addAll(MouseUpIdx);
				if(SecIdx.get(SecIdx.size()-1)<thisSampleData.size()-1)
					SecIdx.add(thisSampleData.size()-1);
				secondLength = SecIdx.size();
				for(int kI=1;kI<secondLength;kI++){
					if(SecIdx.get(kI)-SecIdx.get(kI-1)>4){
						ArrayList<ArrayList<Integer>> MovingDataSec = new  ArrayList<ArrayList<Integer>>();
						MovingDataSec = new ArrayList<ArrayList<Integer>> (thisSampleData.subList(SecIdx.get(kI-1)+1, SecIdx.get(kI)+1));
						SingleSampleRawData.add(MovingDataSec);
					}
				}
			}
			SectionRawData.add(SingleSampleRawData);
		}
		return SectionRawData;
	}
	
	public static ArrayList<Float> CalCurvature(ArrayList<ArrayList<Integer>> Txtdata){
		ArrayList<ArrayList<Integer>> TracePos = new ArrayList<ArrayList<Integer>> ();
		int firstLength = Txtdata.size();
		for(int i=0;i<firstLength;i++){
			if(Txtdata.get(i).get(0)==2)
				TracePos.add(Txtdata.get(i));
		}
		ArrayList<Float> CurvMtr = new ArrayList<Float>();
		if(TracePos.size()<3)
			return CurvMtr;
		else{
			ArrayList<ArrayList<Integer>> VecCur = numJv.MtrMinus2d(TracePos,TracePos,1,0,TracePos.size()-1,1,1,2);
			firstLength = VecCur.size()-1;
			for(int i=0;i<firstLength;i++){
				float tmpInnerPr = -numJv.DotPro1d(VecCur.get(i), VecCur.get(i+1));
				ArrayList<Integer> tmpVecCuri = new ArrayList<Integer>();
				ArrayList<Integer> tmpVecCurip1 = new ArrayList<Integer>();
				tmpVecCuri.addAll(VecCur.get(i)); tmpVecCurip1.addAll(VecCur.get(i+1)); 
				int secondLength = tmpVecCuri.size();
				for(int j=0;j<secondLength;j++){
					tmpVecCuri.set(j, (int)Math.pow(tmpVecCuri.get(j), 2));
					tmpVecCurip1.set(j, (int)Math.pow(tmpVecCurip1.get(j), 2));
				}
				float tmpVecMod=0;
				tmpVecMod = (float)Math.sqrt(numJv.Sum1dInt(tmpVecCuri))*(float)Math.sqrt(numJv.Sum1dInt(tmpVecCurip1));
				tmpVecMod=(tmpVecMod==0)?1:tmpVecMod;
				CurvMtr.add(tmpInnerPr/tmpVecMod);
			}
		}
		return CurvMtr;
	}
	
	public static float RatioBelowTh(ArrayList<Float> Vec,float th){
		float RBT;
		if(Vec.size()==0)
			RBT=0;
		else{
			int sum=0;
			for(float f:Vec){
				if(f<th)
					sum++;
			}
			RBT =  (float)sum/(float)Vec.size();
		}
		return RBT;
	}
	
	public static ArrayList<Float> CalMovementEfficiency(ArrayList<ArrayList<Integer>> Txtdata){
		ArrayList<ArrayList<Integer>> TracePos = new ArrayList<ArrayList<Integer>> ();
		ArrayList<Float> backValue = new ArrayList<Float>();
		int firstLength = Txtdata.size();
		for(int i=0;i<firstLength;i++){
			if(Txtdata.get(i).get(0)==2)
				TracePos.add(Txtdata.get(i));
		}
		//原始py代码中将返回值全部设置为'none'，由于java类型不允许返回变化的类型，所以直接返回空list
		float Time,Caldistance,Caldisplacement,MoveEfficiency;
		ArrayList<ArrayList<Integer>> VecCur = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tmpVecCur = new ArrayList<ArrayList<Integer>>();
		ArrayList<Float> distance = new ArrayList<Float>();
		ArrayList<ArrayList<Integer>> displacement= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> aTemp = new ArrayList<Integer>();
		if(TracePos.size()<2)
			return backValue;
		else{
			Time = (float)(TracePos.get(TracePos.size()-1).get(3)-TracePos.get(0).get(3));
			tmpVecCur = numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 1, 1, 2);
			VecCur = numJv.MtrPow(tmpVecCur, 2);
			distance = numJv.MtrSqrt(numJv.Sum2dInt(VecCur, 1));
			displacement = numJv.MtrPow(numJv.MtrMinus2d(TracePos, TracePos, TracePos.size()-1, 0, 1, 1, 1, 2), 2);
			aTemp = displacement.get(0);
			Caldisplacement = (float) Math.sqrt(numJv.Sum1dInt(aTemp));
			Caldistance = numJv.Sum1dFloat(distance);
			if(Caldistance==0)
				return backValue;
			else
				MoveEfficiency = Caldisplacement/Caldistance;
		}
		backValue.add(Caldistance);
		backValue.add(MoveEfficiency);
		backValue.add(Time);
		return backValue;
	}
	public static ArrayList<Float> GetVeloSeries(ArrayList<ArrayList<Integer>> Txtdata){
		ArrayList<ArrayList<Integer>> TracePos = new ArrayList<ArrayList<Integer>> ();
		//原始py代码中将返回值设置为'none'，由于java类型不允许返回变化的类型，所以直接返回空list
		ArrayList<Float> VeSer = new ArrayList<Float>();
		int firstLength = Txtdata.size();
		ArrayList<Float> DistSeries,TimeSeries;
		for(int i=0;i<firstLength;i++){
			if(Txtdata.get(i).get(0)<3)
				TracePos.add(Txtdata.get(i));
		}
		if(TracePos.size()<3) return VeSer;
		else{
			DistSeries = numJv.MtrSqrt(numJv.Sum2dInt(numJv.MtrPow(numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 1, 1, 2), 2), 1));
			TimeSeries = numJv.Sum2dInttoFloat(numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 3, 3, 1),1);
			firstLength = TimeSeries.size();
			for(int i=0;i<firstLength;i++){
				if(TimeSeries.get(i)==0) TimeSeries.set(i, (float) 1);
			}
			VeSer = numJv.MtrDivide(DistSeries, TimeSeries);
		}
		return VeSer;
	}
	public static ArrayList<Float> CalVeloVariation(ArrayList<Float> VeloVec){
		ArrayList<Float> Varia = new ArrayList<Float>();
		if(VeloVec.isEmpty()) return Varia;
		else{
			int halfPos = VeloVec.size()/2;
			if(numJv.Mean(VeloVec, 0, halfPos)==0)
				Varia.add((float)0);
			else 
				Varia.add(numJv.Std(VeloVec, 0, halfPos)/numJv.Mean(VeloVec, 0, halfPos));
			if(numJv.Mean(VeloVec, halfPos, VeloVec.size())==0)
				Varia.add((float)0);
			else {
				//Varia.add(numJv.Std(VeloVec, halfPos, VeloVec.size())/numJv.Mean(VeloVec, halfPos, VeloVec.size()));
				float std = numJv.Std(VeloVec, halfPos, VeloVec.size());
				float mean = numJv.Mean(VeloVec, halfPos, VeloVec.size());
				float tmp = std/mean;
				Varia.add(tmp);
			}
		}
		return Varia;
	}
	public static ArrayList<Float> CalAcelaration(ArrayList<ArrayList<Integer>> Txtdata){
		ArrayList<ArrayList<Integer>> TracePos = new ArrayList<ArrayList<Integer>> ();
		ArrayList<Float> VeSer = new ArrayList<Float>();
		ArrayList<Float> VeSeries = new ArrayList<Float>();
		int firstLength = Txtdata.size();
		ArrayList<Float> AccSer = new ArrayList<Float>();
		ArrayList<Float> DistSeries,TimeSeries,TTimeSeries;
		for(int i=0;i<firstLength;i++){
			if(Txtdata.get(i).get(0)<3)
				TracePos.add(Txtdata.get(i));
		}
		if(TracePos.size()<3) return AccSer;
		else{
			DistSeries = numJv.MtrSqrt(numJv.Sum2dInt(numJv.MtrPow(numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 1, 1, 2), 2), 1));
			TimeSeries = numJv.Sum2dInttoFloat(numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 3, 3, 1),1);
			firstLength = TimeSeries.size();
			for(int i=0;i<firstLength;i++){
				if(TimeSeries.get(i)==0) TimeSeries.set(i, (float) 1);
			}
			VeSer = numJv.MtrDivide(DistSeries, TimeSeries);
			TTimeSeries = TimeSeries; TTimeSeries.remove(TTimeSeries.size()-1);
			VeSeries = numJv.MtrMinus1d(VeSer, VeSer, 1, 0, VeSer.size()-1);
			AccSer = numJv.MtrDivide(VeSeries, TTimeSeries);
		}
		return AccSer;
	}
	public static ArrayList<Float> CalAccVariation(ArrayList<Float> AccVec){
		ArrayList<Float> AccAccVaria = new ArrayList<Float>();
		if(AccVec.isEmpty()) return AccAccVaria;
		else {
			AccVec = numJv.Abs(AccVec);
			AccAccVaria.add(numJv.Mean(AccVec, 0, AccVec.size()));
		}
		return AccAccVaria;
	}
	public static float CalWholeDuation(ArrayList<ArrayList<Integer>> Txtdata){
		float WholeTime = (float)(Txtdata.get(Txtdata.size()-1).get(3)-Txtdata.get(0).get(3));
		return WholeTime;
	}
	public static ArrayList<Float> CalTheta(ArrayList<ArrayList<Integer>> Txtdata){
		ArrayList<ArrayList<Integer>> TracePos = new ArrayList<ArrayList<Integer>> ();
		ArrayList<Float> Theta = new ArrayList<Float>();
		int firstLength = Txtdata.size();
		for(int i=0;i<firstLength;i++){
			if(Txtdata.get(i).get(0)==2)
				TracePos.add(Txtdata.get(i));
		}
		if(TracePos.size()<2) return Theta;
		else{
			ArrayList<ArrayList<Integer>> PointSeries = numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 1, 1, 2);
			firstLength = PointSeries.size();
			for(int item=0;item<firstLength;item++){
				if(PointSeries.get(item).get(0)==0)
					Theta.add((float) (Math.PI/2));
				else
					Theta.add((float) Math.atan((float)PointSeries.get(item).get(1)/PointSeries.get(item).get(0)));
			}
		}
		return Theta;
	}
	public static ArrayList<Float> CalOmiga(ArrayList<ArrayList<Integer>> Txtdata,ArrayList<Float> Theta){
		ArrayList<Float> VerSer = new ArrayList<Float>();
		ArrayList<ArrayList<Integer>> TracePos = new ArrayList<ArrayList<Integer>> ();
		ArrayList<Float> Omiga = new ArrayList<Float>();
		ArrayList<Float> dTheta = new ArrayList<Float>();
		if(Theta.size()<2) return VerSer;
		else{
			int firstLength = Txtdata.size();
			for(int i=0;i<firstLength;i++){
				if(Txtdata.get(i).get(0)==2)
					TracePos.add(Txtdata.get(i));
			}
			ArrayList<Float> TimeSeries = numJv.Sum2dInttoFloat(numJv.MtrMinus2d(TracePos, TracePos, 1, 0, TracePos.size()-1, 3, 3, 1),1);
			firstLength = TimeSeries.size();
			for(int i=0;i<firstLength;i++){
				if(TimeSeries.get(i)==0)
					TimeSeries.set(i, (float) 1);
			}
			TimeSeries.remove(TimeSeries.size()-1);
			firstLength = Theta.size();
			for(int i=0;i<firstLength-1;i++)
				dTheta.add(Theta.get(i+1)-Theta.get(i));
			Omiga = numJv.MtrDivide(numJv.Abs(dTheta), TimeSeries);
		}
		return Omiga;
	}
}
