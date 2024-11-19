package scanner;

/**
 * Authors: Krishna Foster, Nathan Garretson, Matthew Naugle, Ian Schiedenhelm
 * Reviewers: Omar Ndiaye, Wyatt Rock
 */

public class Token {
	public enum TokenClass {
		KEYWORD, OPERATOR, IDENTIFIER, LITERAL, UNKNOWN
	}

	private TokenClass name;
	private Object value;

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
