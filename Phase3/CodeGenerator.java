import java.util.HashMap;

public class CodeGenerator {

	// Op Codes
	static final int CLR = 0x0;
	static final int ADD = 0x1;
	static final int SUB = 0x2;
	static final int MUL = 0x3;
	static final int DIV = 0x4;
	static final int JMP = 0x5;
	static final int CMP = 0x6;
	static final int LOD = 0x7;
	static final int STO = 0x8;
	static final int HLT = 0x9;

	// Registers
	static final int[] fpreg = new int[20];

	// Next available register
	static int r = 0;

	// Program Counter
	static int pc = 0;

	// Label Table
	static HashMap<String, Integer> labelTable = new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * Generates machine code binary. Supports the 32 bit instructions for the 410
	 * architecture in absolute mode.
	 */
	void gen(Integer op, int cmp, int r, int address) {

		// Make a new instruction variable
		// Maybe it's a string we concatenate onto? Maybe a new object we need to make?

		// add opcode as binary
		// op.byteValue()?
		// add 0 and cmp value (0 if opcode not cmp)
		// add register as binary
		// add address as binary

		// write this new instruction to a file, or print it out.

	}

}
