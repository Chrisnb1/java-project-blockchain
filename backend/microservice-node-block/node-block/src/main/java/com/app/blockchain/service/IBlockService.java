package com.app.blockchain.service;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.model.Block;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

public interface IBlockService {
    List<Block> listAllBlocks();

    Block createBlock(BlockDTO block);

    Block getLastBlock();

    Block saveBlock(BlockDTO block);

    List<Block> compareBlockChain(List<Block> newBlockchain);

}
