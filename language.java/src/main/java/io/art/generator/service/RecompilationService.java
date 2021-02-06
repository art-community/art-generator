package io.art.generator.service;

import javax.tools.FileObject;

public interface RecompilationService {
    void recompile();
    void recompile(Iterable<String> sources);
    FileObject createStubFile(String className);
}
