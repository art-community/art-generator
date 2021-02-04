package io.art.generator.service;

public interface RecompilationService {
    void recompile();
    void recompile(Iterable<String> sources);
}
