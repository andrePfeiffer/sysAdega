package bebidas.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bebidas.model.PedidoManager;


@WebServlet("/EncerraPedido.do")
public class EncerrarPedidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idPedido = Integer.parseInt(request.getParameter("idPedido"));	

		// Encaminhar para a classe especialista
		String result = PedidoManager.encerrarPedido( idPedido );		
		request.setAttribute("mensagem", result);
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}
}
