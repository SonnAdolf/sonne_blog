package sonn.filter;      
    
import java.io.IOException;      
    
import javax.servlet.Filter;      
import javax.servlet.FilterChain;      
import javax.servlet.FilterConfig;      
import javax.servlet.ServletException;      
import javax.servlet.ServletRequest;      
import javax.servlet.ServletResponse;      
    
    
    
public class MyFilter implements Filter {      
    
public void destroy() {      
// TODO 自动生成方法存根      
    
}      
    
public void doFilter(ServletRequest request, ServletResponse response,      
FilterChain fc) throws IOException, ServletException {      
// TODO 自动生成方法存根      
    request.setCharacterEncoding("UTF-8");      
    response.setCharacterEncoding("UTF-8");      
    fc.doFilter(request,response);      
}      
    
public void init(FilterConfig arg0) throws ServletException {      
// TODO 自动生成方法存根      
    
}      
    
}   
