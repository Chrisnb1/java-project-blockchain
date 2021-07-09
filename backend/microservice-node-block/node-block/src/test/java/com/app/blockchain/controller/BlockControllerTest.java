package com.app.blockchain.controller;

import com.app.blockchain.model.Block;
import com.app.blockchain.repository.IBlockRepository;
import com.app.blockchain.service.BlockService;
import com.app.blockchain.service.IBlockService;
import com.app.blockchain.service.INodeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockControllerTest {

    @Autowired
    IBlockService blockService;

    @Autowired
    INodeService nodeService;

    BlockService blockServicesMock = Mockito.mock(BlockService.class);

    @Autowired
    BlockController blockController = new BlockController(blockService, nodeService);

    @BeforeEach
    void setUp() {
        Block mockBlock = new Block();
        mockBlock.setIndex(16);
        mockBlock.setData("prueba send 2");
        mockBlock.setTimestamp("17:04 04/07/2021");
        mockBlock.setPreviousHash("ba156423822026d2617b85944bef45ddb706eedfe21d678f433189057dde9cb3");
        mockBlock.setHash("52414d97a58dce4e63eeb1eb4d41fcea85f17a5f6b69f04783c684720970ec93");

        Mockito.when(blockServicesMock.getLastBlock()).thenReturn(mockBlock);
    }

    @Test
    void getLastBlock() {
        ResponseEntity<Block> response = blockController.getLastBlock();
        Assertions.assertEquals(16,response.getBody().getIndex());
    }

    @AfterEach
    void tearDown() {
        ResponseEntity<Block> response = blockController.getLastBlock();
        Assertions.assertNotEquals(15, response.getBody().getIndex());
    }
}