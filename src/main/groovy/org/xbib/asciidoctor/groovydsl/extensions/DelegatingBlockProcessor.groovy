package org.xbib.asciidoctor.groovydsl.extensions

import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.BlockProcessor
import org.asciidoctor.extension.Reader
import org.xbib.asciidoctor.groovydsl.AsciidoctorExtensionHandler

/**
 *
 */
class DelegatingBlockProcessor extends BlockProcessor {

    Closure cl

    DelegatingBlockProcessor(Map<String, Object> attributes, @DelegatesTo(BlockProcessor) Closure cl) {
        super(attributes[AsciidoctorExtensionHandler.OPTION_NAME] as String, attributes)
        this.cl = cl
        cl.delegate = this
    }

    @Override
    Object process(AbstractBlock parent, Reader reader, Map<String, Object> attributes) {
        cl.call(parent, reader, attributes)
    }
}
