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
import javax.validation.Valid;
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
     *
     * @return la cadena de bloques del nodo
     */
    @GetMapping("/blocks")
    public List<Block> getBlockChain() {
        try{
            return blockService.listAllBlocks();
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param request Bloque creado por el propio nodo
     * @return El mismo bloque creado si es correcto
     */
    @PostMapping(path = "/createblock")
    public ResponseEntity<Block> createNewBlock(@Valid @RequestBody BlockDTO request) {
        if (blockService.saveBlock(request) != null) {
            Block block = blockService.createBlock(request);
            nodeService.sendBlockNetwork("block", block);
            return new ResponseEntity<Block>(block, HttpStatus.OK);
        } else {
            return new ResponseEntity<Block>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @return El ultimo bloque de la cadena actual del nodo
     */
    @GetMapping("/lastblock")
    public Block getLastBlock() {
        try{
            return blockService.getLastBlock();
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param request Los datos de un bloque creado en otro nodo (index, data, timepstamp...etc)
     * @return El mismo bloque con HttpStatus.OK (200)
     */
    @PostMapping(path = "/saveblock")
    public ResponseEntity<Block> saveBlock(@Valid @RequestBody BlockDTO request) {
        if (blockService.saveBlock(request) != null) {
            Block block = blockService.saveBlock(request);
            return new ResponseEntity<Block>(block, HttpStatus.OK);
        } else {
            return new ResponseEntity<Block>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Para un cadena de bloques de otro nodo
     * @param newBlockChain
     * @return
     */
    @PostMapping(path = "/saveblockchain")
    public ResponseEntity<Void> saveBlockChain(@Valid @RequestBody List<Block> newBlockChain) {
        try{
            blockService.compareBlockChain(newBlockChain);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}



