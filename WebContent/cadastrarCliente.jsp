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
		String nomeCliente = (String)request.getAttribute("nomeCliente");	
		String cpf = (String)request.getAttribute("cpf");
		String cep = (String)request.getAttribute("cep");
		String endereco = (String)request.getAttribute("endereco");
		String numero = (String)request.getAttribute("numero");
		String complemento = (String)request.getAttribute("complemento");
		String bairro = (String)request.getAttribute("bairro");
		String cidade = (String)request.getAttribute("cidade");
		String estado = (String)request.getAttribute("estado");
		String pais = (String)request.getAttribute("pais");
	%>
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>

		
	 <!-- CADASTRO DE NOVO RISCO -->
	<div class="container">
	
		<!-- Botões -->	
		<a href="gerenciarClientes.jsp" class="btn btn-success">Consultar Clientes</a>
		<br><br/><br/>
		
		<form action="CadastraNovoCliente.do" method="post" class="form form-">
			<fieldset>
				<legend>Novo Cliente</legend>
				
				<div class="row">
					<div class="col-md-7">	
		        		<label for="nomeVinho">Nome</label>					
						<input type="text" class="form-control" id="nomeCliente" name="nomeCliente" maxlength="150" required value="<%if(nomeCliente!=null){out.println(nomeCliente);}%>"/>						
					</div>
					<div class="col-md-5">
						<label for="cpf">Cpf</label>					
						<input type="text" class="form-control" id="cpf" name="cpf" maxlength="150" onblur="validaCPF(this)" required="required" value="<%if(cpf!=null){out.println(cpf);}%>"/>
					</div>
				</div>
				<br><br>
				<div class="row">
					<div class="col-md-2">
						<label for="cep">Cep</label>					
						<input type="text" class="form-control" id="cep" name="cep" maxlength="150" required value="<%if(cep!=null){out.println(cep);}%>"/>
					</div>
					<div class="col-md-4">
						<label for="endereco">Endereço</label>					
						<input type="text" class="form-control" id="endereco" name="endereco" maxlength="150" required value="<%if(endereco!=null){out.println(endereco);}%>"/>
					</div>
					<div class="col-md-1">
						<label for="numero">Número</label>					
						<input type="text" class="form-control" id="numero" name="numero" maxlength="150" required value="<%if(numero!=null){out.println(numero);}%>"/>
					</div>
					<div class="col-md-2">
						<label for="complemento">Complemento</label>					
						<input type="text" class="form-control" id="complemento" name="complemento" maxlength="150" required value="<%if(complemento!=null){out.println(complemento);}%>"/>
					</div>
					<div class="col-md-3">
						<label for="bairro">Bairro</label>					
						<input type="text" class="form-control" id="bairro" name="bairro" maxlength="150" required value="<%if(bairro!=null){out.println(bairro);}%>"/>
					</div>
				</div>
				<br><br>
				<div class="row">
					<div class="col-md-6">
						<label for="cidade">Cidade</label>					
						<input type="text" class="form-control" id="cidade" name="cidade" maxlength="150" required value="<%if(cidade!=null){out.println(cidade);}%>"/>
					</div>
					<div class="col-md-2">
						<label for="estado">Estado</label>					
						<input type="text" class="form-control" id="estado" name="estado" maxlength="150" required value="<%if(estado!=null){out.println(estado);}%>"/>
					</div>
					<div class="col-md-4">
						<label for="pais">País</label>					
						<input type="text" class="form-control" id="pais" name="pais" maxlength="150" required value="<%if(pais!=null){out.println(pais);}%>"/>
					</div>
				</div>
				<br><br>					
			</fieldset>
			<button type="submit" class="btn btn-primary">Cadastrar Cliente</button>
		</form>		
	</div>
	<!-- fim .container da pagina -->
</body>
<script type="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
        
    //Quando o campo cep perde o foco.
    $("#cep").blur(function() {

        //Nova variável "cep" somente com dígitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if(validacep.test(cep)) {

                //Consulta o webservice viacep.com.br/
                $.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#endereco").val(dados.logradouro);
                        $("#bairro").val(dados.bairro);
                        $("#cidade").val(dados.localidade);
                        $("#estado").val(dados.uf);
                        $("#pais").val("Brasil");
                    } //end if.
                    else {
                        //CEP pesquisado não foi encontrado.
                        limpa_formulário_cep();
                        alert("CEP não encontrado.");
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                limpa_formulário_cep();
                alert("Formato de CEP inválido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulário_cep();
        }
    });
});

$('#cpf').mask('999.999.999-99', {reverse: true});
$("#cep").mask("99.999-999");

	
function coreCPFValidador(cpf) {  
    cpf = cpf.replace(/[^\d]+/g,'');    
    if(cpf == '') return false; 
        
    if (cpf.length != 11 || 
        cpf == "00000000000" || 
        cpf == "11111111111" || 
        cpf == "22222222222" || 
        cpf == "33333333333" || 
        cpf == "44444444444" || 
        cpf == "55555555555" || 
        cpf == "66666666666" || 
        cpf == "77777777777" || 
        cpf == "88888888888" || 
        cpf == "99999999999")
            return false;       
     
    add = 0;    
    for (i=0; i < 9; i ++)       
        add += parseInt(cpf.charAt(i)) * (10 - i);  
        rev = 11 - (add % 11);  
        if (rev == 10 || rev == 11)     
            rev = 0;    
        if (rev != parseInt(cpf.charAt(9)))     
            return false;       
     
    add = 0;    
    for (i = 0; i < 10; i ++)        
        add += parseInt(cpf.charAt(i)) * (11 - i);  
    rev = 11 - (add % 11);  
    if (rev == 10 || rev == 11) 
        rev = 0;    
    if (rev != parseInt(cpf.charAt(10)))
        return false;       
    return true;   
}
	
function validaCPF(value){
	if (!coreCPFValidador(value.value)){
		alert("Por favor, digite um CPF válido.");
		$('.cpf').html("");
	}
}

</script>
</html>
