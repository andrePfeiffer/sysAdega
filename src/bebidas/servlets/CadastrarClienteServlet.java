package bebidas.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bebidas.model.ClienteManager;
import bebidas.model.VinhoManager;
import utils.Utils;


@WebServlet("/CadastraNovoCliente.do")
public class CadastrarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nomeCliente = request.getParameter("nomeCliente").trim(); // campo obrigatório		
		String cpf = request.getParameter("cpf").trim(); // campo obrigatório		
		String cep = request.getParameter("cep").trim(); // campo obrigatório		
		String endereco = request.getParameter("endereco").trim(); // campo obrigatório
		String numero = request.getParameter("numero").trim(); // campo obrigatório
		String complemento = request.getParameter("complemento").trim(); // campo obrigatório
		String bairro = request.getParameter("bairro").trim(); // campo obrigatório
		String cidade = request.getParameter("cidade").trim(); // campo obrigatório
		String estado = request.getParameter("estado").trim(); // campo obrigatório
		String pais = request.getParameter("pais").trim(); // campo obrigatório
		
		// Encaminhar para a classe especialista
		String result = ClienteManager.cadastrarCliente( nomeCliente, cpf, cep, endereco, numero, complemento, bairro, cidade, estado, pais );		
		request.setAttribute("mensagem", result);
		RequestDispatcher view = request.getRequestDispatcher("gerenciarClientes.jsp");
		
		if( result.contains("Não foi possível cadastrar")) {
			request.setAttribute("nomeCliente", nomeCliente);	
			request.setAttribute("cpf", cpf);	
			request.setAttribute("cep", cep);	
			request.setAttribute("endereco", endereco);	
			request.setAttribute("numero", numero);
			request.setAttribute("complemento", complemento);
			request.setAttribute("bairro", bairro);
			request.setAttribute("cidade", cidade);
			request.setAttribute("estado", estado);
			request.setAttribute("pais", pais);
			view = request.getRequestDispatcher("cadastrarCliente.jsp");
		}		
		
		view.forward(request, response);
	}
}
