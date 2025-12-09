# LD Parser

[![Build](https://github.com/antoniovazquezblanco/LD-Parser/actions/workflows/main.yml/badge.svg)](https://github.com/antoniovazquezblanco/LD-Parser/actions/workflows/main.yml)
[![CodeQL](https://github.com/antoniovazquezblanco/LD-Parser/actions/workflows/codeql.yml/badge.svg)](https://github.com/antoniovazquezblanco/LD-Parser/actions/workflows/codeql.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.antoniovazquezblanco/ld-parser)](https://central.sonatype.com/artifact/io.github.antoniovazquezblanco/ld-parser)

A Java parser for linker script (LD) format using ANTLR.

## Usage

```java
import io.ldparser.LDParser;
import io.ldparser.Script;
import io.ldparser.Command;
import io.ldparser.ProvideCommand;

import java.io.File;

// Parse from file
Script script = LDParser.parse(new File("script.ld"));

// Access commands
for (Command cmd : script.getCommands()) {
    if (cmd instanceof ProvideCommand) {
        ProvideCommand pc = (ProvideCommand) cmd;
        System.out.println("Symbol: " + pc.getSymbolName() + ", Expression: " + pc.getExpression());
    }
}
```

## Development

Install Eclipse. Once in Eclipse click `File > Open Projects from File System...`. On the import dialog select `LD-Parser` folder and import the project.

You are ready for building, testing and developing.

## Installing

The package is published to [Maven Central](https://central.sonatype.com/artifact/io.github.antoniovazquezblanco/ld-parser) and [Github package repository](https://github.com/antoniovazquezblanco/LD-Parser/packages/).

Those pages provide installation snippets, visit them for more information.
