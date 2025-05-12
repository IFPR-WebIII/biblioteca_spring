
package br.edu.ifpr.biblioteca_spring;


import br.edu.ifpr.biblioteca_spring.models.Livro;
import br.edu.ifpr.biblioteca_spring.service.LivroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LivroServiceTest {

    @Autowired
    private LivroService livroService;

    @BeforeEach
    public void setup() {
        livroService = new LivroService();
    }

    @Test
    public void deveAdicionarLivro() {
        Livro adicionado = livroService.adicionar("1984", "George Orwell");
        assertNotNull(adicionado.getId());
        assertEquals("1984", adicionado.getTitulo());
    }

}
