# ANTLR 4 Grammar For RQL-Parsing

In order to help write more concise and compact code when defining REST services, we use our own RQL-like parser to generate the queries for endpoints (using interceptors).

## Development Setup

You'll need VS Code to efficiently edit, compile and test the ANTLR Lexer and Parser.

There is a `.vscode` directory in the `antlr4` folder containing the proper settings for VS Code.
The `settings.json` contains settings for the ANTLR-plugin ([ANTLR4 grammar syntax support](https://marketplace.visualstudio.com/items?itemName=mike-lischke.vscode-antlr4)) which we use.
The `launch.json` contains a debug-launch setting ready to be opened with `VS Code -> launch/debug -> open file`. Both contains variables you'll probably want to change:

## Version
The version of ANTLR4 used is the version that's registered in your npm-install (the local vscode folder).
Currently, as of this writing, it's 4.9.2, even if there's already a version 4.10.1 out there for some time.
That's because the maintainer of the VsCode plugin hasn't updated it yet (because of TS issues).
You have to use the same version in Java as was used when generating the code.

### settings.json

`outputDir` is the directory (absolute) you want the parser-generator to copy the java-files into.

`package` is the Java fully-qualified-package-name the files it generates should bear.

### launch.json

`input` the filename of the file that is used as input-file when debugging.

`grammar` the grammar-file to start when hitting the debug-key.

`startRule` the main-rule (root) of your grammar.

### Usage

Start up VS Code and open the folder.

You may edit the grammar.

When pressing `F5` a new tab should pop up with the title `Parse Tree: Rql.g4` showing the resulting parse-tree after parsing your grammar (see settings in `launch.json`).



> **! When updating generated Files !**
>
> You have to update the `*.g4` file you use for generation before pressing F5 since the internal caching mechanism will kick in and no files will be generated a second time for the same file.
>
> It suffices to add a space, remove it again and save the file.



You may also go to your grammar file, right click and select either:

- Show Railroad Diagram for Rule
- Show Railroad Diagram for all Rules
- Show ATN Graph for Rule
- Show Grammar Call Graph

Which will open in a new tab, depending on which point you chose, depending on the rule your cursor currently is located in.

After pressing `F5` your grammar is generated and the files are copied.

Change to your Eclipse and refresh the folder the classes are located in, for Eclipse to detect and process the changes.

You may use the visitor now and run some tests.