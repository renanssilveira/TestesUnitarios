package service;

import br.com.rss.entidades.Filme;
import br.com.rss.entidades.Locacao;
import br.com.rss.entidades.Usuario;
import br.com.rss.servicos.LocacaoService;
import br.com.rss.utils.DataUtils;
import exception.FilmesSemEstoqueException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;


import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException expectedException = ExpectedException.none();


	@Test
    public void alugarFilmeTestDone() throws Exception {
        LocacaoService service = new LocacaoService();
        Usuario usuario = Usuario.builder().nome("Usuario1").build();
        Filme filme = Filme.builder().nome("Filme1").estoque(2).precoLocacao(5.0).build();



			Locacao locacao = service.alugarFilme(usuario, filme);

			assertThat(locacao.getValor(), is((equalTo(5.0))));
			assertThat(locacao.getValor(), is((not(4.0))));
			assertEquals(5.0, locacao.getValor(), 0.1);
			assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

			error.checkThat(locacao.getValor(), is((equalTo(5.0))));
			error.checkThat(locacao.getValor(), is((not(4.0))));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));



    }

	@Test(expected = FilmesSemEstoqueException.class)
	public void testeLocacao_filmeSemestoque() throws Exception {
		LocacaoService service = new LocacaoService();
		Usuario usuario = Usuario.builder().nome("Usuario1").build();
		Filme filme = Filme.builder().nome("Filme1").estoque(0).precoLocacao(5.0).build();

    	Locacao locacao = service.alugarFilme(usuario, filme);
	}

	@Test
	public void testeLocacao_filmeSemestoque2() {
		LocacaoService service = new LocacaoService();
		Usuario usuario = Usuario.builder().nome("Usuario1").build();
		Filme filme = Filme.builder().nome("Filme1").estoque(0).precoLocacao(5.0).build();

		try {
			Locacao locacao = service.alugarFilme(usuario, filme);
			Assert.fail("Deveria Falhar");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	@Test
	public void testeLocacao_filmeSemestoque3() throws Exception {
		LocacaoService service = new LocacaoService();
		Usuario usuario = Usuario.builder().nome("Usuario1").build();
		Filme filme = Filme.builder().nome("Filme1").estoque(0).precoLocacao(5.0).build();

		expectedException.expect(FilmesSemEstoqueException.class);
		expectedException.expectMessage("Filme sem estoque");

		Locacao locacao = service.alugarFilme(usuario, filme);
	}
}
