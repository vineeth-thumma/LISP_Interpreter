
public class Evaluator {
	

	
	public static SExp CAR(SExp Exp) throws Invalid_Exception {
		if((Exp.type == SExp_type.INT_ATOM) || (Exp.type == SExp_type.SYM_ATOM )) {
			throw new Invalid_Exception("> Error in CAR: Argument to CAR cannot be an Atom");
		}
		return Exp.left;
	}
	
	
	public static SExp CDR(SExp Exp) throws Invalid_Exception {
		if((Exp.type == SExp_type.INT_ATOM) || (Exp.type == SExp_type.SYM_ATOM )) {
			throw new Invalid_Exception("> Error in CDR: Argument to CDR cannot be an Atom");
		}
		return Exp.right;
	}
	
	
	public static SExp CONS(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		SExp Exp = new SExp(SExp_type.NON_ATOM, null);
		Exp.left = Exp1;
		Exp.right = Exp2;
		return Exp;
		
	}
	
	
	public static SExp ATOM (SExp Exp) throws Invalid_Exception{
		if((Exp.type == SExp_type.INT_ATOM) || (Exp.type == SExp_type.SYM_ATOM))
			return Input_Routine.get_ID("T");
		else 
			return Input_Routine.get_ID("NIL");
		
	}
	
	
	public static SExp INT(SExp Exp) throws Invalid_Exception{
		if(Exp.type == SExp_type.INT_ATOM)
			return Input_Routine.get_ID("T");
		else 
			return Input_Routine.get_ID("NIL");
		
	}
	
	
	public static SExp EQ(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			if(Exp1.val == Exp2.val)
				return Input_Routine.get_ID("T");
		}
		else if ((Exp1.type == SExp_type.SYM_ATOM) && (Exp2.type == SExp_type.SYM_ATOM)) {
			if(Exp1.name.equals(Exp2.name))
				return Input_Routine.get_ID("T");
		}
		else if ((Exp1.type == SExp_type.NON_ATOM) || (Exp2.type == SExp_type.NON_ATOM))
			throw new Invalid_Exception("> Argument to EQ cannot be NON_ATOM");

		return Input_Routine.get_ID("NIL");
		
	}
	
	
	public static SExp NULL(SExp Exp) throws Invalid_Exception{
		// Checking if the reference is to the same symbolic atom NIL
		if(Input_Routine.get_ID(Exp.name) == Input_Routine.get_ID("NIL"))
			return Input_Routine.get_ID("T");
		else 
			return Input_Routine.get_ID("NIL");
		
	}
	
	
	public static SExp PLUS(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			SExp Exp = new SExp(SExp_type.INT_ATOM, Integer.toString(Exp1.val+Exp2.val));
				return Exp;
		}
		throw new Invalid_Exception("> Error in PLUS: Arguments to PLUS cannot be non-Integers");
		
	}
	
	
	public static SExp MINUS(SExp Exp1, SExp Exp2) throws Invalid_Exception {
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			SExp Exp = new SExp(SExp_type.INT_ATOM, Integer.toString(Exp1.val-Exp2.val));
				return Exp;
		}
		throw new Invalid_Exception("> Error in MINUS: Arguments to MINUS cannot be non-Integers");
		
	}
	
	
	public static SExp TIMES(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			SExp Exp = new SExp(SExp_type.INT_ATOM, Integer.toString(Exp1.val*Exp2.val));
				return Exp;
		}
		throw new Invalid_Exception("> Error in TIMES: Arguments to TIMES cannot be non-Integers");
		
	}
	
	
	public static SExp QUOTIENT(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			if(Exp2.val == 0)
				throw new IllegalArgumentException("> Error in REMAINDER:Argument Exp2-'divisor' is 0");
			SExp Exp = new SExp(SExp_type.INT_ATOM, Integer.toString(Exp1.val/Exp2.val));
				return Exp;
		}
		throw new Invalid_Exception("> Error in QUOTIENT: Arguments to QUOTIENT cannot be non-Integers");
	}
	
	
	public static SExp REMAINDER(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			if(Exp2.val == 0)
				throw new IllegalArgumentException("> Error in REMAINDER:Argument Exp2-'divisor' is 0");
			SExp Exp = new SExp(SExp_type.INT_ATOM, Integer.toString(Exp1.val%Exp2.val));
				return Exp;
		}
		throw new Invalid_Exception("> Error in REMAINDER: Arguments to REMAINDER cannot be non-Integers");
	}
	
	
	public static SExp LESS(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			if(Exp1.val < Exp2.val)
				return Input_Routine.get_ID("T");
			else 
				return Input_Routine.get_ID("NIL");
		}
		throw new Invalid_Exception("> Error in LESS: Arguments to LESS cannot be non-Integers");
		
	}
	
	
	public static SExp GREATER(SExp Exp1, SExp Exp2) throws Invalid_Exception{
		if((Exp1.type == SExp_type.INT_ATOM) && (Exp2.type == SExp_type.INT_ATOM)) {
			if(Exp1.val > Exp2.val)
				return Input_Routine.get_ID("T");
			else 
				return Input_Routine.get_ID("NIL");
		}
		throw new Invalid_Exception("> Error in GREATER: Arguments to GREATER cannot be non-Integers");
	}
	
	
	public static SExp evlis(SExp list, SExp aList, SExp dList) throws Invalid_Exception{
		
		if(NULL(list) == Input_Routine.get_ID("T")) {
			return Input_Routine.get_ID("NIL");
		}
		else 
			return CONS(eval(CAR(list), aList, dList), evlis(CDR(list), aList, dList));
		
	}
	
	public static SExp evcon(SExp be, SExp aList, SExp dList) throws Invalid_Exception{
		if(NULL(be) == Input_Routine.get_ID("T")) {
			throw new Invalid_Exception("> Error in evcon: condition to evcon cannot be NIL");
		}
		else if (eval(CAR(CAR(be)), aList, dList) != Input_Routine.get_ID("NIL")) {
			if (len_SExp(CAR(be)) == 2)
				return eval(CAR(CDR(CAR(be))), aList, dList);
			else
				throw new Invalid_Exception("> Error in evcon: invalid no of arguments");
				
		}
		else
			return evcon(CDR(be), aList, dList);
					
	}
	
	public static SExp eval(SExp exp, SExp aList, SExp dList) throws Invalid_Exception {
		if(ATOM(exp) == Input_Routine.get_ID("T")) {
			if(INT(exp) == Input_Routine.get_ID("T")) {
				return exp;
			}
			else if (EQ(exp, Input_Routine.get_ID("T")) == Input_Routine.get_ID("T")) {
				return Input_Routine.get_ID("T");
			}
			else if (EQ(exp, Input_Routine.get_ID("NIL")) == Input_Routine.get_ID("T")) {
				return Input_Routine.get_ID("NIL");
			}
			else if (in_aList(exp, aList) == Input_Routine.get_ID("T")) {
				return getVal_aList(exp, aList);
			}
			else
				throw new Invalid_Exception("> Error in eval: unbound variable !");
		}
		else if(ATOM(CAR(exp)) == Input_Routine.get_ID("T")) {
			if(EQ(CAR(exp), Input_Routine.get_ID("QUOTE")) == Input_Routine.get_ID("T")) {
				return CAR(CDR(exp));
			}
			else if (EQ(CAR(exp), Input_Routine.get_ID("COND")) == Input_Routine.get_ID("T")) {
				return evcon(CDR(exp), aList, dList);
			}
			else if (EQ(CAR(exp), Input_Routine.get_ID("DEFUN")) == Input_Routine.get_ID("T")) {
				// Add to D-List and return function name
				SExp new_root = new SExp(SExp_type.NON_ATOM, null);
				SExp new_dList_node = new SExp(SExp_type.NON_ATOM, null);
				SExp fun_def_node = new SExp(SExp_type.NON_ATOM, null);
				
				new_dList_node.left = CAR(CDR(exp));
				new_dList_node.right = fun_def_node;
				fun_def_node.left = CAR(CDR(CDR(exp)));
				fun_def_node.right = CAR(CDR(CDR(CDR(exp))));
				new_root.left = new_dList_node;
				new_root.right = Input_Routine.dList_global;
				Input_Routine.dList_global = new_root;
				if(CDR(CDR(CDR(CDR(exp)))) != Input_Routine.get_ID("NIL"))
					throw new Invalid_Exception("> Error in eval: DEFUN has improper function definition !");
				return Input_Routine.dList_global.left.left;
			}
			else {
				
				return apply(CAR(exp), evlis(CDR(exp), aList, dList), aList, dList);
			}
		}
		else 
			throw new Invalid_Exception("> Error in eval: Invalid S-Exp!");
	}
	
	public static int len_SExp (SExp Exp) {
		int length = 0;
		while(Exp != Input_Routine.get_ID("NIL")) {
			length++;
			Exp = Exp.right;
			}
		return length;
	}

	public static SExp apply(SExp f, SExp x, SExp aList, SExp dList) throws Invalid_Exception{
		if(ATOM(f) == Input_Routine.get_ID("T")) {
			if(EQ(f, Input_Routine.get_ID("CAR")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 1)
					return CAR(CAR(x));
				else
					throw new Invalid_Exception("> Error in CAR: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("CDR")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 1)
					return CDR(CAR(x));
				else
					throw new Invalid_Exception("> Error in CDR: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("CONS")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return CONS(CAR(x), CAR(CDR(x)));
				else
					throw new Invalid_Exception("> Error in CONS: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("ATOM")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 1)
					return ATOM(CAR(x));
				else 
					throw new Invalid_Exception("> Error in ATOM: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("INT")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 1)
					return INT(CAR(x));
				else 
					throw new Invalid_Exception("> Error in INT: Invalid no. of arguments!");
				
			}
			else if (EQ(f, Input_Routine.get_ID("NULL")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 1)
					return NULL(CAR(x));
				else 
					throw new Invalid_Exception("> Error in NULL: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("EQ")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return EQ(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in EQ: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("PLUS")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return PLUS(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in PLUS: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("MINUS")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return MINUS(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in MINUS: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("TIMES")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return TIMES(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in TIMES: Invalid no. of arguments!");
				
			}
			else if (EQ(f, Input_Routine.get_ID("QUOTIENT")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return QUOTIENT(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in QUOTIENT: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("REMAINDER")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return REMAINDER(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in REMAINDER: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("LESS")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return LESS(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in LESS: Invalid no. of arguments!");
			}
			else if (EQ(f, Input_Routine.get_ID("GREATER")) == Input_Routine.get_ID("T")) {
				if(len_SExp(x) == 2)
					return GREATER(CAR(x), CAR(CDR(x)));
				else 
					throw new Invalid_Exception("> Error in GREATER: Invalid no. of arguments!");
			}
			else {
				return eval(CDR(getVal_dList(f, dList)), addpairs(CAR(getVal_dList(f, dList)), x, aList), dList);
			}
						
		}
		else {
			throw new Invalid_Exception("> Error in apply: 1st argument is not atomic");
		}
		
	}
	
	public static SExp in_aList(SExp exp, SExp aList) {
		SExp tmp = aList;
		while(tmp != null) {
			if(tmp.left.left == exp) {
				return Input_Routine.get_ID("T");
			}
			else
				tmp = tmp.right;
			
		}
		return Input_Routine.get_ID("NIL");
					
	}
	
	public static SExp getVal_aList (SExp exp, SExp aList) throws Invalid_Exception {
		SExp tmp = aList;
		while(tmp != Input_Routine.get_ID("NIL")) {
			if(tmp.left.left == exp) {
				return tmp.left.right;
			}
			else
				tmp = tmp.right;
			
		}
		throw new Invalid_Exception("> Error in getVal_aList: unbound variable");
				
	}
	
	public static SExp getVal_dList (SExp exp, SExp aList) throws Invalid_Exception {
		SExp tmp = aList;
		while(tmp != Input_Routine.get_ID("NIL")) {
			if(tmp.left.left == exp) {
				return tmp.left.right;
			}
			else
				tmp = tmp.right;
			
		}
		throw new Invalid_Exception("> Error in getVal_dList: function "+ exp.name+" not found");
				
	}
	
	public static SExp addpairs(SExp pList, SExp x, SExp aList) throws Invalid_Exception {
		
		while(pList != Input_Routine.get_ID("NIL") && x != Input_Routine.get_ID("NIL")) {
			SExp exp1 = new SExp(SExp_type.NON_ATOM, null);
			SExp tmp = new SExp(SExp_type.NON_ATOM, null);
			
			exp1.left = pList.left;
			exp1.right = x.left;
			tmp.left = exp1;
			tmp.right = aList;
			pList = pList.right;
			x = x.right;
			aList = tmp;
		}
		if(pList != Input_Routine.get_ID("NIL") || x != Input_Routine.get_ID("NIL")) {
			throw new Invalid_Exception("> Error in addpairs: Invalid Input to addpairs");
		}
		return aList;
	}
	
}

