package io.art.generator.service;

import javax.tools.*;

public interface RecompilationService {
    void recompile();
    void recompile(Iterable<String> sources);
    FileObject createStubFile(String className);
}
