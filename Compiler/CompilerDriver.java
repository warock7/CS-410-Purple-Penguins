import java.nio.file.Path;
import java.nio.file.Paths;

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

		Scanner scanner = new Scanner(sourceFile, tokenFile);
		scanner.start();

		Parser parser = new Parser(tokenFile, atomFile);
		parser.parse();

		CodeGenerator codeGenerator = new CodeGenerator(atomFile, binaryFile);
		codeGenerator.codeGeneration();

		System.out.println("Compilation completed successfully.");
	}

}
