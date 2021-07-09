package com.app.blockchain.service;

import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.model.Node;
import com.app.blockchain.repository.INodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service("nodeService")
public class NodeService implements INodeService {

    private final IBlockService blockService;
    private final INodeRepository nodeRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public NodeService(IBlockService blockService, INodeRepository nodeRepository){
        this.blockService = blockService;
        this.nodeRepository = nodeRepository;
    }

    /**
     * Devuelve la lista de URLs de todos los nodos de la red
     * @return
     */
    @Override
    public Set<URL> getUrlNodes() {
        Set<URL> urlNodes = new HashSet<>();
        for (Node n: listAllNodes()) { urlNodes.add(n.getURL()); }
        return urlNodes;
    }

    /**
     * Añade un nodo a la red
     * @param request se solicita un nodoDTO y luego se mapea a un nodo para almacenar en la BD
     */
    @Override
    public Node addNode(NodeDTO request){
        try{
            if(validateNode(request)){
                URL url = new URL("http", request.getHost(), request.getPort(), "");
                Node newNode = new Node(url);
                return nodeRepository.save(newNode);
            }
        } catch(MalformedURLException e){
            System.out.println("Error de tipo: " + e.getMessage());
        }
        return null;
    }

    /**
     * Restramite un bloque creado a la red
     * Se utiliza Restemplate para enviar a las URLs de los nodos
     * @param endpoint
     * @param data
     */
    @Override
    public void sendBlockNetwork(String endpoint, Object data){
        System.out.println("Enviando bloque a: " + getUrlNodes());
        try{
            getUrlNodes().forEach(urlNode -> restTemplate.postForLocation(urlNode + "/", data));
        }catch (Exception e){
            System.out.println("Error al enviar el bloque : " + e.getMessage());
            return;
        }
    }

    /**
     * Lista de todos los nodos
     * @return
     */
    public List<Node> listAllNodes(){
        List<Node> nodes = new ArrayList<>();
        nodeRepository.findAll().forEach(nodes::add);
        return nodes;
    }

    /**
     * Valida un nodo
     * @param newNode
     * @return Si el nodo se encuentra en la red, retorna falso, caso contrario true
     */
    public boolean validateNode(NodeDTO newNode){
        for (Node n :listAllNodes()) {
            if(n.getURL().getPort() == newNode.getPort()){
                System.out.println("El nodo ya se encuentra en la red");
                return false;
            }
        }
        return true;
    }

}
