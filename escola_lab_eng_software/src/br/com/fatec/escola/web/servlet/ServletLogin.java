package br.com.fatec.escola.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fatec.escola.api.dao.UserDAO;
import br.com.fatec.escola.api.entity.User;
import br.com.spektro.minispring.core.implfinder.ImplementationFinder;

/**
 * @author Dante
 * 
 * @version
 */

//Responsavel por realizar o login do usu�rio
public class ServletLogin extends HttpServlet {

	/** */
	private static final long serialVersionUID = 7774963102366849971L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Pega parametros enviados pela requisi��o
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		//Realiza opera��o no banco atraves dos parametros passados
		User usuario = ImplementationFinder.getImpl(UserDAO.class).findByLoginAndPassword(login, password);
		
		//Caso encontre usu�rio, cria cookie de nome login_usuario e valor login. Apos isso redireciona para o menu principal
		if (usuario != null) {
			Cookie ck = new Cookie("login_usuario", login);
			ck.setMaxAge(3000);
			response.addCookie(ck);
			response.sendRedirect("menu");
		} else {
			//Caso nao encontre usu�rio atraves dos parametros redireciona para um novo login
			response.sendRedirect("novoLogin.html");
		}
	}

}
