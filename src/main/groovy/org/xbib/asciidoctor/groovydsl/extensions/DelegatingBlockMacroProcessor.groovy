package org.xbib.asciidoctor.groovydsl.extensions

import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.BlockMacroProcessor

/**
 *
 */
class DelegatingBlockMacroProcessor extends BlockMacroProcessor {

    private final Closure cl

    DelegatingBlockMacroProcessor(String name, Map options, @DelegatesTo(BlockMacroProcessor) Closure cl) {
        super(name, options)
        this.cl = cl
        cl.delegate = this
    }

    @Override
    Object process(AbstractBlock parent, String target, Map<String, Object> attributes) {
        cl.call(parent, target, attributes)
    }
}
