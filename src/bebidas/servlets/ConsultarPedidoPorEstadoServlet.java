package bebidas.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bebidas.model.Pedido;
import bebidas.model.PedidoManager;


@WebServlet("/ConsultaPedidoPorEstado.do")
public class ConsultarPedidoPorEstadoServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String estadoPedido = request.getParameter("estadoPedido"); // campo obrigatório		
		
		// Encaminhar para a classe especialista
		List<Pedido> result = PedidoManager.consultarPedidoPorEstado(estadoPedido);		
		request.setAttribute("listaPedidos", result);
		request.setAttribute("estadoPedido", estadoPedido);
		RequestDispatcher view = request.getRequestDispatcher("consultarPedidoPorEstadoResultado.jsp");
		
		view.forward(request, response);
	}
}
