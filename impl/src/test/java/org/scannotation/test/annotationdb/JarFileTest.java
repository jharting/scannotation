package org.scannotation.test.annotationdb;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scannotation.AnnotationDB;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * @version $Revision: 1 $
 */
public class JarFileTest {

    public static final String ARCHIVE_NAME = "test.jar";
    public static final String DEPLOY_PATH = "target/jarFileTest.jar";
    
    protected URL testedArchiveUrl;

    protected JavaArchive getTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME).addPackage(this.getClass().getPackage());
    }

    /**
     * This looks weird, I know, but JUnit won't let you define a non-static class-specific initializer.
     */
    @Before
    public void prepareArchive() {
        if (testedArchiveUrl == null) {
            testedArchiveUrl = deployArchive();
        }
    }
    
    /**
     * Create the test archive and place it in the target directory.
     */
    protected URL deployArchive() {
        File testFile = new File(DEPLOY_PATH);
        getTestArchive().as(ZipExporter.class).exportTo(testFile, true);
        try {
            return testFile.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFieldParameter() throws Exception {
        AnnotationDB db = new AnnotationDB();
        db.scanArchives(testedArchiveUrl);

        Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
        Set<String> simpleClasses = annotationIndex.get(SimpleAnnotation.class.getName());
        Assert.assertTrue(simpleClasses.contains(ClassWithFieldAnnotation.class.getName()));
        Assert.assertTrue(simpleClasses.contains(InterfaceWithParameterAnnotations.class.getName()));

        Set<String> simpleAnnotations = db.getClassIndex().get(ClassWithFieldAnnotation.class.getName());
        Assert.assertTrue(simpleAnnotations.contains(SimpleAnnotation.class.getName()));
        simpleAnnotations = db.getClassIndex().get(InterfaceWithParameterAnnotations.class.getName());
        Assert.assertTrue(simpleAnnotations.contains(SimpleAnnotation.class.getName()));
    }

    @Test
    public void testCrossRef() throws Exception {
        AnnotationDB db = new AnnotationDB();
        db.scanArchives(testedArchiveUrl);
        db.crossReferenceImplementedInterfaces();

        Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
        Set<String> simpleClasses = annotationIndex.get(SimpleAnnotation.class.getName());
        Assert.assertTrue(simpleClasses.contains(CrossRef.class.getName()));

        Set<String> simpleAnnotations = db.getClassIndex().get(CrossRef.class.getName());
        Assert.assertTrue(simpleAnnotations.contains(SimpleAnnotation.class.getName()));
    }

    @Test
    public void testCrossRefMetaAnnotations() throws Exception {
        AnnotationDB db = new AnnotationDB();
        db.scanArchives(testedArchiveUrl);
        db.crossReferenceMetaAnnotations();

        Assert.assertTrue(db.getAnnotationIndex().get(MetaAnnotation.class.getName())
                .contains(CrossRefMetaAnnotaiton.class.getName()));
    }
}
