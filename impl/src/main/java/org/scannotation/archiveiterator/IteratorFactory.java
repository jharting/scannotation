package org.scannotation.archiveiterator;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.scannotation.spi.StreamIteratorFactory;
import org.scannotation.spi.StreamIterator;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 * @version $Revision: 1 $
 */
public class IteratorFactory {
    private final Map<String, StreamIteratorFactory> registry = new HashMap<String, StreamIteratorFactory>();

    public IteratorFactory() {
        for (Iterator<StreamIteratorFactory> iterator = ServiceLoader.load(StreamIteratorFactory.class).iterator(); iterator
                .hasNext();) {
            StreamIteratorFactory isf = iterator.next();
            registry.put(isf.getProtocol(), isf);
        }
    }

    public StreamIterator create(URL url, Filter filter) throws IOException {

        /* No idea what !/ means ...
        String urlString = url.toString();
        if (urlString.endsWith("!/")) {
            urlString = urlString.substring(4);
            urlString = urlString.substring(0, urlString.length() - 2);
            url = new URL(urlString);
        }
        */

        StreamIteratorFactory factory = registry.get(url.getProtocol());
        if (factory == null)
        {
            throw new IllegalArgumentException("Unknown protocol: " + url.getProtocol());
        }
        return factory.create(url, filter);
    }
}
