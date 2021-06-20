package com.app.blockchain.service;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.util.SHA256;
import com.app.blockchain.model.Block;
import com.app.blockchain.repository.IBlockRepository;
import com.app.blockchain.repository.INodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service("blockService")
public class BlockService implements IBlockService {

    private final IBlockRepository blockRepository;
    private final INodeRepository nodeRepository;

    private List<Block> blockchain = new ArrayList<>();

    @Autowired
    public BlockService(IBlockRepository blockRepository, INodeRepository nodeRepository) {
        this.blockRepository = blockRepository;
        this.nodeRepository = nodeRepository;
    }

    @Override
    public List<Block> listAllBlocks() {
        blockRepository.findAll().forEach(blockchain::add);
        return blockchain;
    }

    @Override
    public Block createBlock(BlockDTO request) {
        Block block = new Block();
        block.setIndex(this.getLastBlock().getIndex() + 1);
        block.setData(request.getData());
        block.setTimestamp(request.calculateDateTimeFormatter());
        block.setPreviousHash(this.getLastBlock().getHash());
        block.setHash(SHA256.getSHA256(request.concatenatedData()));

        if (this.validateBlock(block)) {
            return blockRepository.save(block);
        }
        return null;
    }

    @Override
    public Block getLastBlock() {
        blockchain = listAllBlocks();
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
            return blockRepository.save(block);
        }
        return null;
    }

    public boolean validateBlock(Block block) {
        Block previousBlock = getLastBlock();

        if (block.getIndex() != (previousBlock.getIndex() + 1)) {
            System.out.println("El indice del bloque n°" + block.getIndex() + " es invalido.");
            return false;
        } else if (!block.getPreviousHash().equals(previousBlock.getHash())) {
            System.out.println("El hash previo del bloque n°" + block.getIndex() + " es invalido.");
            return false;
        }

        return true;
    }

    @Override
    public void compareBlockChain(List<Block> newBlockchain) {
        blockchain = listAllBlocks();

        if (this.longestChain(newBlockchain)) {
            for (int i = getLastBlock().getIndex(); i < newBlockchain.size(); i++) {
                blockRepository.save(newBlockchain.get(i));
            }
        }
    }

    /**
     * Elige la cadena mas larga
     * Compara la cadena actual con una recibida por parametro
     *
     * @param newBlockChain
     */
    public boolean longestChain(List<Block> newBlockChain) {
        if (listAllBlocks().size() < newBlockChain.size()) {
            System.out.println("La cadena actual sera reemplazada por la nueva cadena recibida");
            return true;

        } else {
            System.out.println("Sin modificaciones en la cadena actual");
            return false;
        }
    }

    public void saveBlockChain(List<Block> newBlockChain) {
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
