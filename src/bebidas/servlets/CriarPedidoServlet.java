package bebidas.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bebidas.model.PedidoManager;


@WebServlet("/CriaPedido.do")
public class CriarPedidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idVinho = Integer.parseInt(request.getParameter("vinho")); // campo obrigatório		
		int idCliente = Integer.parseInt(request.getParameter("cliente")); // campo obrigatório		
		int qtdVinho = Integer.parseInt(request.getParameter("qtdVinho").trim()); // campo obrigatório		

		// Encaminhar para a classe especialista
		String result = PedidoManager.criarPedido( idVinho, idCliente, qtdVinho);		
		request.setAttribute("mensagem", result);
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		
		if( result.contains("Não foi possível")) {
			//TODO: tratar erro - continuar:
//			request.setAttribute("idVinho", idVinho);	
//			view = request.getRequestDispatcher("index.jsp");
		}		
		
		view.forward(request, response);
	}
}
