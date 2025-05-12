
package br.edu.ifpr.biblioteca_spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpr.biblioteca_spring.models.Emprestimo;
import br.edu.ifpr.biblioteca_spring.models.Livro;
import br.edu.ifpr.biblioteca_spring.models.Usuario;
import br.edu.ifpr.biblioteca_spring.service.EmprestimoService;
import br.edu.ifpr.biblioteca_spring.service.LivroService;
import br.edu.ifpr.biblioteca_spring.service.UsuariosService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmprestimoServiceTest {

    @Autowired
    private UsuariosService usuarioService;

    @Autowired
    private LivroService livroService;

    @Autowired
    private EmprestimoService emprestimoService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void deveRealizarEmprestimo() {
        Usuario usuario = usuarioService.adicionar(new Usuario(null, "Lucas", "00000000000"));
        Livro livro = livroService.adicionar("Clean Code", "Robert C. Martin");

        Emprestimo emprestimo = emprestimoService.emprestarLivro(usuario, livro);
        
        assertNotNull(emprestimo);
        assertEquals("Clean Code", emprestimo.getLivro().getTitulo());
        assertFalse(emprestimo.getLivro().isDisponivel());
    }

    @Test
    public void naoDevePermitirEmprestimoComLivroIndisponivel() {
        Usuario usuario = usuarioService.adicionar(new Usuario(null, "Lucas", null));
        Livro livro = livroService.adicionar("Clean Code", "Robert C. Martin");
        livro.setDisponivel(false);

        assertThrows(IllegalArgumentException.class, () -> {
            emprestimoService.emprestarLivro(usuario, livro);
        });
    }

    @Test
    public void deveCalcularDataDevolucaoEmDiaUtil() {
        LocalDate hoje = LocalDate.of(2025, 5, 9); // sexta-feira
        LocalDate devolucao = emprestimoService.calcularDataDevolucao(hoje);
        assertNotEquals(6, devolucao.getDayOfWeek().getValue()); // sábado
        assertNotEquals(7, devolucao.getDayOfWeek().getValue()); // domingo
    }
}
