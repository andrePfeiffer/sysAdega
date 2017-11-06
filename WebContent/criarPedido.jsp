<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%
		// TODO: Caso tenha ocorrido erro na criação do pedido, recuperar os valores para que o formulário já venha preenchido
	
		List<Vinho> vinhos = VinhoManager.consultarTodosVinhos();
		
		String selectVinhos = "";
	
		if( vinhos == null || vinhos.isEmpty() ) {
	%>	
		<div class="container"> 
			<div class="alert alert-danger">		
				<strong>Não há vinhos cadastrados.</strong>
			</div>
		</div>		
	<%
		} else {
	%>

	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>

		
	 <!-- CADASTRO DE NOVO PEDIDO -->
	<div class="container">
	
		<!-- Botões -->	
		
		<form action="CriaPedido.do" method="post">
			<fieldset>
				<legend>Novo Pedido</legend>
				
				<!-- TODO: substituir o campo nomeCliente por uma combobox que traga os nomes dos clientes cadastrados -->					
					<div class="form-group">	
		        		<label for="nomeCliente">Cliente</label>					
						<input type="text" class="form-control" id="nomeCliente" name="nomeCliente" maxlength="150" required/>						
					</div>
									
				
					<div class="form-group">
						<div id="vinhos">
						<div class="row">							
							<div class="col-md-5">
				        		<label for="vinho">Vinho</label>		
								<select class="form-control" id="vinho" name="vinho" required>
									<% 
									
										for( Vinho vinho : vinhos ) { 
									
											selectVinhos += "<option value=" + vinho.getIdVinho() + ">" + vinho.getNomeVinho() + "</option>";
									 
										} 
										
										out.print(selectVinhos);
									%>
					  			</select>
					  		</div>
					  		<div class="col-md-2">	
					  			<label for="qtdVinho">Quantidade</label>		
								<input type="number" min="0" class="form-control" id="qtdVinho" name="qtdVinho" required/>
							</div>		
							<div class="col-md-5">
					  			<span><i class="fa fa-plus fa-2x addWine" aria-hidden="true"></i></span>&nbsp;&nbsp;&nbsp;
					  			
							</div>	
						</div> 
						</div>				
					</div>
					
					
					<!-- TODO: permitir a seleção de mais de um vinho por pedido, cada um com sua quantidade -->
				
							
			</fieldset>
			<button type="submit" class="btn btn-primary">Criar Pedido</button>
		</form>		
	</div>
	<!-- fim .container da pagina -->
	<%
		}
	%>
	<script type="text/javascript">
	
	var idVinho = 0;
	
	$(document).on('click', '.addWine', function() {
		  $("#vinhos").append("<div class='row' id='"+ idVinho +"'>"+							
			"<div class='col-md-5'>"+	
			"<br><select class='form-control' id='vinho' name='vinho' required>"+
			<% out.print('"'+ selectVinhos + '"'); %> + 
			"</select></div><div class='col-md-2'>"+		
			"<br><input type='number' min='0' class='form-control' id='qtdVinho' name='qtdVinho' required/>"+
			"</div><div class='col-md-5'>"+
  			"<span><i class='fa fa-plus fa-2x addWine' aria-hidden='true' onclick='addWineLine()'></i></span>&nbsp;&nbsp;&nbsp;"+
  			"<span><i class='fa fa-trash fa-2x removeWine' aria-hidden='true' onclick='removeWineLine("+idVinho+")'></i></span>&nbsp;"+
			"</div></div>");
		  
		  idVinho++;
		});
	
	function removeWineLine(id){
		$("#"+id).empty();
	}
	</script>
</body>
</html>
