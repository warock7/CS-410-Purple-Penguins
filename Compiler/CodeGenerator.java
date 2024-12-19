import static java.nio.file.StandardOpenOption.APPEND;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class CodeGenerator {

//	private static final Path BASE = Paths.get("db", "compiler"); // Directories where atom and instruction files exist.
	private Path binaryFile; // File where the machine code will be placed.
	private Path atomFile; // File of Atoms from Parser, will be read in.

	private static Queue<Atom> atoms = new LinkedList<Atom>(); // Queue to hold atoms

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

	// Next available register
	static int r = 0; // when making atoms, will need a number of registers to complete instructions,
						// then once done, can just reset and use for next instructions

	// Program Counter
	static int pc = 0; // probably good enough name, just using to keep track of label values

	// Label Table
	// Used during first and second passes
	static HashMap<Object, Integer> labelTable = new HashMap<>();

	// Address Table
	// Used during the second pass
	static HashMap<Object, Integer> addressTable = new LinkedHashMap<>();

	// Main method for testing
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java CodeGenerator <atomFile> <binaryFile>");
			return;
		}

		Path atomFile = Paths.get(args[0]);
		Path binaryFile = Paths.get(args[1]);

		CodeGenerator codeGenerator = new CodeGenerator(atomFile, binaryFile);
		codeGenerator.codeGeneration();
	}

	/**
	 * Constructor that creates directories and file that will hold the machine code
	 */
	public CodeGenerator(Path atomFile, Path binaryFile) {
		this.atomFile = atomFile;
		this.binaryFile = binaryFile;

		try {
			Files.deleteIfExists(binaryFile);
			Files.createFile(binaryFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Starts the generation process by reading in the atoms from a file, then
	 * processing them.
	 */
	public void codeGeneration() {
		readAtoms(atomFile);
		System.out.println(atoms); // Print atoms *FOR TESTING PURPOSES*

		// First pass logic to build labels
		buildLabels();

		pc++;

		// Second pass logic to generate instructions
		secondPass();

		// Write variables and constants to file
		addressTable.entrySet().stream().map(kv -> {
			Object k = kv.getKey();
			if (k instanceof Integer i) {
				return i;
			} else {
				Double d = null;
			        try {
				        d = Double.valueOf(k.toString());
			        } catch (NumberFormatException e) {
				        d = 0.0;
			        }
			        return Float.floatToIntBits(d.floatValue());
			}
		}).forEach(i -> writeInstruction(i));

		System.out.println(labelTable);
	}

	/**
	 * Generates machine code binary. Supports the 32 bit instructions for the 410
	 * architecture in absolute mode.
	 * 
	 * @param op      - Opcode bits of the instruction. Can be values (0-9).
	 * @param cmp     - Compare bits of the instruction. Can be values (0-6). Value
	 *                is 0 unless making CMP instruction.
	 * @param r       - register used in the instruction.
	 * @param address - address to store the result.
	 */
	public void gen(int op, int cmp, int r, int address) {
		int word = 0;
		word |= (op & 0xF) << 28; // add opcode
		word |= (cmp & 0xF) << 24; // add cmp
		word |= (r & 0xF) << 20; // add register
		word |= (address & 0xFFFFF); // add address
		System.out.printf("%d, %d, %d, %d\t", op, cmp, r, address);
		System.out.println(String.format("%32s", Integer.toBinaryString(word)).replace(' ', '0')); // Print instruction
																									// out *FOR TESTING
																									// PURPOSES*

		writeInstruction(word); // write the instruction to the file.
	}

	// First pass
	// Builds a table of labels with their corresponding pc numbers
	public void buildLabels() {
		// Loop through queue
		for (var current : atoms) {
			// If current atom is a Label, add it to the table
			if (current.getOpcode().equals(Atom.OpCode.LBL)) {
				labelTable.put(current.getDest(), pc);
			}
			// Increment pc based on OpCodes, different OpCodes require varying number of
			// instructions
			else if (current.getOpcode().equals(Atom.OpCode.MOV) || current.getOpcode().equals(Atom.OpCode.JMP)) {
				pc += 2;
			}

			else {
				pc += 3;
			}
		}
	}

	// Second pass
	// Generates the code while using the label table to fill in labels
	@SuppressWarnings("incomplete-switch")
	public void secondPass() {

		r = 1;
		// Loop through the queue until it is empty
		while (atoms.size() > 0) {

			var current = atoms.poll();
			// If left, right, or result do not have addresses, give them one
			Stream.of(current.getLeft(), current.getRight(), current.getResult())
					.filter(value -> value != null && value != "" && addressTable.get(value) == null)
					.forEach(value -> addressTable.put(value, pc++));
			// Write instructions according to the current opcode
			switch (current.getOpcode()) {
			case ADD -> {
				gen(LOD, 0, r, addressTable.get(current.getLeft()));
				gen(ADD, 0, r, addressTable.get(current.getRight()));
				gen(STO, 0, r, addressTable.get(current.getResult()));
			}
			case SUB -> {
				gen(LOD, 0, r, addressTable.get(current.getLeft()));
				gen(SUB, 0, r, addressTable.get(current.getRight()));
				gen(STO, 0, r, addressTable.get(current.getResult()));
			}
			case NEG -> {
				gen(CLR, 0, r, 0);
				gen(SUB, 0, r, addressTable.get(current.getLeft()));
				gen(STO, 0, r, addressTable.get(current.getResult()));
			}
			case MUL -> {
				gen(LOD, 0, r, addressTable.get(current.getLeft()));
				gen(MUL, 0, r, addressTable.get(current.getRight()));
				gen(STO, 0, r, addressTable.get(current.getResult()));
			}
			case DIV -> {
				gen(LOD, 0, r, addressTable.get(current.getLeft()));
				gen(DIV, 0, r, addressTable.get(current.getRight()));
				gen(STO, 0, r, addressTable.get(current.getResult()));
			}
			case JMP -> {
				gen(CMP, 0, 0, 0);
				gen(JMP, 0, 0, labelTable.get(current.getDest()));
			}
			case TST -> {
				gen(LOD, 0, r, addressTable.get(current.getLeft()));
				gen(CMP, (int) current.getCmp(), r, addressTable.get(current.getRight()));
				gen(JMP, 0, 0, labelTable.get(current.getDest()));
			}
			case MOV -> {
				gen(LOD, 0, r, addressTable.get(current.getLeft()));
				gen(STO, 0, r, addressTable.get(current.getResult()));
			}
			}
		}
		gen(HLT, 0, 0, 0);
	}

	// To output bytes to file, many options on how to do so.
	// Could use byte buffer to hold bytes and write to file
	// Could use byte output stream? (find API)
	// Could probably find an API that converts Strings into byte arrays (if wanted
	// to do String concat to build instructions
	// But overall, each instruction is 32 bits, and when write to file, all will be
	// on one line (Like 210 Module)

	/**
	 * Write the given 32-bit word to the binary file.
	 * 
	 * @param word - instruction to write to the file
	 */
	void writeInstruction(int word) {
		try {
			var bytes = ByteBuffer.allocate(4).putInt(word).array();
			Files.write(binaryFile, bytes, APPEND);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * This method reads in all the Atom lines from a file, then decodes each line
	 * into an Atom and adds it to a queue.
	 */
	void readAtoms(Path atomFile) {
		try {

			var lines = Files.readAllLines(atomFile); //
			for (String line : lines) { // Loop through every line
				atoms.add(Atom.parseAtom(line)); // Parse line into Atom and add to queue
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
