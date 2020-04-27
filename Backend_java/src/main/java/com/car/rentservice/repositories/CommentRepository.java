package com.car.rentservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.car.rentservice.modal.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {

	@Query(value = "SELECT * FROM comments c WHERE c.user_id = :userId", nativeQuery = true)
	List<Comments> findByUserId(@Param("userId") Long userId);
	List<Comments> findBySerialNumber(String serialNumber);
}
