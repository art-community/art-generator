package io.art.generator.constants;

public interface LoggingMessages {
    String RECOMPILE_ARGUMENTS = "Executing recompilation with arguments: {0}";
    String RECOMPILATION_STARTED = "Recompilation started";
    String RECOMPILATION_COMPLETED = "Recompilation completed";
    String GENERATION_STARTED = "Generation started";
    String GENERATION_COMPLETED = "Generation completed";
    String STUB_GENERATION_STARTED = "Stubs generation started";
    String STUB_GENERATION_COMPLETED = "Stubs generation completed";
    String STUBS_REMOVED = "Removed stubs for existed classes";
    String GENERATED_MAPPER = "Generated mapper for type: {0}";
    String GENERATED_CONFIGURATION_PROXY = "Generated configuration proxy for type: {0}";
    String GENERATED_CLASS = "Generated class: {0}";
    String GENERATED_SERVICE_METHOD_SPECIFICATION = "Generated service method specification: {0}";
    String GENERATED_MAIN_METHOD = "Generated main method for existed main class: {0}";
    String GENERATED_MAPPERS = "All mappers were successfully generated";
    String GENERATED_SERVICE_SPECIFICATIONS = "All service specifications were successfully generated";
    String GENERATED_COMMUNICATOR_PROXIES = "All communicator proxies were successfully generated";
    String GENERATED_CUSTOM_CONFIGURATION_PROXIES = "All custom configuration proxies were successfully generated";
}
