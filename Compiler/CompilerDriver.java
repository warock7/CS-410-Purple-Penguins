import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CompilerDriver {

	private static String usage = "Usage: java CompilerDriver <sourceFile> <binaryFile> [--enableLocal] [--enableGlobal]";
	private static String enableLocalString = "--enableLocal";
	private static String enableGlobalString = "--enableGlobal";

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println(usage);
			return;
		}

		Path sourceFile = Paths.get(args[0]);
		Path tokenFile = Paths.get("tokens.temp");
		Path atomFile = Paths.get("atoms.temp");
		Path binaryFile = Paths.get(args[1]);

		HashMap<String, Boolean> argMap = new HashMap<>(Map.of(enableGlobalString, false, enableLocalString, false));

		// Check to see if user passed in an optimization flag
		if (args.length > 2) {
			if (args[2].equals(enableGlobalString) || args[2].equals(enableLocalString)) {
				argMap.put(args[2], true);
			} else {
				System.out.println("Invalid option: " + args[2]);
				System.out.println(usage);
				return;
			}
		}

		// Check to see if user passed in an another optimization flag
		if (args.length > 3) {
			if (args[3].equals(enableGlobalString) || args[3].equals(enableLocalString)) {
				argMap.put(args[3], true);
			} else {
				System.out.println("Invalid option: " + args[3]);
				System.out.println(usage);
				return;
			}
		}

		var scanner = new Scanner(sourceFile, tokenFile);
		scanner.start();

		var parser = new Parser(tokenFile, atomFile, argMap.get(enableGlobalString));
		parser.parse();

		var codeGenerator = new CodeGenerator(atomFile, binaryFile, argMap.get(enableLocalString));
		codeGenerator.codeGeneration();

	}

}
