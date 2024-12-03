import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CodeGenerator {

	private static final Path BASE = Paths.get("db", "compiler"); // Directories where atom and instruction files exist.
	private Path binaryFile; // File where the machine code will be placed.
	private Path atomFile; // File of Atoms from Parser, will be read in.

	private static Queue<Atom> atoms = new LinkedList<Atom>(); // Queue to hold atoms
	private Atom current; // Current Atom to turn into instruction

	private boolean loop; // boolean to use when looping through atoms to generate instructions (keep
							// generating instructions while there are atoms left).

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
	static HashMap<String, Integer> labelTable = new HashMap<>();

	// Main method for testing
	public static void main(String[] args) {
		CodeGenerator genny = new CodeGenerator();
		genny.codeGeneration();
	}

	/**
	 * Constructor that creates directories and file that will hold the machine code
	 */
	public CodeGenerator() {
		try {
			Files.createDirectories(BASE);
			binaryFile = BASE.resolve("bytes");
			if (Files.notExists(binaryFile))
				Files.createFile(binaryFile);

			atomFile = BASE.resolve("atoms");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Starts the generation process by reading in the atoms from a file, then
	 * processing them.
	 */
	public void codeGeneration() {
		// Get the atoms from the file
		atomFile = BASE.resolve("atoms");
		readAtoms(atomFile);
		System.out.println(atoms); // Print atoms *FOR TESTING PURPOSES*
		getAtom();
		gen(ADD, 0, r, 100);

		// First pass logic to build labels?

		buildLabels();

		// Second pass logic to generate instructions?

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

		// Need to do a mix of OR, AND, and bit shifting to make instruction
		// Value of each parameter will need to be shifted to it's place in instruction
		// ex: 0000000000 (imagine this is 32 bit instruction)
		// 010 (imagine this is opcode value)
		// << shift it over to far left!
		// 0100000000
		// etc. for rest of instruction
		Integer word = 0;
		word |= (op & 0xF) << 28; // add opcode
		word |= (cmp & 0xF) << 24; // add cmp
		word |= (r & 0xF) << 20; // add register
		word |= (address & 0xFFFFF); // add address
		System.out.println(String.format("%32s", Integer.toBinaryString(word)).replace(' ', '0')); // Print instruction
																									// out *FOR TESTING
																									// PURPOSES*

		writeInstruction(word); // write the instruction to the file.
	}

	//First pass
	//Builds a table of labels with their corresponding pc numbers
	public void buildLabels() {
		//Gets initial atom
		getAtom();
		//Loop through queue until it is empty
	        while (loop == true) {
		    //If current atom is a Label, add it to the table
	            if (current.getOpcode().equals(Atom.OpCode.LBL)) {
	                labelTable.put(current.getLeft(), pc);
	            } 
	            //Increment pc based on OpCodes, different OpCodes require varying number of instructions
	            else if (current.getOpcode().equals(Atom.OpCode.MOV)|| current.getOpcode().equals(Atom.OpCode.JMP)) {
	                pc += 2;
	            } 
	            
	            else if (current.getOpcode().equals(Atom.OpCode.NEG)){
	                pc += 3;
	            } 
	            
	            else {
	                pc += 4;
	            }
	            //Get atoms until queue is empty
	            getAtom();
	        }
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
	void writeInstruction(Integer word) {
		try {
			var bytes = ByteBuffer.allocate(32).putInt(word).array();
			Files.write(binaryFile, bytes);
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

	/**
	 * This method attempts to retrieve an atom from the queue. If the queue is
	 * empty, the loop boolean is set to false.
	 */
	void getAtom() {
		try {
			current = atoms.remove();
		} catch (NoSuchElementException e) {
			loop = false;
		}
	}

}
