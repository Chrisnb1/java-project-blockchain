package com.app.blockchain.repository;

import com.app.blockchain.model.Block;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlockRepository extends MongoRepository<Block, String> {
}
