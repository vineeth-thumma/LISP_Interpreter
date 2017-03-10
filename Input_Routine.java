import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Input_Routine {
	
	static BufferedReader buffer = new BufferedReader(
            new InputStreamReader(System.in));
			static String token;
			static token_type token_val;
			static char tmp_char;
			static int tmp_val;
			static boolean buffer_read_flag = false;
			static boolean s_exp_start_flag = false;
			static {
				get_ID("T");
				get_ID("NIL");
			}
			

	public static void main(String[] args) throws IOException, Invalid_Exception{
		s_exp_start_flag = true;
		skip_token();
		s_exp_start_flag = false;
		
		while(token_val != token_type.END) {
		try {
		if(token_val != token_type.DOLLAR) {
			
			SExp Exp = Input_1();
			
			// skip white-space char's after valid S-Exp
			while( tmp_val == 32) {
				 skip_token();
  		    }
  		    
			if(token_val != token_type.DOLLAR  && token_val != token_type.END && tmp_val != 36) {
				throw new Invalid_Exception("> Error: Unexpected token '"+token+"' after valid S-Expression");
				
			}
			else  {	
				System.out.println();
				System.out.print("> ");
				Output(Exp);
				System.out.println();
			}
			if(token_val == token_type.END)
				return;
		
		  }
			s_exp_start_flag = true;
			skip_token();
			s_exp_start_flag = false;
			
		}
		catch (Invalid_Exception exception ){
			
				System.out.println();
				System.out.print(exception);
				System.out.println();
			
			while(token_val != token_type.DOLLAR && token_val != token_type.END)
				skip_token();
			if(token_val == token_type.DOLLAR)
				skip_token();
			else
				return;
		}
	 }
		return;
	}
	
	public static SExp Input_1() throws IOException, Invalid_Exception { 
		
		  
			if(token_val == token_type.ID) {
				String tmp = token;
				skip_token();
				
				return get_ID(tmp);
			}
			else if (token_val == token_type.INT){
				SExp Exp = new SExp(SExp_type.INT_ATOM, token);
				skip_token();
				return Exp;
			}
			else if( token_val == token_type.LEFT_BRACKET) {
				SExp Exp = new SExp(SExp_type.NON_ATOM, token);
				skip_token();
				if(token_val == token_type.RIGHT_BRACKET) {
					skip_token();
					return get_ID("NIL");
				}
				
				Exp.left = Input_1();
				if(token_val == token_type.DOT) {
					skip_token();
					Exp.right = Input_1();
					if(token_val != token_type.RIGHT_BRACKET)
						throw new Invalid_Exception("> Error I1(): Unexpected token in the S-exp: '"+token+"'");
					else 
						skip_token();
						
				}
				else {
					Exp.right = Input_2();
				}
				
				return Exp;
				
			}
			else {
				throw new Invalid_Exception("> Error I1(): Unexpected token in the S-exp: '"+token+"'");
				
			}
			
	}
	
	public static SExp Input_2() throws IOException, Invalid_Exception { 
		
		if(token_val == token_type.RIGHT_BRACKET) {
			skip_token();
			return get_ID("NIL");
		} 
		else if(token_val == token_type.SPACE)
		{
			SExp Exp = new SExp(SExp_type.NON_ATOM, token);
			skip_token();
			Exp.left = Input_1();
			Exp.right = Input_2();
			return Exp;
		}
		else {
			throw new Invalid_Exception("> Error I2(): Unexpected token in the S-exp: '"+token+"'");
		}
	}
	
	public static void Output(SExp Exp) {
		
		if(Exp.left != null) {
			System.out.print("(");
			Output(Exp.left);
		}
		if(Exp.type != SExp_type.NON_ATOM)
			print_SExp(Exp);
		if(Exp.type == SExp_type.NON_ATOM)
			System.out.print(".");
		if(Exp.right != null) {
			Output(Exp.right);
			System.out.print(")");
		
		}
			
		
	}
	
	public static void print_SExp(SExp Exp){
		if(Exp.type == SExp_type.INT_ATOM) 
			System.out.print(Exp.val);
		else if(Exp.type == SExp_type.SYM_ATOM)
			System.out.print(Exp.name);
					
	}
	
	public static SExp get_ID (String token) {
		if(SExp.ID_List.get(token) != null) {
			return SExp.ID_List.get(token);
		}
		else {
			SExp tmp_Exp = new SExp(SExp_type.SYM_ATOM, token);
			SExp.ID_List.put(token, tmp_Exp);
			return tmp_Exp;
		}
		
	}
	
	public static token_type check_next_token() {
		
		return token_val;
	}
	
	public static void skip_token() throws IOException, Invalid_Exception {
			
			if(!buffer_read_flag)  {
				tmp_val = buffer.read();
				tmp_char = (char) tmp_val;
			}
			buffer_read_flag = false;
						
			if(tmp_char != -1) {
	                
	           if(tmp_char == '(') {
	        	   token = "(";
	        	   token_val = token_type.LEFT_BRACKET;
	        	   tmp_val = buffer.read();
	        		  while(tmp_val == 9 || tmp_val == 10 || tmp_val == 13 || tmp_val == 32) {
	        			  tmp_val = buffer.read();
	        	   }
	        	   buffer_read_flag = true;
  			       tmp_char = (char)tmp_val;
	        	   return;
	           }
	           else if (tmp_char == ')') {
	        	   token = ")";
	        	   token_val = token_type.RIGHT_BRACKET;
	        	   return;
	           }
	           else if (tmp_char == '.') {
	        	   token = ".";
	        	   token_val = token_type.DOT;
	        	   tmp_val = buffer.read();
	        		  while(tmp_val == 9 || tmp_val == 10 || tmp_val == 13 || tmp_val == 32) {
	        			  tmp_val = buffer.read();
	        		  }
	        		  buffer_read_flag = true;
     			      tmp_char = (char)tmp_val;
	        		  return;
	           }
	           // ID token
	           else if (tmp_char >= 'A' && tmp_char <= 'Z') {
	        	   String tmp_tok = String.valueOf(tmp_char);
	        	   int c = buffer.read();
	        	   // check if [A-Z] or [1-9]
	        	   while(( c >= 65 && c <= 90) || (c >= 48 && c <= 57)) {
	        		   tmp_tok += Character.toString((char)c);
	        		   c = buffer.read();
	        	   }
	        	   // 9-Tab  10,13 -line feed   32-' '  36-'$'  40-'('   41 -')'    46-'.'
	        	   if(c!= 9 && c!= 10 && c != 13 && c != 32 && c != 36 && c!= 40 && c != 41 && c != 46) {
	        		   token_val = token_type.INVALID;
	        		   throw new Invalid_Exception("> Error: Unexpected '"+Character.toString((char)c)+"'");
	        	   }
	        	   buffer_read_flag = true;
	        	   tmp_char = (char)c;
	        	   tmp_val = c;
	        	   
	        	   token = tmp_tok;
	        	   token_val = token_type.ID;
	        	   return;
	        	   
	           }
	           // INT token
	           else if ((tmp_char >= '0' && tmp_char <= '9') || tmp_char == '+' || tmp_char == '-' ){
	        	   String tmp_tok = String.valueOf(tmp_char);
	        	   int c = buffer.read();
	        	   if((tmp_char == '+' || tmp_char == '-') && (c < 48 || c > 57)) {
	        		   token_val = token_type.INVALID;
	        		   throw new Invalid_Exception("> Error: Unexpected '"+Character.toString((char)c)+"'");
	        	   	}
	        	   // check if [0-9]
	        	   while (c >= 48 && c <= 57) {
	        		   tmp_tok += Character.toString((char)c);
	        		   c = buffer.read();
	        	   }
	        	   // 9-Tab  10,13 -line feed   32-' '  36-'$'  40-'('   41 -')'    46-'.'
	        	   if(c!=9 && c!= 10 && c != 13 && c != 32 && c != 36 && c!= 40 && c != 41 && c != 46) {
	        		   token_val = token_type.INVALID;
	        		   throw new Invalid_Exception("> Error: Unexpected '"+Character.toString((char)c)+"'");
		        	   }
	        	   buffer_read_flag = true;
	        	   tmp_char = (char)c;
	        	   tmp_val = c;
	        	   	        	   
	        	   token = tmp_tok;
	        	   token_val = token_type.INT;
	        	   return;
	           }
	           else if (tmp_val == 9 || tmp_val == 10 || tmp_val == 13 || tmp_val == 32) {
	        	   while(tmp_val == 9 || tmp_val == 10 || tmp_val == 13) {
	        		   tmp_val = buffer.read();
	        	   }
	        	 
	        	   if(tmp_val == 32) {
	        		   token = " ";
	        		   token_val = token_type.SPACE;
	        		   tmp_val = buffer.read();
	        		   while(tmp_val == 9 || tmp_val == 10 || tmp_val == 13 || tmp_val == 32) {
	        			  tmp_val = buffer.read();
	        		   }
	        		  if(tmp_val == 46) {
	        			  token = ".";
		        		  token_val = token_type.DOT;
		        		  tmp_val = buffer.read();
		        		  while(tmp_val == 9 || tmp_val == 10 || tmp_val == 13 || tmp_val == 32) {
		        			  tmp_val = buffer.read();
		        		  }
		        		  buffer_read_flag = true;
	        			  tmp_char = (char)tmp_val;
		        		  return;
	        		  }
	        		  else if (tmp_val == 40 && s_exp_start_flag == true) {
	        			  s_exp_start_flag = false;
	        			  token = "(";
		        		  token_val = token_type.LEFT_BRACKET;
		        		  tmp_val = buffer.read();
		        		  while(tmp_val == 9 || tmp_val == 10 || tmp_val == 13 || tmp_val == 32) {
		        			  tmp_val = buffer.read();
		        		  }
		        		  buffer_read_flag = true;
	  			       	  tmp_char = (char)tmp_val;
		        		  return;
	        		  }
	        		  else if (tmp_val == 41) {
	        			  token = ")";
		        		  token_val = token_type.RIGHT_BRACKET;
		        		  tmp_char = (char)tmp_val;
		        		  return;
	        		  }
	        		  
	        		  else if (tmp_val ==36) {
	        			  tmp_val = buffer.read();
	        			  if((char)tmp_val == '$') {
	        			   token = "$$";
	   	        		   token_val = token_type.END;
	   	        		   return;
	   	        	    }
	   	        	    else {
	   	        	       token = "$";
	   	        	       tmp_char = (char)tmp_val;
	   	        		   token_val = token_type.DOLLAR;
	   	        		   buffer_read_flag = true;
	   	        		   return;
	   	        	    } 	  
	        		  }
	        		  else {
	        			  buffer_read_flag = true;
	        			  tmp_char = (char)tmp_val;
	        			  return;
	        		  }
	        		  
	        	   }
	        	   else {
	        		   buffer_read_flag = true;
	        		   tmp_char = (char)tmp_val;
	        		   return;
	        	  }
	        	   
	           }
	           else if (tmp_char == '$') {
	        	   tmp_val = buffer.read();
     			   if((char)tmp_val == '$') {
     				   token = "$$";
	        		   token_val = token_type.END;
	        		   return;
	        	   }
	        	    else {
	        	       token = "$";
	        	       tmp_char = (char)tmp_val;
	        		   token_val = token_type.DOLLAR;
	        		   buffer_read_flag = true;
	        		   return;
	        	   } 	  
	           }
	           else {
	        	   token_val = token_type.INVALID;
	        	   throw new Invalid_Exception("Error: Invalid token: '"+Character.toString(tmp_char)+"'");
	           }
	          
	         
			}
	}
	
	
}
