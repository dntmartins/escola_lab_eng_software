package br.com.fatec.escola.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import br.com.fatec.escola.api.entity.User;


/**
 * @author Dante
 * 
 * @version
 */
public class FiltroStudent implements Filter {

@Override
public void doFilter(ServletRequest req, ServletResponse resp,
		FilterChain chain) throws IOException,
		ServletException {

	//pega usu�rio setado no FiltroLogin
	User usuario = (User) req.getAttribute("usuario");

	HttpServletResponse httpResponse = (HttpServletResponse) resp;

	//Caso exista usuario
	if (usuario != null) {
		//Verifica se ele possui permiss�o
		if (usuario.getRole().getRoleName().equals("Student")) {
			chain.doFilter(req, resp);
		} else {
			//Caso n�o possua permiss�o redireciona para a p�gina semPermiss�o.html
			httpResponse.sendRedirect("semPermissao.html");
		}
	} else {
		//Caso nao exista usu�rio, redireciona usu�rio para realizar novo login
		httpResponse.sendRedirect("login.html");
	}
	return;
}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
