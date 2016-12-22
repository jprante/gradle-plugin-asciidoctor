package org.xbib.gradle.plugin.asciidoctor

/**
 */
class AsciidoctorProxyImpl implements AsciidoctorProxy {
    def delegate

    @Override
    String renderFile(File filename, Map<String, Object> options) {
        delegate.renderFile(filename, options)
    }

    @Override
    void requireLibrary(String... requiredLibraries) {
        delegate.requireLibrary(requiredLibraries)
    }
}
