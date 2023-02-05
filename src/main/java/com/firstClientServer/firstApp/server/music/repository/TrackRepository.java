package com.firstClientServer.firstApp.server.music.repository;

import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<TrackEntity,Long> {


}
