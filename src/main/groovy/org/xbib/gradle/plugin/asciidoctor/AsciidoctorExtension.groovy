package org.xbib.gradle.plugin.asciidoctor

import org.gradle.api.Project

/**
 *
 */
class AsciidoctorExtension {
    String version = '1.5.4.1'

    /**
     * By default the plugin will try to add a default repository to find AsciidoctorJ.
     * For certain cases this approach is not acceptable, the behaviour can be turned off
     * by setting this value to {@code true}
     */
    boolean addDefaultRepositories = true

    final Project project

    AsciidoctorExtension(Project project) {
        this.project = project
    }
}
