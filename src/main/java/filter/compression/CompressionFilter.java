package filter.compression;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

public class CompressionFilter implements Filter {

   
    static int MIN_COMPRESSION_THRESHOLD = 512;
    int compressionThreshold;

    @Override
    public void init(FilterConfig filterConfig) {

        String str = filterConfig.getInitParameter("compressionThreshold");
        if (str != null) {
            try {
                compressionThreshold = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
                compressionThreshold = MIN_COMPRESSION_THRESHOLD;
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println(">XXX doFilter: uri=" + req.getRequestURI() + "; Accept-Encoding=" + req.getHeader("Accept-Encoding"));
        if (!req.getHeader("Accept-Encoding").contains("gzip")) {
            System.out.println("no compression");
            chain.doFilter(request, response);
        } else {
            CompressionServletResponseWrapper wrappedResponse = new CompressionServletResponseWrapper((HttpServletResponse) response);
            System.out.println("compressing");
            chain.doFilter(request, wrappedResponse);
            wrappedResponse.flushBuffer();
        }
        System.out.println("<doFilter");
    }
}
