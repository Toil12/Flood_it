package flood_it;
import java.util.*;

public class Main {
	public int[][] direction={{0,-1},{-1,0},{0,1},{1,0}};
	//private static Hashtable<String,Integer[][]> save_related = new Hashtable<String,Integer[][]>();  
	private static Hashtable<String,Integer[]> frontier = new Hashtable<String,Integer[]>();//g(x),h(x)
	//A*搜索
	bitmap_op bit_op=new bitmap_op();
	//
	public eachgroup AStar(ArrayList<eachgroup> groupSaveSource,int[][] group,int[][] color){
		Main construction2=new Main();
		int col_number=construction2.findcolournumber(color);
		ArrayList<eachgroup> groupSaveUse=groupSaveSource;//groupSaveSource是原始的数据，便于搜索
		eachgroup composing=groupSaveUse.get(0);
		composing.notify="0";
		Integer[] initialize={0,0,0};
		frontier.put("0", initialize);
		int gn=1;
//		eachgroup composing2=composing;
		ArrayList<String> keys=new ArrayList<String>();
		keys.add(0,"0");
		//ArrayList<String> keys2=keys;
		composing=construction2.AStar2(composing, groupSaveUse, groupSaveSource, keys, color, group,gn);
		return  composing;
	}
		public eachgroup AStar2(eachgroup composing,ArrayList<eachgroup> groupSaveUse,ArrayList<eachgroup> groupSaveSource,ArrayList<String> keys,int[][] color,int[][] group,int gn){
			int hn=0;
			int lastcolour=0;
			int[][] Colour1=color;
			int colour=0;
			int row=group.length;
			int column=group[0].length;
			Main construction1=new Main();
			int[][] samecolour=new int[construction1.findcolournumber(color)][25];
			// 
			for(int i=0;i<samecolour.length;i++)
				for(int k=0;k<samecolour[0].length;k++){
					samecolour[i][k]=-1;
				}
			//当最后一步时检测并输出
			if(construction1.findcolournumber(color)==1){
				for(int i=0;i<=row-1;i++){
					for(int j=0;j<=column-1;j++){
						if(group[i][j]!=composing.groupID){
							composing.notify=composing.notify+"+"+group[i][j];
							group[i][j]=composing.groupID;
						}
					}
				}
				return composing;
			}
			//
	
			//
			for(int a:composing.connected){
				String key=composing.bitmap;
				lastcolour=a;
				eachgroup eat=groupSaveUse.get(a);
				colour=eat.colour;
				for(int c:composing.related){
					int rowp=(c)/(row);
					int colp=(c)%column;
					Colour1[rowp][colp]=colour;
				}
				String current=bit_op.intergrate(key, groupSaveSource.get(a).bitmap);
				//current=construction1.sortkey(current);

				Enumeration<String> e = frontier.keys();
				boolean has=false;
				while (e.hasMoreElements()){
			         if(e.nextElement()==current){
			        	 has=true;
			        	 break;
			         }
				}
				if(!has){
			         hn=construction1.findcolournumber(Colour1,composing,eat);
			         Integer[] fn={gn,hn,lastcolour};
			         frontier.put(current, fn);
			         keys.add(current);
				}
			}
			
			int maxof=0;
			int keyweneed =0;
			for(String a:keys){
				if(a=="0")
					continue;
				int sumof=frontier.get(a)[0]+(25-frontier.get(a)[1]);
				if(maxof<sumof){
					maxof=sumof;
					keyweneed=frontier.get(a)[2];
				}
			}
			int eatnumber=keyweneed;
			gn++;
			eachgroup eatgroup=groupSaveSource.get(eatnumber);
			composing.composewith(eatgroup);
			for(int a:composing.related){
				int rowp=(a)/(row);
				int colp=(a)%column;
				group[rowp][colp]=composing.groupID;
				color[rowp][colp]=composing.colour;
			}
			composing.notify=composing.notify+"+"+keyweneed;
			composing.removeone(keyweneed);
			composing.findconnection(group, true);
			composing=construction1.AStar2(composing, groupSaveUse, groupSaveSource, keys, color, group,gn);
			return composing;
	}
	//对key值进行排序
	public String sortkey(String tosort){
		tosort=tosort.trim();
		String[] strArray=tosort.split("[+]");   
		int length=strArray.length;
        int[] Reihenfolge=new int[length];
        for(int i=0;i<=length-1;i++){
        	Reihenfolge[i]=Integer.parseInt(strArray[i]);
        }
        Arrays.sort(Reihenfolge);
        tosort="";
        for(int i=0;i<=Reihenfolge.length-2;i++){
        	tosort=String.valueOf(Reihenfolge[i])+"+";
        }
        tosort=tosort+String.valueOf(Reihenfolge[length-1]);
        return tosort;
	}
	//找寻不同的颜色数量
	public int findcolournumber(int[][] colourarray,eachgroup compose,eachgroup eat){ 
		int colournumber;
		Set set = new HashSet();
		for(int i=0;i<=colourarray.length-1;i++){
			for(int j=0;j<=colourarray[0].length-1;j++){
				int position=i*colourarray.length+j;
				boolean jud=true;
				for(int a:compose.related){
					if(position==a)
						jud=false;
				}
				for(int a:eat.related){
					if(position==a)
						jud=false;
				}
				if(jud)
			     set.add(colourarray[i][j]);
			    }
			}
		colournumber=set.size();
		return colournumber;
	}
	//
	public int findcolournumber(int[][] colourarray){
		int colournumber;
		Set set = new HashSet();
		for(int i=0;i<=colourarray.length-1;i++){
			for(int j=0;j<=colourarray[0].length-1;j++){
			     set.add(colourarray[i][j]);
			    }
			}
		colournumber=set.size();
		return colournumber;
	}
	//
	public int getmax(int[][] array){
		int max=0;
		int row=array.length;
		int column=array[0].length;
		for(int i=0;i<=row-1;i++){
			for(int j=0;j<=column-1;j++){
				if(max<array[i][j])
					max=array[i][j];
			}
		}
		return max;
	}
	//
	public int getmax(int[] array){
		int max=0;
		for(int a:array){
			if(max<a)
				max=a;
		}
		return max;
	}
	//
	public void printarray(int array[][]){
		int row=array.length;
		int column=array[0].length;
		for(int i=0;i<=row-1;i++){
			for(int j=0;j<=column-1;j++){
				if(array[i][j]<10){
					System.out.print(array[i][j]+"   ");
				}
				else{
					System.out.print(array[i][j]+"  ");
				}
				
				if(j==column-1){
					System.out.println();
				}
			}
		}
		System.out.println();
	}
	//遍历并且分组
	public int[][] divide_group(int[][] color_matrix){
		Main contruction=new Main();
		int row=color_matrix.length;
		int column=color_matrix[0].length;
		int[][] group_matrix=new int[row][column];
		int divide=0;
		boolean[][] change=new boolean[row][column];
		//
		change[0][0]=true;
		group_matrix[0][0]=divide;
		//
		for(int i=0;i<=row-1;i++){
			for(int j=0;j<=column-1;j++){
				if(change[i][j]==true&&i+j!=0)
					continue;
			group_matrix=contruction.sort(change, group_matrix, color_matrix, divide, i, j);
			divide++;
			}
		}
		return group_matrix;
	}
	//递归搜索对同色的快分组
	public int[][] sort(boolean[][] change,int[][] group_matrix,int[][]color_matrix,int divide,int i,int j){
		change[i][j]=true;
		group_matrix[i][j]=divide;
		int newdivide=divide;
		int now=group_matrix[i][j];
		for(int k=0;k<=3;k++){
			int dr=direction[k][0];
			int dc=direction[k][1];
			int changerow=i+dr;
			int changecolumn=j+dc;
			if(changerow<0||changecolumn<0||changerow>=color_matrix.length||changecolumn>=color_matrix[0].length)
				continue;
			if(color_matrix[changerow][changecolumn]==color_matrix[i][j]){			
				if(change[changerow][changecolumn]==true){
					group_matrix[i][j]=group_matrix[changerow][changecolumn];
					continue;
				}
				else{					
					group_matrix[changerow][changecolumn]=now;
					group_matrix=sort(change, group_matrix,color_matrix,newdivide,changerow,changecolumn);
				}
			}
			else{					
				continue;
			}			
		}
		return group_matrix;
	}
//
	@SuppressWarnings("null")
	public static void main(String[] args){
		Main construction=new Main();
		Scanner input=new Scanner(System.in);
//		int[][] anothermatrix={{0,4,1,1,4},{2,2,2,0,1},{2,2,2,4,0},{1,2,4,2,1},{2,2,4,2,0}};
//		int[][] anothermatrix={{0,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}};
		int[][] anothermatrix={{2,3,2,3,2},{0,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}};
		int[][] group;
		int[][] colormatrix=anothermatrix;
		ArrayList<eachgroup> groupsave = new ArrayList<eachgroup>();
		int r=colormatrix.length;
		int c=colormatrix[0].length;
		//
		System.out.println("the source matrix is(every number represents a colour):");
		construction.printarray(anothermatrix);
		System.out.println("below is the matrix after being divided by colour:");
		group=construction.divide_group(anothermatrix);
		construction.printarray(group);
		//
		int groupnumber=construction.getmax(group);
		for(int i=0;i<=groupnumber;i++){
			groupsave.add(new eachgroup(i));
		}
		for(int i=0;i<=group.length-1;i++)
			for(int j=0;j<=group[0].length-1;j++){
				int position=i*r+j;
				int ID=group[i][j];
				int color=colormatrix[i][j];
				groupsave.get(ID).colour=color;
				groupsave.get(ID).addnumber(position);
			}
		for(int i=0;i<=groupsave.size()-1;i++){
			groupsave.get(i).findconnection(group, true);
		}
		eachgroup final0=construction.AStar(groupsave, group, colormatrix);
		System.out.println(final0.notify);
	//main function end
	}
}
