package org.xbib.gradle.plugin.asciidoctor;

import java.io.File;
import java.util.Map;

/**
 */
public interface AsciidoctorProxy {
    String renderFile(File filename, Map<String, Object> options);

    void requireLibrary(String... requiredLibraries);
}
