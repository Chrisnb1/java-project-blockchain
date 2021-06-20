package com.app.blockchain;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.model.Block;
import com.app.blockchain.model.Node;
import com.app.blockchain.repository.IBlockRepository;
import com.app.blockchain.repository.INodeRepository;
import com.app.blockchain.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class BlockchainApplication implements CommandLineRunner {

    private final IBlockRepository blockRepository;
    private final INodeRepository nodeRepository;
    private final INodeService nodeService;

    @Autowired
    public BlockchainApplication(IBlockRepository blockRepository, INodeRepository nodeRepository, INodeService nodeService) {
        this.blockRepository = blockRepository;
        this.nodeRepository = nodeRepository;
        this.nodeService = nodeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        if (blockRepository.findAll().isEmpty()) {
            BlockDTO genesis = new BlockDTO(0, "bloque_genesis", "0");
            Block block0 = new Block(
                    genesis.getIndex(),
                    genesis.getData(),
                    genesis.getTimestamp(),
                    genesis.getPreviousHash(),
                    genesis.getHash());

            blockRepository.save(block0);
        }

        for (Block blocks : blockRepository.findAll()) {
            System.out.println(blocks);
        }
        if(nodeRepository.findAll().isEmpty()) {
            NodeDTO myNode = new NodeDTO("localhost", 8093);
            nodeService.addNode(myNode);
        }

        for(Node nodes : nodeRepository.findAll()){
            System.out.println(nodes);
        }

    }
}
