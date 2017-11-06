package bebidas.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bebidas.model.VinhoManager;

@WebServlet("/ExcluirVinho.do")
public class ExcluirVinhoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Vinho a excluir
		int idVinho = Integer.parseInt(request.getParameter("idVinho"));
		String nomeVinho = request.getParameter("nomeVinho");
		
		// Encaminhar para a classe especialista
		String result = "";
		result = VinhoManager.apagarVinho(idVinho);
		result += nomeVinho;
		
		request.setAttribute("mensagem", result);
		RequestDispatcher view = request.getRequestDispatcher("gerenciarVinhos.jsp");
		view.forward(request, response);
	}

}
