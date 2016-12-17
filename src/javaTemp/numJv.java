package javaTemp;

import java.util.ArrayList;

public class numJv {
	/*
	 * 二维矩阵对应元素相减
	 * 怎么才能做到任意类型的二维矩阵相减？？
	 */
	public static ArrayList<ArrayList<Integer>> MtrMinus2d(ArrayList<ArrayList<Integer>> a,ArrayList<ArrayList<Integer>> b,int a1start,int b1start,int length1,int a2start,int b2start,int length2){
		ArrayList<ArrayList<Integer>> outMtr = new ArrayList<ArrayList<Integer>> ();
		for(int i=0;i<length1;i++){
			ArrayList<Integer>c1=new ArrayList<Integer>();
			for(int j=0;j<length2;j++){
				int temp=a.get(a1start+i).get(a2start+j)-b.get(b1start+i).get(b2start+j);
				c1.add(temp);
			}
			outMtr.add(c1);
		}
		return outMtr;
	}
	/*
	 * 一维矩阵对应元素相减
	 */
	public static ArrayList<Float> MtrMinus1d(ArrayList<Float> a,ArrayList<Float> b,int astart,int bstart,int length){
		ArrayList<Float> outMtr = new ArrayList<Float> ();
		for(int i=0;i<length;i++){
			float temp=a.get(astart+i)-b.get(bstart+i);
			outMtr.add(temp);
		}
		return outMtr;
	}
	/*
	 * 两个一维向量求内积
	 */
	public static float DotPro1d(ArrayList<Integer> a,ArrayList<Integer> b){
		int length = a.size(),sum=0;
		if(length!=b.size()) {
			System.out.println("点乘向量个数不相等");
			return (float)sum;
		}
		for(int i=0;i<length;i++)
			sum += a.get(i)*b.get(i);
		return (float)sum;
	}
	/*
	 * 一维求和（整型到float型）
	 */
	public static float Sum1dInt(ArrayList<Integer> a){
		int sum=0;
		for(int i=0;i<a.size();i++)
			sum+=a.get(i);
		return (float)sum;

	}
	/*
	 * 一维求和（float型）
	 */
	public static float Sum1dFloat(ArrayList<Float> a){
		float sum=0;
		for(int i=0;i<a.size();i++)
			sum+=a.get(i);
		return sum;

	}
	/*
	 * 二维求和到一维，返回int，传入参数k为1表示内层内求和
	 */
	public static ArrayList<Integer>Sum2dInt(ArrayList<ArrayList<Integer>> a,int k){
		ArrayList<Integer>b=new ArrayList<Integer>();
		if(k==1){
			int firstLength = a.size();
			for(int i=0;i<firstLength;i++){
				int sum=0;
				int secondLength = a.get(i).size();
				for(int j=0;j<secondLength;j++){
					sum+=a.get(i).get(j);
				}
				b.add(sum);	
			}
		}
		return(b);
	}
	public static ArrayList<Float>Sum2dInttoFloat(ArrayList<ArrayList<Integer>> a,int k){
		ArrayList<Float>b=new ArrayList<Float>();
		if(k==1){
			int firstLength = a.size();
			for(int i=0;i<firstLength;i++){
				int sum=0;
				int secondLength = a.get(i).size();
				for(int j=0;j<secondLength;j++){
					sum+=a.get(i).get(j);
				}
				b.add((float)sum);	
			}
		}
		return(b);
	}
	/*
	 * 一维向量平均值,空List返回0,左闭右开
	 */
	public static float Mean(ArrayList<Float> a,int startPos,int endPos){
	    float sum=0;
	    if(a.size()==0||startPos>=endPos) return sum;
        for(int i=startPos;i<endPos;i++)
			sum+=a.get(i);
        return sum/(float)(endPos-startPos);
	}
	/*
	 * 二维矩阵求某行（列）平均值
	 * 0是最里层求平均，1是最里层之间求平均
	 */
	public static ArrayList<Float> Mean2d(ArrayList<ArrayList<Float>> a, int n){
		ArrayList<Float>b=new ArrayList<Float>();
		if(a.size()==0||a.get(0).size()==0){
			b.add((float)0);
			b.add((float)0);
			return b;
		}
		if(n==1){
			int firstLength=a.size();
			int secondLength = a.get(0).size();
			ArrayList<Float> tmpList = new ArrayList<Float>();
			for(int j=0;j<secondLength;j++){
				tmpList.add((float) 0);
			}
			for(int i=0;i<firstLength;i++){
				for(int j=0;j<secondLength;j++){
					tmpList.set(j, tmpList.get(j)+a.get(i).get(j));
				}
			}
			for(int j=0;j<secondLength;j++){
				b.add(tmpList.get(j)/firstLength);
			}
		}
		return b;
	}
	/*
	 * 返回一维向量的标准差
	 */
	public static float Std(ArrayList<Float> a,int startPos,int endPos){
		if(a.size()==0||startPos>=endPos) return (float)0;
		float c=Mean(a,startPos,endPos);
		float sum=0;
		float d=0;
		for(int i=startPos;i<endPos;i++){
			sum+=Math.pow((a.get(i)-c),2);
		}
		d=(float)Math.sqrt(sum/(endPos-startPos));
		return d;

	}
	/*
	 * 找出二维向量最内层第一个元素等于某个值的索引值
	 */
	public static ArrayList<Integer>Where(ArrayList<ArrayList<Integer>> a,int pos, int number){
  	  int length=a.size();
  	  ArrayList<Integer> b=new ArrayList<Integer>();
  	  for(int i=0;i<length;i++){
  		  if(a.get(i).get(pos)==number)
  			  b.add(i);
  	  }
  	  return b;
    }
	/*
	 * 输出传入矩阵的乘方值
	 */
	public static ArrayList<ArrayList<Integer>> MtrPow(ArrayList<ArrayList<Integer>> a, int n){
		ArrayList<ArrayList<Integer>>b=new ArrayList<ArrayList<Integer>>();
		int length1=a.size();
		for(int i=0;i<length1;i++){
			int length2=a.get(i).size();
			ArrayList<Integer>c=new ArrayList<Integer>();
			for(int j=0;j<length2;j++){
				int temp=(int)Math.pow(a.get(i).get(j),2);
				c.add(temp);
			}
			b.add(c);
		}
		return b;
	}
	/*
	 * 传入一维数组开方
	 */
	public static ArrayList<Float> MtrSqrt(ArrayList<Integer> a){
		int length=a.size();
   	  	ArrayList<Float> b=new ArrayList<Float>();
   	  	for(int i=0;i<length;i++){
   	  		float temp=(float)Math.sqrt(a.get(i));
   	  		b.add(temp);
   	  	}
   	  	return b;
	}
	/*
	 * 返回一维数组绝对值
	 */
	public static ArrayList<Float> Abs(ArrayList<Float> a){
		ArrayList<Float> b=new ArrayList<Float>();
		int num=a.size();
		for(int i=0;i<num;i++){
			float temp=Math.abs(a.get(i));
			b.add(temp);
		}
		return b;
	}
	public static ArrayList<Float> MtrDivide(ArrayList<Float> a,ArrayList<Float> b){
		int length=a.size();
   	  	ArrayList<Float>c=new ArrayList<Float>();
   	  	for(int i=0;i<length;i++){
   	  		float temp=a.get(i)/b.get(i);
   	  		c.add(temp);
   	  	}
   	  	return c;
	}

}
