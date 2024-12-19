import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CompilerDriver {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java CompilerDriver <sourceFile> <tokenFile>");
			return;
		}

		Path sourceFile = Paths.get(args[0]);
		Path tokenFile = Paths.get("tokens.temp");
		Path atomFile = Paths.get("atoms.temp");
		Path binaryFile = Paths.get(args[1]);

		HashMap<String, Boolean> argMap = new HashMap<>(Map.of("--enableGlobal", false,
								       "--enableLocal", false));
		
		if (args.length > 2) {
			argMap.put(args[2], true);
		}

		if (args.length > 3) {
			argMap.put(args[3], true);
		}

		var scanner = new Scanner(sourceFile, tokenFile);
		scanner.start();

		var parser = new Parser(tokenFile, atomFile, argMap.get("--enableGlobal"));
		parser.parse();

		var codeGenerator = new CodeGenerator(atomFile, binaryFile, argMap.get("--enableLocal"));
		codeGenerator.codeGeneration();

		System.out.println("Compilation completed successfully.");
	}

}
