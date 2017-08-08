package flood_it;

import java.util.Random;
import java.util.*;

public class Random_matrix {
	private int row=0;
	private int column=0;
	public  int[][] flood_array={};

	public Random_matrix(int row_in,int column_in){
		row=row_in;
		column=column_in;
		flood_array=new int[row][column];
		for(int i=0;i<=row-1;i++){
			for(int j=0;j<=column-1;j++){
				long t = System.currentTimeMillis();
				Random rd=new Random(t);
				//diferent number means different colour
				flood_array[i][j]=rd.nextInt(100)%row;
			}
		}
	}
	
}
