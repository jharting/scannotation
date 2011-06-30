package org.scannotation.spi;

import java.io.IOException;
import java.net.URL;

import org.scannotation.archiveiterator.Filter;

/**
 * Implement this interface to provide support for an additional protocol (VFS, HTTP, ...)
 * 
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public interface StreamIteratorFactory {
    
    StreamIterator create(URL url, Filter filter) throws IOException;

    /**
     * 
     * @return protocol Protocol handled by this StreamIteratorFactory (e.g. "file").
     */
    String getProtocol();
}
