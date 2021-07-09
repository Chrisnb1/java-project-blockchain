package com.app.blockchain.service;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.util.SHA256;
import com.app.blockchain.model.Block;
import com.app.blockchain.repository.IBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("blockService")
public class BlockService implements IBlockService {

    private final IBlockRepository blockRepository;

    @Autowired
    public BlockService(IBlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Override
    public List<Block> listAllBlocks() {
        List<Block> blockchain = new ArrayList<>();
        if(!blockRepository.findAll().isEmpty()){
            blockRepository.findAll().forEach(blockchain::add);
            return blockchain;
        }
        return null;
    }

    @Override
    public Block createBlock(BlockDTO request) {
        Block newblock = new Block();
        newblock.setIndex(getLastBlock().getIndex() + 1);
        newblock.setData(request.getData());
        newblock.setTimestamp(request.calculateDateTimeFormatter());
        newblock.setPreviousHash(getLastBlock().getHash());
        newblock.setHash(SHA256.getSHA256(request.concatenatedData()));

        if (this.validateBlock(newblock)) {
            return blockRepository.save(newblock);
        }
        return null;
    }

    @Override
    public Block getLastBlock() {
        List<Block> blockchain = listAllBlocks();
        return blockchain.get(blockchain.size() - 1);
    }


    @Override
    public Block saveBlock(BlockDTO request) {
        Block block = new Block();
        block.setIndex(request.getIndex());
        block.setData(request.getData());
        block.setTimestamp(request.getTimestamp());
        block.setPreviousHash(request.getPreviousHash());
        block.setHash(request.getHash());

        if (this.validateBlock(block)) {
            System.out.println("Bloque guardado con exito!");
            return blockRepository.save(block);
        }
        return null;
    }

    public boolean validateBlock(Block block) {
        Block previousBlock = getLastBlock();

        if (block.getIndex() != (previousBlock.getIndex() + 1)) {
            System.out.println("El indice del bloque n°" + block.getIndex() + " es invalido.");
            return false;
        } else if (block.getData() == null || block.getData() == "") {
            System.out.println("Los datos del bloque n°" + block.getIndex() + " son invalidos.");
            return false;
        } else if (block.getTimestamp() == null || block.getTimestamp() == "") {
            System.out.println("El tiempo del bloque n°" + block.getIndex() + " es invalido.");
            return false;
        } else if (!block.getPreviousHash().equals(previousBlock.getHash()) || block.getPreviousHash() == null || block.getPreviousHash() == "") {
            System.out.println("El hash previo del bloque n°" + block.getIndex() + " es invalido.");
            return false;
        } else if (block.getHash() == null || block.getHash() == "") {
            System.out.println("El hash del bloque n°" + block.getIndex() + " es invalido.");
            return false; }

            System.out.println("Bloque n°" + block.getIndex() + " valido");
            System.out.println();
            return true;
        }

        @Override
        public List<Block> compareBlockChain (List < Block > newBlockchain) {
            List<Block> blockchain = listAllBlocks();

            if (this.longestChain(newBlockchain)) {
                for (int i = getLastBlock().getIndex(); i < newBlockchain.size(); i++) {
                    blockRepository.save(newBlockchain.get(i));
                }
                return newBlockchain;
            }

            return null;
        }

        /**
         * Elige la cadena mas larga
         * Compara la cadena actual con una recibida por parametro
         *
         * @param newBlockChain
         */
        public boolean longestChain (List < Block > newBlockChain) {
            List<Block> blockchain = listAllBlocks();
            if (blockchain.size() < newBlockChain.size()) {
                System.out.println("La cadena actual sera reemplazada por la nueva cadena recibida");
                return true;

            } else {
                System.out.println("Sin modificaciones en la cadena actual");
                return false;
            }
        }

        public void saveBlockChain (List < Block > newBlockChain) {
            for (Block block : newBlockChain) {
                blockRepository.save(block);
            }
        }


    /* Solicitar la cadena de bloques a un nodo vecino
    public void requestBlockchain(URL urlNode, RestTemplate restTemplate) {
        List<Block> newBlockChain = restTemplate.getForObject(urlNode + "/blocks", List.class);
        System.out.println("Obtenida cadena de bloques de nodo " + urlNode + ".\n");
        try {
            if (listAllBlocks().isEmpty()) {
                assert newBlockChain != null;
                saveBlockChain(newBlockChain);
            } else {
                compareBlockChain(newBlockChain);
            }
        } catch (Exception e) {
            System.out.println("La Cadena de bloques es inválida");
        }
    }*/
    }
