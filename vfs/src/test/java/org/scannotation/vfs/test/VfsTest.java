package org.scannotation.vfs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.junit.Test;
import org.scannotation.AnnotationDB;

public class VfsTest {

    @Test
    public void test() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("org/scannotation/vfs/test");

        AnnotationDB db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        db.setScanFieldAnnotations(false);
        db.setScanMethodAnnotations(false);
        db.setScanParameterAnnotations(false);
        db.scanArchives(url);

        Set<String> classes = db.getAnnotationIndex().get(Fast.class.getName());
        assertNotNull(classes);
        assertEquals(2, classes.size());
        assertTrue(classes.contains(Airplane.class.getName()));
        assertTrue(classes.contains(Car.class.getName()));
    }
}
