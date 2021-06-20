package com.app.blockchain.service;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.dto.NodeDTO;
import com.app.blockchain.model.Block;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public interface INodeService {
    void addNode(NodeDTO newNode);

     void sendBlockNetwork(String endpoint, Object data);

    Set<URL> getUrlNodes();

}
