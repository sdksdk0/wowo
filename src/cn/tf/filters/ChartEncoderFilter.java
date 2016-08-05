package cn.tf.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


public class ChartEncoderFilter implements Filter {

    
	@Override
	public void destroy() {
		
	}

	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String encoding = filterConfig.getInitParameter("encoding");
		if(encoding==null)
			encoding = "UTF-8";
		
		request.setCharacterEncoding(encoding);//解决POST请求参数编码
		response.setCharacterEncoding(encoding);//更改响应字符流使用的编码
		response.setContentType("text/html;charset="+encoding);//更改响应字符流使用的编码，还能告知浏览器用什么编码进行显示
		chain.doFilter(request, response);
	}
}
