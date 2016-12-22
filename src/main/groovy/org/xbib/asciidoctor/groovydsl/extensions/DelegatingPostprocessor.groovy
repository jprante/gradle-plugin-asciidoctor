package org.xbib.asciidoctor.groovydsl.extensions

import org.asciidoctor.ast.Document
import org.asciidoctor.extension.Postprocessor

/**
 *
 */
class DelegatingPostprocessor extends Postprocessor {

    final Closure cl
    
    DelegatingPostprocessor(Map options, @DelegatesTo(Postprocessor) Closure cl) {
        super(options)
        this.cl = cl
        cl.delegate = this
    }

    @Override
    String process(Document document, String output) {
        cl.call(document, output)
    }
}
