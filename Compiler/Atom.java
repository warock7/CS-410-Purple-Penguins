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
		this.cmp = cmp;
		this.dest = dest;
	}

	public Atom(OpCode opCode, Object left, Object right, Object result) {
		this.opCode = opCode;
		this.left = left;
		this.right = right;
		this.result = result;
		this.cmp = "";
		this.dest = "";
	}

	public OpCode getOpcode() {
		return this.opCode;
	}

	public Object getLeft() {
		return this.left;
	}

	public Object getRight() {
		return this.right;
	}

	public Object getResult() {
		return this.result;
	}

	public Object getCmp() {
		return this.cmp;
	}

	public Object getDest() {
		return this.dest;
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

	/**
	 * Static method to parse an Atom from a string
	 * 
	 * @param line
	 * @return
	 */
	public static Atom parseAtom(String line) {
		line = line.trim();

		// Remove parentheses
		if (line.startsWith("(") && line.endsWith(")")) {
			line = line.substring(1, line.length() - 1);
		}

		// Split by commas
		String[] parts = line.split(",");

		// Process each part
		OpCode opCode = OpCode.valueOf(parts[0].trim());

		Object left = parseObject(parts[1].trim());
		Object right = parseObject(parts[2].trim());
		Object result = parseObject(parts[3].trim());
		Object cmp = (parts.length > 4) ? parseObject(parts[4].trim()) : "";
		Object dest = (parts.length > 5) ? parseObject(parts[5].trim()) : "";

		// Create and return the Atom
		return new Atom(opCode, left, right, result, cmp, dest);
	}

	/**
	 * Helper method to parse the Object fields
	 * 
	 * @param part
	 * @return
	 */
	private static Object parseObject(String part) {
		if (part.equals(""))
			return "";
		try {
			return Integer.parseInt(part); // Try parsing as Integer
		} catch (NumberFormatException e) {
			return part; // If it's not an integer, return it as a String
		}
	}
}