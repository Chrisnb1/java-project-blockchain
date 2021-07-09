package com.app.blockchain.service;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.model.Block;
import com.app.blockchain.model.Node;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public interface INodeService {
    Node addNode(NodeDTO newNode);

     void sendBlockNetwork(String endpoint, Object data);

    Set<URL> getUrlNodes();

}
