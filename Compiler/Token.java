
/*
 * Authors: Wyatt Rock, Matthew Naugle, Ian Schiedenhelm
 * Reviewers: Nathan Garretson, Krishna Foster
 */

public class Token {
	public enum TokenClass {
		GT_OP, GE_OP, LT_OP, LE_OP, NE_OP, ASS_OP, EQUAL_OP, OPEN_PAR, CLOSE_PAR, DIV_OP, MULT_OP, SUB_OP, ADD_OP,
		SEMI_OP, DOUBLE_KW, INT_KW, ELSE_KW, IF_KW, WHILE_KW, FOR_KW, INT_LITERAL, DOUBLE_LITERAL, IDENTIFIER, UNKNOWN
	}

	private TokenClass name;
	private Object value;

	// 1-ary constructor
	public Token(TokenClass name) {
		this.name = name;
	}

	// 2-ary constructor
	public Token(TokenClass name, Object value) {
		this.name = name;
		this.value = value;
	}

	public TokenClass getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Token: " + "class=" + name + ", value= '" + value + '\'';
	}

	public static Token parseToken(String line) {
		line = line.trim();

		// Split the line into parts based on ', ' separator
		String[] parts = line.split(", ");

		// Extract the class part
		String classPart = parts[0].split("=")[1]; // Extracts the text after 'class='

		// Extract the value part
		String valuePart = parts[1].split("=")[1].trim(); // Extracts the text after 'value='

		// Remove the single quotes around the value (if present)
		if (valuePart.startsWith("'") && valuePart.endsWith("'")) {
			valuePart = valuePart.substring(1, valuePart.length() - 1);
		}

		// Get the TokenClass from the classPart
		TokenClass tokenClass = TokenClass.valueOf(classPart);

		// Check if the value is "null" and set the value accordingly
		Object value = "null".equals(valuePart) ? null : valuePart;

		// If the TokenClass requires a numeric value, cast appropriately
		if (tokenClass == TokenClass.INT_LITERAL) {
			value = Integer.parseInt(value.toString());
		} else if (tokenClass == TokenClass.DOUBLE_LITERAL) {
			value = Double.parseDouble(value.toString());
		}

		// Return the new Token
		return value == null ? new Token(tokenClass) : new Token(tokenClass, value);
	}

}
