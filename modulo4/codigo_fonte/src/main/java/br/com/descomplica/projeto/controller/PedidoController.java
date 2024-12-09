package br.com.descomplica.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.descomplica.projeto.entity.Pedido;
import br.com.descomplica.projeto.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    // Método para criar uma resposta OK
    private <T> ResponseEntity<T> createResponse(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    // Método para tratar pedidos não encontrados
    private <T> ResponseEntity<T> createNotFoundResponse() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getAll() {
        List<Pedido> pedidos = pedidoService.getAll();
        return pedidos.isEmpty() ? createNotFoundResponse() : createResponse(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Integer id) {
        return pedidoService.getById(id)
                .map(pedido -> createResponse(pedido, HttpStatus.OK))
                .orElseGet(this::createNotFoundResponse);
    }

    @PostMapping
    public ResponseEntity<Pedido> savePedido(@RequestBody Pedido pedido) {
        Pedido savedPedido = pedidoService.savePedido(pedido);
        return createResponse(savedPedido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Pedido updatedPedido = pedidoService.updatePedido(id, pedido);
        return updatedPedido != null ? createResponse(updatedPedido, HttpStatus.OK) : createNotFoundResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePedido(@PathVariable Integer id) {
        boolean deleted = pedidoService.deletePedido(id);
        return createResponse(deleted, HttpStatus.OK);
    }
}
