package io.art.generator.writer;

import com.sun.tools.javac.tree.*;
import java.io.*;

public class PrettyWriter extends Pretty {
    public PrettyWriter(Writer writer) {
        super(writer, false);
    }
}
