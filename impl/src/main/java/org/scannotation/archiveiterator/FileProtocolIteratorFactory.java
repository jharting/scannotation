package org.scannotation.archiveiterator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.scannotation.spi.StreamIteratorFactory;
import org.scannotation.spi.StreamIterator;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 * @version $Revision: 1 $
 */
public class FileProtocolIteratorFactory implements StreamIteratorFactory {

    public StreamIterator create(URL url, Filter filter) throws IOException {
        File f = new File(url.getPath());
        if (f.isDirectory()) {
            return new FileIterator(f, filter);
        } else {
            return new JarIterator(url.openStream(), filter);
        }
    }

    public String getProtocol() {
        return "file";
    }
}
