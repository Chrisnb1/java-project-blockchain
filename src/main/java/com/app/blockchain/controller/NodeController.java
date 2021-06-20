package com.app.blockchain.controller;

import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
     * Obtener las URLs de todos los nodos en la red
     * @return JSON Set<></> de URLs (para no tener repetidos)
     */
    @GetMapping("/urlnodes")
    public Set<URL> getUrlNodes() {
        return nodeService.getUrlNodes();
    }

    /**
     * Añade un nodo a la red
     * Los parametros dentro del DTO son: host y puerto
     * @param newNode
     */
    @PostMapping(path = "/addnode")
    public ResponseEntity<Void> addNode(@Valid @RequestBody NodeDTO newNode) {
        try{
            nodeService.addNode(newNode);
            System.out.println("Nodo agregado correctamente");
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Nodo no registrado, error" + e);
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
