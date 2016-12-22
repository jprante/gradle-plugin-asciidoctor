package org.xbib.asciidoctor.groovydsl.extensions

import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.InlineMacroProcessor

/**
 *
 */
class DelegatingInlineMacroProcessor extends InlineMacroProcessor {

    private final Closure cl

    DelegatingInlineMacroProcessor(String name, Map options, @DelegatesTo(InlineMacroProcessor) Closure cl) {
        super(name, options)
        this.cl = cl
        cl.delegate = this
    }

    @Override
    Object process(AbstractBlock parent, String target, Map<String, Object> attributes) {
        cl.call(parent, target, attributes)
    }
}
