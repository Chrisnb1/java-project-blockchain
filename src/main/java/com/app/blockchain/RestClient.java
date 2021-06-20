package com.app.blockchain;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.model.Block;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestClient {

    private static final String GET_ALL_BLOCKS_API = "http://localhost:8093/blocks";
    private static final String GET_LAST_BLOCK_API = "http://localhost:8093/lastblock";
    private static final String CREATE_NEW_BLOCK_API = "http://localhost:8093/createblock";
    private static final String SAVE_BLOCK_API = "http://localhost:8093/saveblock";

    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        CallGetAllBlocksAPI();
        //CallGetLastBlockAPI();
        //CallCreateNewBlockAPI();
    }

    public static void CallGetAllBlocksAPI() {
        //Nodo response = restTemplate.getForObject(GET_ALL_BLOCKS_API, Nodo.class);
        Block[] response = restTemplate.getForObject(GET_ALL_BLOCKS_API, Block[].class);
        //List<Block> newBlockChain = new ArrayList<Block>(Arrays.asList(response));

        List<Block> newBlockChain = new ArrayList<Block>(response.length);
        Collections.addAll(newBlockChain, response);
        /*
        System.out.println();
        System.out.println("GET All StatusCode = " + response.getStatusCode());
        System.out.println("GET All Headers = " + response.getHeaders());
        System.out.println("GET Body (object list): ");
        Arrays.asList(response.getBody()).forEach(block -> System.out.println(block));
        System.out.println();*/

        System.out.println();
        //System.out.println(newBlockChain);
        Block lastBlock = newBlockChain.get(newBlockChain.size()-1);
        System.out.println(lastBlock);

        BlockDTO dto = new BlockDTO(lastBlock.getIndex()+1, "4ta Transferencia", lastBlock.getHash());
        newBlockChain.add(new Block(dto.getIndex(), dto.getData(), dto.getTimestamp(), dto.getPreviousHash(), dto.getHash()));
        BlockDTO dto2 = new BlockDTO(dto.getIndex()+1, "5ta Transferencia", dto.getHash());
        newBlockChain.add(new Block(dto2.getIndex(), dto2.getData(), dto2.getTimestamp(), dto2.getPreviousHash(), dto2.getHash()));

        //enviar por metodo put
        System.out.println();
        System.out.println(newBlockChain);
    }

    public static void CallGetLastBlockAPI(){
        ResponseEntity<Block> response = restTemplate.getForEntity(GET_LAST_BLOCK_API, Block.class);

        System.out.println();
        System.out.println("GET StatusCode = " + response.getStatusCode());
        System.out.println("GET Headers = " + response.getHeaders());
        System.out.println("GET Body (object): " + response.getBody());
    }

    public static void CallCreateNewBlockAPI(){
        BlockDTO newBlock = new BlockDTO();
        newBlock.setData("3da Transeferencia");

        ResponseEntity<BlockDTO> response = restTemplate.postForEntity(CREATE_NEW_BLOCK_API, newBlock,BlockDTO.class);

        System.out.println();
        System.out.println("POST executed");
        System.out.println("POST StatusCode = " + response.getStatusCode());
        System.out.println("POST Body (object): " + response.getBody());
    }

    public static void callSaveBlockAPI(){

    }
}
