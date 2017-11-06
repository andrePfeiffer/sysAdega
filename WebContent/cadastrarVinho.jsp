<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%
		// Caso tenha ocorrido erro no cadastro, recuperar os valores preenchidos
		String nomeVinho = (String)request.getAttribute("nomeVinho");	
		String corVinho = (String)request.getAttribute("corVinho");
		Double precoVinho = (Double)request.getAttribute("precoVinho");
		Integer anoVinho = (Integer)request.getAttribute("anoVinho");
		Integer qtdEstoque = (Integer)request.getAttribute("qtdEstoque");
	%>
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>

		
	 <!-- CADASTRO DE NOVO RISCO -->
	<div class="container">
	
		<!-- Botões -->	
		<a href="gerenciarVinhos.jsp" class="btn btn-success">Consultar Vinhos</a>
		<br/>
		
		<form action="CadastraNovoVinho.do" method="post">
			<fieldset>
				<legend>Novo Vinho</legend>
				
				
					<div class="form-group">	
		        		<label for="nomeVinho">Nome</label>					
						<input type="text" class="form-control" id="nomeVinho" name="nomeVinho" maxlength="150" required value="<%if(nomeVinho!=null){out.println(nomeVinho);}%>"/>						
					</div>
					
					<div class="form-group">
		        		<label for="corVinho">Cor</label>		
						<select class="form-control" id="corVinho" name="corVinho" required>
							<option value="Branco" <%if( corVinho != null && corVinho.equals("Branco")){%>selected="selected"<%}%>>Branco</option>
							<option value="Tinto" <%if( corVinho != null && corVinho.equals("Tinto")){%>selected="selected"<%}%>>Tinto</option>
							<option value="Rose" <%if( corVinho != null && corVinho.equals("Rose")){%>selected="selected"<%}%>>Rose</option>
						</select>
					</div>
					
		        	<div class="form-group">	
		        		<label for="anoVinho">Ano</label>					
						<input type="number" min="1900" max="2017" class="form-control" id="anoVinho" name="anoVinho" maxlength="4" required value="<%=anoVinho%>"/>						
					</div>	
					
					<div class="form-group">
						<label for="precoVinho">Preço</label>		
						<input type="text" class="form-control" id="precoVinho" name="precoVinho" required value="<%if(precoVinho!=null){out.println(Utils.strDoubleParaMoeda(precoVinho));}%>"/>
					</div>	
					
					<div class="form-group">
						<label for="qtdEstoque">Quantidade em Estoque</label>		
						<input type="number" min="0" class="form-control" id="qtdEstoque" name="qtdEstoque" required value="<%=qtdEstoque%>"/>
					</div>	
							
			</fieldset>
			<button type="submit" class="btn btn-primary">Cadastrar Vinho</button>
		</form>		
	</div>
	<!-- fim .container da pagina -->
</body>
</html>
