package filter.compression;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
 

public class CompressionResponseStream extends ServletOutputStream {

    
    public CompressionResponseStream(ServletOutputStream os) throws IOException {
        gzipstream = new GZIPOutputStream(os, true);
        servletOutputStream = os;
    }

    ServletOutputStream servletOutputStream;
    GZIPOutputStream gzipstream;

    @Override
    public void close() throws IOException {
        System.out.println(">close");
        gzipstream.close();
    }

    @Override
    public void flush() throws IOException {
        System.out.println(">flush");
        gzipstream.flush();
    }

    public void finish() throws IOException {
        System.out.println(">finish");
        gzipstream.finish();
    }

    @Override
    public void write(int b) throws IOException {
        System.out.println(">write1");
        gzipstream.write(b);
    }

    @Override
    public void write(byte b[]) throws IOException {
        System.out.println(">write2");
        gzipstream.write(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
    //    System.out.println(">write3");
        gzipstream.write(b, off, len);
    }

    @Override
    public boolean isReady() {
        System.out.println(">isReady");
        return this.servletOutputStream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        System.out.println(">setWriteListener");
        this.servletOutputStream.setWriteListener(writeListener);
    }

}
