package br.com.descomplica.projeto.controller;

import br.com.descomplica.projeto.entity.Categoria;
import br.com.descomplica.projeto.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController categoriaController; // Controlador que será testado

    @Mock
    private CategoriaService categoriaService; // Serviço mockado

    private Categoria categoria;
    
    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Categoria Teste");
    }

    @Test
    void testGetAllCategorias_Success() {
        // Configurando o comportamento do mock
        when(categoriaService.getAll()).thenReturn(Arrays.asList(categoria));

        // Executando o teste
        ResponseEntity<List<Categoria>> response = categoriaController.getAll();

        // Verificando os resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllCategorias_NotFound() {
        when(categoriaService.getAll()).thenReturn(Arrays.asList());

        ResponseEntity<List<Categoria>> response = categoriaController.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetById_Success() {
        when(categoriaService.getById(1)).thenReturn(categoria);

        ResponseEntity<Categoria> response = categoriaController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(categoriaService.getById(1)).thenReturn(null);

        ResponseEntity<Categoria> response = categoriaController.getById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSaveCategoria_Success() {
        when(categoriaService.saveCategoria(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<Categoria> response = categoriaController.saveCategoria(categoria);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateCategoria_Success() {
        when(categoriaService.updateCategoria(eq(1), any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<Categoria> response = categoriaController.updateCategoria(1, categoria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateCategoria_NotFound() {
        when(categoriaService.updateCategoria(eq(1), any(Categoria.class))).thenReturn(null);

        ResponseEntity<Categoria> response = categoriaController.updateCategoria(1, categoria);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCategoria_Success() {
        when(categoriaService.deleteCategoria(1)).thenReturn(true);

        ResponseEntity<Boolean> response = categoriaController.deleteCategoria(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void testDeleteCategoria_Failure() {
        when(categoriaService.deleteCategoria(1)).thenReturn(false);

        ResponseEntity<Boolean> response = categoriaController.deleteCategoria(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
    }
}
