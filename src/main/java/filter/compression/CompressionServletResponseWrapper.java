package filter.compression;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CompressionServletResponseWrapper extends HttpServletResponseWrapper {

    public CompressionServletResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    protected HttpServletResponse origResponse;

    protected CompressionResponseStream stream;

    @Override
    public void setContentType(String contentType) {
        origResponse.setContentType(contentType);
    }

    @Override
    public void flushBuffer() throws IOException {
        stream.finish();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        System.out.println(">getOutputStream");
        if (stream == null) {
            origResponse.addHeader("Content-Encoding", "gzip");
            stream = new CompressionResponseStream(origResponse.getOutputStream());
        } else {
            System.out.println(">getOutputStream:stream is not null: " + stream);
        }

        System.out.println("<getOutputStream");
        return stream;
    }

    PrintWriter writer;

    @Override
    public PrintWriter getWriter() throws IOException {
        System.out.println(">getWriter");
        if (writer == null) {
            writer = new PrintWriter(getOutputStream());
        } else {
            System.out.println(">getWriter:writer is not null: " + writer);
        }
        //this.writer = new PrintWriter(new OutputStreamWriter(  this.outputStream, this.getCharacterEncoding()));
        System.out.println("<getWriter");
        return writer;
    }

    public void setContentLength(int length) {
        System.out.println(">setContentLength");
    }

    @Override
    public void setContentLengthLong(long length) {
        System.out.println(">setContentLengthLong");
    }

    @Override
    public void setHeader(String name, String value) {
        if (!"content-length".equalsIgnoreCase(name)) {
            super.setHeader(name, value);
        }
    }

    @Override
    public void addHeader(String name, String value) {
        if (!"content-length".equalsIgnoreCase(name)) {
            super.setHeader(name, value);
        }
    }

    @Override
    public void setIntHeader(String name, int value) {
        if (!"content-length".equalsIgnoreCase(name)) {
            super.setIntHeader(name, value);
        }
    }

    @Override
    public void addIntHeader(String name, int value) {
        if (!"content-length".equalsIgnoreCase(name)) {
            super.setIntHeader(name, value);
        }
    }
}
