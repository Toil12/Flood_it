package flood_it;

public class bitmap_op {
	//控构造函数
	public bitmap_op(){
		
	}
	//字符串异或
	public  String intergrate(String s1,String s2){
		byte b1[] = s1.getBytes(); 
		byte b2[] = s2.getBytes(); 
		int temp=b1.length;
		int[] e=new int[temp];
		
		for(int i=0;i<e.length;i++){ 
			int b=(int)b1[i]^(int)b2[i]; 
			e[i]=b;   
	 	}
		String str = "";
		for(int i=0;i<e.length;i++){
		  str = str + e[i];
		}
		return str;
	}
	//
}
