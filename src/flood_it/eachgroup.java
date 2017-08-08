package flood_it;

import java.util.ArrayList;
import java.util.Collections;

public class eachgroup implements Cloneable{
	public String notify="";
	public ArrayList<Integer> related=new ArrayList<Integer>();
	public ArrayList<Integer> connected=new ArrayList<Integer>();
	public int groupID;
	public int colour;
	public int[][] directionH={{0,-1},{-1,0},{0,1},{1,0}};
	public String bitmap;
	//构造方法
	public eachgroup(int ID){
		groupID=ID;
		String strbit="0000000000000000000000000";
		StringBuilder strBuilder = new StringBuilder(strbit);
		strBuilder.setCharAt(24-ID, '1');
		strbit=strBuilder.toString();
		bitmap=strbit;
		
	}
	public eachgroup() {
		// TODO Auto-generated constructor stub
	}
	//添加同组数字
	@SuppressWarnings("unchecked")
	public void addnumber(int number){
		int length=related.size();
		related.add(length,number);
		Collections.sort(related);
	}
	//
	
	//
	public void findconnection(int[][] arrayGroup,boolean judge){
		int row=arrayGroup.length;
		int column=arrayGroup[0].length;
		for(int a:related){
		if(judge){
				for(int k=0;k<=3;k++){
					int dr=directionH[k][0];
					int dc=directionH[k][1];
					int changerow;
					int changecolumn;
					changerow=(a)/(row)+dr;
					changecolumn=(a)%column+dc;
					if(changerow<0||changecolumn<0||changerow>=row||changecolumn>=column)
						continue;
					if(arrayGroup[changerow][changecolumn]!=groupID){
						boolean what=true;
						for(int b:connected){
							if(arrayGroup[changerow][changecolumn]==b){
								what=false;
								break;
							}
						}
						if(what==true){
							connected.add(arrayGroup[changerow][changecolumn]);
						}
					}
					
				}
			
			}
		Collections.sort(connected);
		}

		}
	//加入联通集
	public void composewith(eachgroup another){
		this.colour=another.colour;
		for(int a:another.related){
				this.addnumber(a);

		}
	}
	//
	public void removeone(int a){
		for(int i=0;i<=connected.size()-1;i++){
			if(connected.get(i)==a){
				connected.remove(i);
				break;
			}
		}
	}
	//
	protected Object clone() throws CloneNotSupportedException {
		// 实现clone方法
		return super.clone();
	}
	//
}
	//

