package org.scannotation.test;

import org.junit.Assert;
import org.junit.Test;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class AnnotationDbTest
{

   @Test
   public void testFieldParameter() throws Exception
   {
      URL url = ClasspathUrlFinder.findClassBase(AnnotationDbTest.class);
      AnnotationDB db = new AnnotationDB();
      db.scanArchives(url);

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
   public void testCrossRef() throws Exception
   {
      URL url = ClasspathUrlFinder.findClassBase(AnnotationDbTest.class);
      AnnotationDB db = new AnnotationDB();
      db.scanArchives(url);
      db.crossReferenceImplementedInterfaces();

      Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
      Set<String> simpleClasses = annotationIndex.get(SimpleAnnotation.class.getName());
      Assert.assertTrue(simpleClasses.contains(CrossRef.class.getName()));

      Set<String> simpleAnnotations = db.getClassIndex().get(CrossRef.class.getName());
      Assert.assertTrue(simpleAnnotations.contains(SimpleAnnotation.class.getName()));
   }


   @Test
   public void testCrossRefMetaAnnotations() throws Exception
   {
      URL url = ClasspathUrlFinder.findClassBase(AnnotationDbTest.class);
      AnnotationDB db = new AnnotationDB();
      db.scanArchives(url);
      db.crossReferenceMetaAnnotations();

      Assert.assertTrue(db.getAnnotationIndex().get(MetaAnnotation.class.getName()).contains(CrossRefMetaAnnotaiton.class.getName()));
   }
}
