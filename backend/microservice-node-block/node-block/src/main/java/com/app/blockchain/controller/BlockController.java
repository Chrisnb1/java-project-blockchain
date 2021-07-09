package com.app.blockchain.controller;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.model.Block;
import com.app.blockchain.service.IBlockService;
import com.app.blockchain.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Esta clase es la encargada de controlar
 * todas las peticiones (endpoint) del bloque
 */
@RestController
@Validated
public class BlockController {

    private final IBlockService blockService;
    private final INodeService nodeService;

    @Autowired
    public BlockController(IBlockService blockService, INodeService nodeService) {
        this.blockService = blockService;
        this.nodeService = nodeService;
    }

    /**
     * @return la cadena de bloques del nodo
     */
    @GetMapping("/blocks")
    public ResponseEntity<List> getBlockChain() {
        List<Block> blockchain = blockService.listAllBlocks();
        if (blockchain == null) {
            System.out.println("Hubo un problema al devolver la cadena de bloques");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.accepted().body(blockchain);
        }
    }

    /**
     * Si el bloque es correcto, se crea.
     * Se envia al resto de los nodos si el param send == true.
     *
     * @param request
     * @param send
     * @return
     */
    @PostMapping(path = "/createblock")
    public ResponseEntity<Block> createNewBlock(@Valid @RequestBody BlockDTO request, @RequestParam(required = false) Boolean send) {
        try {
            Block block = blockService.createBlock(request);
            if (block == null) {
                System.out.println("Hubo un problema al crear el bloque");
                return ResponseEntity.notFound().build();
            } else {
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .buildAndExpand(block.toString())
                        .toUri();

                if (send != null && send) {
                    nodeService.sendBlockNetwork("saveblock", block);
                }
                System.out.println("Bloque creado correctamente");
                return ResponseEntity.created(uri).body(block);
            }
        } catch (Exception e) {
            System.out.println("El error es: " + e.getMessage());
            return new ResponseEntity<Block>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return El ultimo bloque de la cadena actual del nodo
     */
    @GetMapping("/lastblock")
    public ResponseEntity<Block> getLastBlock() {
        Block block = blockService.getLastBlock();
        if (block == null) {
            System.out.println("Hubo un problema al devolver el ultimo bloque");
            return ResponseEntity.notFound().build();

        } else {
            System.out.println("Bloque enviado correctamente");
            return ResponseEntity.accepted().body(block);
        }
    }

    /**
	 * Los datos de un bloque creado en otro nodo (index, data, timepstamp...etc)
     * @param request 
     * @return El mismo bloque con HttpStatus.OK (200)
     */
    @PostMapping(path = "/saveblock")
    public ResponseEntity<Block> saveBlock(@Valid @RequestBody BlockDTO request) {
        Block block = blockService.saveBlock(request);
        if (block == null) {
            System.out.println("Hubo un problema al guardar el bloque");
            //return ResponseEntity.badRequest().body(block);
            return new ResponseEntity<Block>(HttpStatus.BAD_REQUEST);
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(block.toString())
                    .toUri();

            System.out.println("Bloque enviado correctamente");
            return ResponseEntity.created(uri).body(block);
        }
    }

    /**
     * Para un cadena de bloques de otro nodo
     *
     * @param newBlockChain
     * @return
     */
    @PostMapping(path = "/saveblockchain")
    public ResponseEntity<List> saveBlockChain(@Valid @RequestBody List<Block> newBlockChain) {
        List<Block> blockchain = blockService.compareBlockChain(newBlockChain);
        if (blockchain == null) {
            System.out.println("No hubo modificaciones en la cadena actual");
            return ResponseEntity.badRequest().body(blockchain);
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(blockchain.toArray())
                    .toUri();

            System.out.println("Bloque enviado correctamente");
            return ResponseEntity.created(uri).body(blockchain);
        }
    }
}



