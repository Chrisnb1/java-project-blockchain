package com.app.blockchain.repository;

import com.app.blockchain.dto.BlockDTO;
import com.app.blockchain.model.Block;
import com.app.blockchain.model.Node;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface INodeRepository extends MongoRepository<Node, String> {

}
