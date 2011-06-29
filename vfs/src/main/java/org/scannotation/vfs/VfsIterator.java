package org.scannotation.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.VirtualFileFilter;
import org.scannotation.spi.StreamIterator;

public class VfsIterator implements StreamIterator {

    private Iterator<VirtualFile> files;

    public VfsIterator(VirtualFile vfile, VirtualFileFilter filter) throws IOException {
        files = vfile.getChildrenRecursively(filter).iterator();
    }

    public InputStream next() {
        if (files.hasNext()) {
            try {
                return files.next().openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    public void close() {
    }
}
