
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
}
