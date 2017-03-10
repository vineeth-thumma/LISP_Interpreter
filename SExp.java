import java.util.HashMap;

public class SExp {
	SExp_type type;
	int val;
	String name;
	SExp left;
	SExp right;
	public static HashMap<String,SExp> ID_List = new HashMap<String,SExp>(); 
	
	
	public SExp(SExp_type type, String s_exp){
		
		if(type == SExp_type.INT_ATOM) {
			this.type = type;
			this.val = Integer.parseInt(s_exp);
			this.left = null;
			this.right = null;
		}
		else if(type == SExp_type.SYM_ATOM) {
			this.type = type;
			this.name = s_exp;
			this.left = null;
			this.right = null;
		}
		else if(type == SExp_type.NON_ATOM) {
			this.type =type;
			this.left = null;
			this.right = null;
		
		}
		
		
		
		
		}
	
	

}
