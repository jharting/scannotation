package org.scannotation.test.annotationdb;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.shrinkwrap.api.exporter.ExplodedExporter;

/**
 * Same tests as {@link JarFileTest} but the tested classes are deployed in an exploded directory instead of a jar file.
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * 
 */
public class DirectoryTest extends JarFileTest {

    @Override
    protected URL deployArchive() {
        File parentDirectory = new File("target");
        getTestArchive().as(ExplodedExporter.class).exportExploded(parentDirectory);
        File explodedDirectory = new File(parentDirectory, JarFileTest.ARCHIVE_NAME);
        try {
            return explodedDirectory.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
