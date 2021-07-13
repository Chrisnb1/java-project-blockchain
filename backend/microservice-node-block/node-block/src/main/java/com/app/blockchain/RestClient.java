package com.app.blockchain;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.model.Block;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RestClient {

    //Endpoint Bloques
    private static final String GET_ALL_BLOCKS_API = "http://localhost:8093/blocks";            //Cadena de bloques
    private static final String GET_LAST_BLOCK_API = "http://localhost:8093/lastblock";         //Ultimo bloque de la cadena
    private static final String CREATE_NEW_BLOCK_API = "http://localhost:8093/createblock";     //Crear un bloque y retransmitirlo
    private static final String SAVE_BLOCK_API = "http://localhost:8093/saveblock";             //Guardar un bloque proveniente de otro nodo
    private static final String SAVE_BLOCKCHAIN_API = "http://localhost:8093/saveblockchain";   //Guardar una cadena bloques proveniente de otro nodo

    //Endpoint Nodos
    private static final String GET_URLS_NODES_API = "http://localhost:8093/urlnodes";      //Obtener las urls de los nodos
    private static final String ADD_NODE_API = "http://localhost:8093/addnode";             //Añadir nodo

    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) throws URISyntaxException, IOException {
        /** OBTENER LA CADENA DE BLOQUES **/
        CallGetAllBlocksAPI();

        /** OBTENER ULTIMO BLOQUE DE LA CADENA DE BLOQUES **/
        CallGetLastBlockAPI();

        /** CREAR UN BLOQUE EN EL NODO **/
        CallCreateNewBlockAPI();

        /** AÑADIR UN BLOQUE DESDE OTRO NODO **/
        callSaveBlockAPI();

        /** COMPARAR LA CADENA DE BLOQUES POR OTRA **/
        callSaveBlockchainAPI();

        /** OBTENER LAS URLS DE LOS NODOS EN LA RED **/
        callGetAllUrlsNodesAPI();

        /** AÑADIR UN NODO A LA RED **/
        callAddNodeAPI();

    }

    /**
     * Solicita la cadena de bloques
     */
    public static void CallGetAllBlocksAPI() {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(GET_ALL_BLOCKS_API, List.class);

            System.out.println();
            System.out.println("GET All StatusCode = " + response.getStatusCode());
            System.out.println("GET All Headers = " + response.getHeaders());
            System.out.println("GET Body (object list): ");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(response.getBody());

            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error CallGetAllBlocks");
        }
    }

    /**
     * Solicita el ultimo bloque de la cadena
     */
    public static void CallGetLastBlockAPI() {
        try {
            ResponseEntity<Block> response = restTemplate.getForEntity(GET_LAST_BLOCK_API, Block.class);

            System.out.println();
            System.out.println("GET StatusCode = " + response.getStatusCode());
            System.out.println("GET Headers = " + response.getHeaders());
            System.out.println("GET Body (object): ");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(response.getBody());

            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error CallGetLastBlock");
        }

    }

    /**
     * Crea un nuevo bloque
     */
    public static void CallCreateNewBlockAPI() {
        try {
            BlockDTO newBlock = new BlockDTO();
            newBlock.setData("Bloque CREADO desde Rest Client Prueba");

            ResponseEntity<BlockDTO> response = restTemplate.postForEntity(CREATE_NEW_BLOCK_API + "?send=true", newBlock, BlockDTO.class);

            System.out.println();
            System.out.println("POST executed");
            System.out.println("POST StatusCode = " + response.getStatusCode());
            System.out.println("POST Body (object): ");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(response.getBody());

            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error CallCreateNewBlock");
        }

    }

    /**
     * Guarda un bloque de otro nodo
     */
    public static void callSaveBlockAPI() {
        try {
            BlockDTO newBlock = new BlockDTO();
            newBlock.setIndex(1);
            newBlock.setData("Bloque AÑADIDO desde Rest Client");
            newBlock.setTimestamp("13:12 21/06/2021");
            newBlock.setPreviousHash("bnritj25678");
            newBlock.setHash("");

            ResponseEntity<BlockDTO> response = restTemplate.postForEntity(SAVE_BLOCK_API, newBlock, BlockDTO.class);

            System.out.println();
            System.out.println("POST executed");
            System.out.println("POST StatusCode = " + response.getStatusCode());
            System.out.println("POST Body (object): ");


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(response.getBody());

            System.out.println(json);
        } catch (Exception e) {
        }

    }

    /**
     * Envia una cadena de bloques nueva
     *
     * @throws URISyntaxException
     */
    public static void callSaveBlockchainAPI() throws URISyntaxException {

        try {
            // Solicito la cadena actual del nodo
            ResponseEntity<List<Block>> resp = restTemplate.exchange(GET_ALL_BLOCKS_API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Block>>() {
            });

            List<Block> newBlockchain = resp.getBody();
            System.out.println(newBlockchain);

            Block lastBlock = newBlockchain.get(newBlockchain.size() - 1);

            // Agrego un nuevo bloque
            BlockDTO dto = new BlockDTO(lastBlock.getIndex() + 1, "Cadena mas Larga", lastBlock.getHash());
            newBlockchain.add(new Block(dto.getIndex(), dto.getData(), dto.getTimestamp(), dto.getPreviousHash(), dto.getHash()));

            // Realizo el post de la cadena con el bloque nuevo
            RequestEntity<List<Block>> request = RequestEntity
                    .post(new URI(SAVE_BLOCKCHAIN_API))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newBlockchain);
            ResponseEntity<List<Block>> res = restTemplate.exchange(
                    SAVE_BLOCKCHAIN_API,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<Block>>() {
                    }
            );

            // Imprimo la cadena
            List<Block> result = res.getBody();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json2 = gson.toJson(result);

            System.out.println(json2);
        } catch (Exception e) {
            System.out.println("Error SaveBlockchain");
        }

    }

    /**
     * Solicita las urls de los nodos en la red
     */
    public static void callGetAllUrlsNodesAPI() {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(GET_URLS_NODES_API, List.class);

            System.out.println();
            System.out.println("GET All StatusCode = " + response.getStatusCode());
            System.out.println("GET All Headers = " + response.getHeaders());
            System.out.println("GET Body (object list): ");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(response.getBody());

            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error callGetAllUrlsNodes");
        }

    }

    /**
     * Agrega un nodo a la red
     */
    private static void callAddNodeAPI() {
        try {
            NodeDTO newNode = new NodeDTO();
            newNode.setHost("localhost");
            newNode.setPort(20064);

            ResponseEntity<NodeDTO> response = restTemplate.postForEntity(ADD_NODE_API, newNode, NodeDTO.class);

            System.out.println();
            System.out.println("POST executed");
            System.out.println("POST StatusCode = " + response.getStatusCode());
            System.out.println("POST Body (object): ");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(response.getBody());

            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error callAddNodeAPI");
        }

    }
}
