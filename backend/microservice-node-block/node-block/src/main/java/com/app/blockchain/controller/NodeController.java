package com.app.blockchain.controller;

import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.model.Node;
import com.app.blockchain.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URL;
import java.util.Set;

/**
 * Esta clase es la encargada de controlar
 * todas las peticiones (endpoint) del nodo
 */
@RestController
@Validated
public class NodeController {

    private final INodeService nodeService;

    @Autowired
    public NodeController(INodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * Devuelve las URLs de todos los nodos en la red
     *
     * @return JSON Set</> de URLs (para no tener repetidos)
     */
    @GetMapping("/urlnodes")
    public ResponseEntity<Set> getUrlNodes() {
        Set<URL> urls = nodeService.getUrlNodes();
        if (urls == null) {
            System.out.println("Hubo un problema al devolver la lista de URLs");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.accepted().body(urls);
        }
    }

    /**
     * Añade un nodo a la red
     * Los parametros dentro del DTO son: host y puerto
     *
     * @param newNode
     */
    @PostMapping(path = "/addnode")
    public ResponseEntity<Node> addNode(@Valid @RequestBody NodeDTO newNode) {
        Node node = nodeService.addNode(newNode);
        if (node == null) {
            System.out.println("No se pudo agregar el nodo");
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(node.getId())
                    .toUri();
            System.out.println("Nodo agregado correctamente");
            return ResponseEntity.created(uri).body(node);
        }
    }
}
