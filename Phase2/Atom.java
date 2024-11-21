class Atom {
	enum OpCode {
		ADD, SUB, MUL, DIV, JMP, NEG, LBL, TST, MOV
	}

	private OpCode opCode;
	private Object left, right, result, dest, cmp;

	public Atom(OpCode opCode, Object left, Object right, Object result, Object cmp, Object dest) {
		this.opCode = opCode;
		this.left = left;
		this.right = right;
		this.result = result;
		this.dest = dest;
		this.cmp = cmp;
	}

	public Atom(OpCode opCode, Object left, Object right, Object result) {
		this.opCode = opCode;
		this.left = left;
		this.right = right;
		this.result = result;
		this.cmp = "";
		this.dest = "";
	}

	@Override
	public String toString() {
//		return String.format("(%s, %s, %s, %s, %s, %s)", opCode, left, right, result, dest, cmp);

		// create a new string builder
		StringBuilder str = new StringBuilder();

		// add open parenthesis
		str.append("(");

		// if the field isn't null or empty, add it to atom
		if (this.opCode != null)
			str.append(this.opCode);
		if (this.left != "") {
			str.append(", " + this.left);
		}
		if (this.right != "") {
			str.append(", " + this.right);
		}
		if (this.result != "") {
			str.append(", " + this.result);
		}
		if (this.cmp != "") {
			str.append(", " + this.cmp);
		}
		if (this.dest != "") {
			str.append(", " + this.dest);
		}

		// add the close parenthesis
		str.append(")");

		// return the string builder
		return str.toString();
	}

}