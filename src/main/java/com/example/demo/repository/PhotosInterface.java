package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Photos;
@Repository
public interface PhotosInterface extends JpaRepository<Photos, Integer> {

	
    List<Photos> findByAlbumId( int albumId);
}
