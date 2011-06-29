package org.scannotation.vfs;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.VirtualFileFilter;
import org.scannotation.archiveiterator.Filter;
import org.scannotation.spi.StreamIterator;
import org.scannotation.spi.StreamIteratorFactory;

public class VfsIteratorFactory implements StreamIteratorFactory {

    public StreamIterator create(URL url, Filter filter) throws IOException {
        URLConnection connection = url.openConnection();
        Object content = connection.getContent();
        if (content instanceof VirtualFile)
        {
            VirtualFile vfile = (VirtualFile) content;
            return new VfsIterator(vfile, wrapFilter(filter));
        }
        else
        {
            throw new IllegalStateException("Not a VirtualFile " + url.toString());
        }
    }
    
    protected VirtualFileFilter wrapFilter(final Filter filter)
    {
        return new VirtualFileFilter() {
            
            public boolean accepts(VirtualFile file) {
                return filter.accepts(file.getName());
            }
        };
    }

    public String getProtocol() {
        return "vfs";
    }
}
