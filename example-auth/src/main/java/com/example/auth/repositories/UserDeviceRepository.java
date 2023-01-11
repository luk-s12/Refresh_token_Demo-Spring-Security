package com.example.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.auth.models.entites.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository< UserDevice, Long>{
	
	@Modifying
	@Query(value = "delete from user_devices where id = :id",  nativeQuery = true)
	void deleteById(@Param("id") UUID id);

	Optional<UserDevice> findByClientId(String clientId);

	@Query(value = "select * from user_devices d join users u on d.user_id = u.id where d.client_id=:clientId and u.username=:username", nativeQuery = true)
	Optional<UserDevice> findByClientIdAndUsername(@Param("clientId") String clientId, @Param("username") String username);
}
