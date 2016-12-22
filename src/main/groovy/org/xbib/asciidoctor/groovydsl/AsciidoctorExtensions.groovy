package org.xbib.asciidoctor.groovydsl

import org.asciidoctor.Asciidoctor
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

/**
 * An instance of this class holds all extension closure and scripts.
 * It evaluates the blocks and scripts and forwards the extracted extensions
 * to the GroovyExtensionRegistry which is service implementation
 * of org.asciidoctor.extension.spi.ExtensionRegistry
 */
class AsciidoctorExtensions {

    // Container closures and scripts
    // These get evaluated when the ExtensionRegistry
    // is asked to register its extensions
    static final List<Object> REGISTERED_EXTENSIONS = []

    static void extensions(@DelegatesTo(AsciidoctorExtensionHandler) Closure cl) {
        REGISTERED_EXTENSIONS << cl
    }

    static void extensions(String s) {
        REGISTERED_EXTENSIONS << s
    }

    static void extensions(File f) {
        REGISTERED_EXTENSIONS << f
    }

    static void registerTo(Asciidoctor asciidoctor) {
        AsciidoctorExtensionHandler extensionHandler = new AsciidoctorExtensionHandler(asciidoctor)
        for (def it : REGISTERED_EXTENSIONS) {
            DelegatingScript script
            switch (it) {
                case Closure:
                    try {
                        it.delegate = extensionHandler
                        it.call()
                    } catch (e) {
                        REGISTERED_EXTENSIONS.clear()
                        throw new AsciidoctorExtensionException("Error registering extension from class in ${it.class.name}", e)
                    }
                    break
                case String:
                    def shell = makeGroovyShell()
                    script = shell.parse(it)
                    script.setDelegate(extensionHandler)
                    try {
                        script.run()
                    } catch (e) {
                        REGISTERED_EXTENSIONS.clear()
                        throw new AsciidoctorExtensionException('Error registering extension from string', e)
                    }
                    break
                case File:
                    def shell = makeGroovyShell()
                    FileReader reader = new FileReader(it)
                    script = shell.parse(reader, it.name)
                    script.setDelegate(extensionHandler)
                    try {
                        script.run()
                    } catch (e) {
                        REGISTERED_EXTENSIONS.clear()
                        throw new AsciidoctorExtensionException("Error registering extension from file ${it.name}", e)
                    } finally {
                        reader.close()
                    }
                    break
            }
        }
        REGISTERED_EXTENSIONS.clear()
    }

    private static GroovyShell makeGroovyShell() {
        def config = new CompilerConfiguration()

        config.setScriptBaseClass(DelegatingScript.name)

        ImportCustomizer importCustomizer = new ImportCustomizer()
        importCustomizer.addStarImports(
                'org.asciidoctor',
                'org.asciidoctor.ast',
                'org.asciidoctor.extension')

        config.addCompilationCustomizers(importCustomizer)

        new GroovyShell(new Binding(), config)
    }
}