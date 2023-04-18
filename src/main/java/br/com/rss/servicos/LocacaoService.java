package br.com.rss.servicos;

import static br.com.rss.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.Objects;

import br.com.rss.entidades.Filme;
import br.com.rss.entidades.Locacao;
import br.com.rss.entidades.Usuario;
import exception.FilmesSemEstoqueException;
import exception.LocadoraExcepiton;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmesSemEstoqueException, LocadoraExcepiton {
		if(filme.getEstoque() ==0){
			throw new FilmesSemEstoqueException("Filme sem estoque");
		}
		if(Objects.isNull(usuario)){
			throw new LocadoraExcepiton("Usuario vazio");
		}
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}