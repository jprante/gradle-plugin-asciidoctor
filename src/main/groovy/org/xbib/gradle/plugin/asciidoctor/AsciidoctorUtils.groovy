package org.xbib.gradle.plugin.asciidoctor

import java.util.regex.Pattern

/**
 *
 */
class AsciidoctorUtils {

    /**
     * Based on
     * http://stackoverflow.com/questions/204784/how-to-construct-a-relative-path-in-java-from-two-absolute-paths-or-urls/1290311#1290311
     *
     * Returns the path of one File relative to another.
     *
     * @param target the target directory
     * @param base the base directory
     * @return target's path relative to the base directory
     * @throws IOException if an error occurs while resolving the files' canonical names
     */
    static String getRelativePath(File target, File base) throws IOException {
        String[] baseComponents = base.canonicalPath.split(Pattern.quote(File.separator))
        String[] targetComponents = target.canonicalPath.split(Pattern.quote(File.separator))

        // skip common components
        int index = 0
        for (; index < targetComponents.length && index < baseComponents.length; ++index) {
            if (!targetComponents[index].equals(baseComponents[index])) {
                break
            }
        }

        StringBuilder result = new StringBuilder()
        if (index != baseComponents.length) {
            // backtrack to base directory
            for (int i = index; i < baseComponents.length; ++i) {
                if (i != index) {
                    result.append(File.separator)
                }
                result.append('..')
            }
        }
        for (int i = index; i < targetComponents.length; ++i) {
            if (i != index) {
                result.append(File.separator)
            }
            result.append(targetComponents[i])
        }

        result.toString()
    }
}
