package org.scannotation.spi;

import java.io.IOException;
import java.net.URL;

import org.scannotation.archiveiterator.Filter;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * @version $Revision: 1 $
 */
public interface StreamIteratorFactory {
    StreamIterator create(URL url, Filter filter) throws IOException;

    /**
     * 
     * @return protocol Protocol handled by this StreamIteratorFactory (e.g. "file").
     */
    String getProtocol();
}
