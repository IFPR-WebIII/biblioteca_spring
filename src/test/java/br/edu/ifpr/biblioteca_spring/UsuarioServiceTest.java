
package br.edu.ifpr.biblioteca_spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpr.biblioteca_spring.models.Usuario;
import br.edu.ifpr.biblioteca_spring.service.EmprestimoService;
import br.edu.ifpr.biblioteca_spring.service.UsuariosService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuariosService usuarioService;

    @Autowired
    private EmprestimoService emprestimoService;

    Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = usuarioService.adicionar(new Usuario(null, "Lucas", "00000000000"));
    }

    @Test
    public void deveAdicionarPessoa() {        
        assertNotNull(usuario.getId());
        assertEquals("Lucas", usuario.getNome());
    }

    @Test
    public void devePermitirEmprestimoSeNaoEstiverBloqueada() {        
        assertTrue(emprestimoService.podeEmprestar(usuario));
    }

    @Test
    public void naoDevePermitirEmprestimoSeEstiverBloqueada() {

        usuario.setBloqueado(true);

        assertFalse(emprestimoService.podeEmprestar(usuario));
    }

    @Test
    public void deveBloquearPessoaComDataCorreta() {
        usuario.setBloqueado(true);
        usuario.bloquearAte(LocalDate.now().plusDays(5));
        
        assertNotNull(usuario.getDataDeDesbloqueio());
        assertTrue(usuario.getDataDeDesbloqueio().isAfter(LocalDate.now()));
    }

    @Test
    public void deveLançarUmaExcecaoAoBuscarIdInexistente() {

        
        assertThrows(IllegalArgumentException.class, ()->{
            usuarioService.buscarPorId(0L);
        });


    }
}
