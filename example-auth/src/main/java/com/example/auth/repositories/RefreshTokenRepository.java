package com.example.auth.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.auth.models.entites.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
	@Modifying
	@Query(value = "delete from refresh_tokens where user_device_id = :deviceId ",  nativeQuery = true)
	void deleteByDeviceId(@Param("deviceId") UUID deviceId);
	
	@Query(value = "select * from refresh_tokens r join users u on r.user_id = u.id "
												+ "join user_devices d on r.user_device_id = d.id "
				 + "where d.client_id=:clientId and u.username=:username", nativeQuery = true)
	Optional<RefreshToken> findRefreshTokenByClientIdAndUsername(@Param("clientId") String clientId, @Param("username") String username);
	
	@Query(value = "select * from refresh_tokens where user_device_id=:userDevice", nativeQuery = true)
	Optional<RefreshToken> findByUserDevice(@Param("userDevice") UUID userDevice);

	@Query(value = "select * from refresh_tokens r where r.datetime_expiration < ( now() at time zone 'America/Argentina/Buenos_Aires' )", nativeQuery = true)
	Optional< List< RefreshToken> > findExpireRefreshToken();
	
}
