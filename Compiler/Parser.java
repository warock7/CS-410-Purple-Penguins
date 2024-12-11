import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser {
	// Fields
	private static Queue<Token> tokens = new LinkedList<Token>();
	private static Token current;
	private static List<Atom> atoms = new ArrayList<>();

	private static final Path BASE = Paths.get("db", "compiler"); // Directories where atom and instruction files exist.
	private Path atomFile; // File of Atoms from Parser, will be written to.
	private Path tokenFile;

	private static int tempCounter = 0;
	private static int labelCounter = 0;

	public static void main(String[] args) {

		/*
		 * This main method sets up a Queue of Tokens with an example statement, Parses
		 * them, and prints out the resulting atoms.
		 */

		// x = - 7;
//		tokens.add(new Token(Token.TokenClass.INT_KW));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
////		tokens.add(new Token(Token.TokenClass.ASS_OP));
////		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "-7"));
////		tokens.add(new Token(Token.TokenClass.SEMI_OP));
////
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
////		tokens.add(new Token(Token.TokenClass.ASS_OP));
////		tokens.add(new Token(Token.TokenClass.SUB_OP));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "y"));
////		tokens.add(new Token(Token.TokenClass.SEMI_OP));
//////
//////		// int n = 0;
////		tokens.add(new Token(Token.TokenClass.INT_KW));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "n"));
////		tokens.add(new Token(Token.TokenClass.ASS_OP));
////		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "0"));
////		tokens.add(new Token(Token.TokenClass.SEMI_OP));
//
//		// set up tokens queue
//		// while (x < 10) (
//		tokens.add(new Token(Token.TokenClass.WHILE_KW));
//		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
//		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "x"));
//		tokens.add(new Token(Token.TokenClass.LT_OP));
//		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "10"));
//		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));
//		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
////
////		// n = i * j;
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "n"));
//		tokens.add(new Token(Token.TokenClass.ASS_OP));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "i"));
//		tokens.add(new Token(Token.TokenClass.MULT_OP));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "j"));
//		tokens.add(new Token(Token.TokenClass.SEMI_OP));
////
//////		x = x / 1;
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
//		tokens.add(new Token(Token.TokenClass.ASS_OP));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
//		tokens.add(new Token(Token.TokenClass.ADD_OP));
//		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "1"));
//		tokens.add(new Token(Token.TokenClass.SEMI_OP));
////
////		//
////
////		// )
//		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));
////
////		// for (int i = 0; i > j; i + 1) (x = i * j;)
////		tokens.add(new Token(Token.TokenClass.FOR_KW));
////		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
////		tokens.add(new Token(Token.TokenClass.INT_KW));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "i"));
////		tokens.add(new Token(Token.TokenClass.ASS_OP));
////		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "0"));
////		tokens.add(new Token(Token.TokenClass.SEMI_OP));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "i"));
////		tokens.add(new Token(Token.TokenClass.GT_OP));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "j"));
////		tokens.add(new Token(Token.TokenClass.SEMI_OP));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "i"));
////		tokens.add(new Token(Token.TokenClass.ADD_OP));
////		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "1"));
////		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));
////
////		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
////		tokens.add(new Token(Token.TokenClass.ASS_OP));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "i"));
////		tokens.add(new Token(Token.TokenClass.MULT_OP));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "j"));
////		tokens.add(new Token(Token.TokenClass.SEMI_OP));
////		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));
//
//		// if (x == 32)
////		tokens.add(new Token(Token.TokenClass.IF_KW));
////		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
////		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
////		tokens.add(new Token(Token.TokenClass.EQUAL_OP));
////		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "32"));
////		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));
////		// (x = x / 1;)
////		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
//		tokens.add(new Token(Token.TokenClass.ASS_OP));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
//		tokens.add(new Token(Token.TokenClass.DIV_OP));
//		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "1"));
//		tokens.add(new Token(Token.TokenClass.SEMI_OP));
//		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));

//		tokens.add(new Token(Token.TokenClass.ELSE_KW));
//		tokens.add(new Token(Token.TokenClass.OPEN_PAR));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
//		tokens.add(new Token(Token.TokenClass.ASS_OP));
//		tokens.add(new Token(Token.TokenClass.IDENTIFIER, "x"));
//		tokens.add(new Token(Token.TokenClass.MULT_OP));
//		tokens.add(new Token(Token.TokenClass.INT_LITERAL, "1"));
//		tokens.add(new Token(Token.TokenClass.SEMI_OP));
//		tokens.add(new Token(Token.TokenClass.CLOSE_PAR));

		Parser par = new Parser();
		par.parse();

	}

	public Parser() {
		try {
			Files.createDirectories(BASE);
			atomFile = BASE.resolve("atoms");
			Files.deleteIfExists(atomFile);
			Files.createFile(atomFile);

			tokenFile = BASE.resolve("tokens");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void parse() {

		readTokens(tokenFile);

		// Remove the first token, and start parsing
		current = tokens.remove();

		try {
			statement();
		} catch (IllegalArgumentException e) {
			System.err.print("Program Rejected\nUnrecognized token: " + current + "\n");
			return;
		}

		// Print out the atoms made during the Parse
		writeAtoms();
		// Parsing Worked!
		System.out.println("Parsing complete");
	}

	public void statement() {
		while (!tokens.isEmpty()) {

			// if peek and see 'while' or 'for'
			if (peek(Token.TokenClass.WHILE_KW) || (peek(Token.TokenClass.FOR_KW))) {
				// call loop
				loop();
			}

			// if peek and see 'if'
			else if (peek(Token.TokenClass.IF_KW)) {
				// call condition
				condition();
			} else if (accept(Token.TokenClass.ELSE_KW)) {
				Else();
			}

			// if peek and see 'int' or 'double'
			else if (peek(Token.TokenClass.INT_KW) || (peek(Token.TokenClass.DOUBLE_KW))) {
				// call declaration
				declaration();
			}

			// if peek and see 'identifier'
			else if (peek(Token.TokenClass.IDENTIFIER)) {
				// call assignment
				assignment();
			}

			// else we didn't see anything we like, reject
			// throw exception here?
			else {
				throw new IllegalArgumentException("Unrecognized token: " + current.getName());
			}
		}
		// end of input, and valid statement?
	}

	public void loop() {
		if (accept(Token.TokenClass.WHILE_KW)) {
			// call while method
			While();
		} else {
			// expect "for" terminal
			expect(Token.TokenClass.FOR_KW);
			// call for method
			For();
		}
	}

	public void While() {
		String loopStartLabel = getLabel();
		String loopEndLabel = getLabel();

		atoms.add(new Atom(Atom.OpCode.LBL, " ", " ", " ", " ", loopStartLabel));

		// open parenthesis
		expect(Token.TokenClass.OPEN_PAR);
		comparisonExpr(loopEndLabel);
		// closed parenthesis
		expect(Token.TokenClass.CLOSE_PAR);
		expect(Token.TokenClass.OPEN_PAR);
		statement();
		// closed parenthesis
//		expect(Token.TokenClass.CLOSE_PAR);

		atoms.add(new Atom(Atom.OpCode.JMP, " ", " ", " ", " ", loopStartLabel));
		atoms.add(new Atom(Atom.OpCode.LBL, " ", " ", " ", " ", loopEndLabel));
	}

	public void For() {
		String loopStartLabel = getLabel();
		String loopEndLabel = getLabel();

		atoms.add(new Atom(Atom.OpCode.LBL, " ", " ", " ", " ", loopStartLabel));

		// need an open parenthesis
		expect(Token.TokenClass.OPEN_PAR);
		declaration();
		comparisonExpr(loopEndLabel);
		// need a semi-colon
		expect(Token.TokenClass.SEMI_OP);
		expr();
		// need a close parenthesis
		expect(Token.TokenClass.CLOSE_PAR);

		expect(Token.TokenClass.OPEN_PAR);
		statement();
//		expect(Token.TokenClass.CLOSE_PAR);

		atoms.add(new Atom(Atom.OpCode.JMP, " ", " ", " ", " ", loopStartLabel));
		atoms.add(new Atom(Atom.OpCode.LBL, " ", " ", " ", " ", loopEndLabel));
	}

	public void condition() {
		String goToElse = getLabel();
		String skipElse = getLabel();
		// can accept an if keyword
		if (accept(Token.TokenClass.IF_KW)) {

			// need an open parenthesis
			expect(Token.TokenClass.OPEN_PAR);
			comparisonExpr(goToElse);
			// need a closed parenthesis
			expect(Token.TokenClass.CLOSE_PAR);

			expect(Token.TokenClass.OPEN_PAR);
			statement();
			atoms.add(new Atom(Atom.OpCode.JMP, " ", " ", " ", " ", skipElse));
			// expect(Token.TokenClass.CLOSE_PAR);

			atoms.add(new Atom(Atom.OpCode.LBL, " ", " ", " ", " ", goToElse));
		}
//		if (accept(Token.TokenClass.ELSE_KW)) {
//			Else();
//			// expect(Token.TokenClass.CLOSE_PAR);
//
//		}

		atoms.add(new Atom(Atom.OpCode.LBL, " ", " ", " ", " ", skipElse));

	}

	public void If() {

	}

	public void Else() {
		expect(Token.TokenClass.OPEN_PAR);
		statement();
	}

	public void comparisonExpr(String dest) {
		String left = expr();
		int cmp = comparisonOp();
		String right = expr();
		atoms.add(new Atom(Atom.OpCode.TST, left, right, " ", cmp, dest));
	}

	// different than what we had in abstract machine,
	// was initially splitting up ==, !=, etc.
	public int comparisonOp() {
		if (accept(Token.TokenClass.EQUAL_OP)) {
			// accept advances to next token
			// check for greater than or less than
			return 6;
		} else if (accept(Token.TokenClass.NE_OP)) {
			return 1;
		} else if (accept(Token.TokenClass.GT_OP)) {
			return 4;
		} else if (accept(Token.TokenClass.LT_OP)) {
			return 5;
		} else if (accept(Token.TokenClass.LE_OP)) {
			return 3;
		} else {
			// else we need to see great than equal token
			expect(Token.TokenClass.GE_OP);
			return 2;
		}
	}

	public void assignment() {
		// check for an identifier
		var destination = current.getValue();
		if (accept(Token.TokenClass.IDENTIFIER)) {
			// need an assignment operator
			expect(Token.TokenClass.ASS_OP);
			// if (accept(SUB_OP)
			// if (peek(IDENTIFER))
			// build a NEG atom
			if (accept(Token.TokenClass.SUB_OP)) {
				if (peek(Token.TokenClass.IDENTIFIER)) {
					// grab identifier value
					var left = current.getValue();
					advanceToken();
					// build atom
					atoms.add(new Atom(Atom.OpCode.NEG, left, " ", destination));
					expect(Token.TokenClass.SEMI_OP);
					return;
				}
			}

			String tempSource = expr();
			expect(Token.TokenClass.SEMI_OP);
			atoms.add(new Atom(Atom.OpCode.MOV, tempSource, " ", destination));
			accept(Token.TokenClass.CLOSE_PAR);
		}
	}

	public void declaration() {
		type();
		var destination = current.getValue();
		expect(Token.TokenClass.IDENTIFIER);
		if (accept(Token.TokenClass.ASS_OP)) {

			String tempSource = expr();
			atoms.add(new Atom(Atom.OpCode.MOV, tempSource, " ", destination));
		}
		expect(Token.TokenClass.SEMI_OP);

	}

	public void type() {
		if (accept(Token.TokenClass.INT_KW)) {
			// accept advances to next terminal
		} else {
			expect(Token.TokenClass.DOUBLE_KW);
		}
	}

	public String expr() {
		String temp1 = term();
		return exprTail(temp1);
	}

	public String exprTail(String temp1) {
		if (peek(Token.TokenClass.ADD_OP) || peek(Token.TokenClass.SUB_OP)) {
			Token.TokenClass op = current.getName();
			advanceToken(); // Advance past the operator
			String temp2 = term();
			String resultTemp = getTemp();
			if (op == Token.TokenClass.ADD_OP) {
				atoms.add(new Atom(Atom.OpCode.ADD, temp1, temp2, resultTemp));
			} else {
				atoms.add(new Atom(Atom.OpCode.SUB, temp1, temp2, resultTemp));
			}
			return exprTail(resultTemp);
		} else {
			return temp1;
		}
	}

	public String term() {
		String temp1 = factor();
		return termTail(temp1);
	}

	public String termTail(String temp1) {
		if (peek(Token.TokenClass.MULT_OP) || peek(Token.TokenClass.DIV_OP)) {
			Token.TokenClass op = current.getName();
			advanceToken(); // Advance past the operator
			String temp2 = factor();
			String resultTemp = getTemp();
			if (op == Token.TokenClass.MULT_OP) {
				atoms.add(new Atom(Atom.OpCode.MUL, temp1, temp2, resultTemp));
			} else {
				atoms.add(new Atom(Atom.OpCode.DIV, temp1, temp2, resultTemp));
			}
			return termTail(resultTemp);
		} else {
			return temp1;
		}
	}

	public String factor() {
		if (current == null) {
			throw new IllegalArgumentException("Expected identifier, literal, or '(' but reached end of input.");
		}
		if (current.getName() == Token.TokenClass.IDENTIFIER) {
			String value = (String) current.getValue();
			advanceToken();
			return value;
		} else if (current.getName() == Token.TokenClass.INT_LITERAL
				|| current.getName() == Token.TokenClass.DOUBLE_LITERAL) {
			String value = (String) current.getValue();
			advanceToken();
			return value;
		} else if (accept(Token.TokenClass.OPEN_PAR)) {
			String temp = expr();
			expect(Token.TokenClass.CLOSE_PAR);
			return temp;
		} else {
			throw new IllegalArgumentException("Expected identifier, literal, or '(' but found " + current.getName());
		}
	}

	public boolean accept(Token.TokenClass name) {
		// if match
		if (current != null && current.getName() == name) {
			// advance, return true
			advanceToken();
			return true;
		}
		// else, retain, return false
		return false;
	}

	public Token expect(Token.TokenClass name) {
		// if match
		if (current != null && current.getName() == name) {
			// advance
			Token previous = current;
			advanceToken();
			return previous;
		}
		// else, reject
		throw new IllegalArgumentException("Expected token " + name + " but found " + current.getName());
	}

	public boolean peek(Token.TokenClass name) {
		return current != null && current.getName().equals(name);
	}

	private void advanceToken() {
		if (!tokens.isEmpty()) {
			current = tokens.remove();
		} else {
			current = null;
		}
	}

	public String getTemp() {
		return "T" + tempCounter++;
	}

	public String getLabel() {
		return "L" + labelCounter++;
	}

	/**
	 * Writes the Atoms to the file that were generated by the Parser. Converts the
	 * List<Atom> into List<String> so it can be written using Files.write method.
	 */
	public void writeAtoms() {

		List<String> atomStrings = new ArrayList<>();
		for (Atom atom : atoms) {
			atomStrings.add(atom.toString());
		}
		try {
			Files.write(atomFile, atomStrings);
		} catch (IOException e) {
			throw new RuntimeException("Failed to write atoms to file", e);
		}
	}

	public void readTokens(Path tokenFile) {
		try {
			var lines = Files.readAllLines(tokenFile);
			for (String line : lines) { // Loop through every line
				tokens.add(Token.parseToken(line));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
