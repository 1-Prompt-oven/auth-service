package com.promptoven.authservice.adaptor.infrastructure.jpa.entity;

import com.promptoven.authservice.domain.OauthInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "oauthinfo")
@NoArgsConstructor
@AllArgsConstructor
public class OauthInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String memberUUID;
	private String provider;
	private String providerID;

	public static OauthInfoEntity fromDomain(OauthInfo oauthInfo) {
		return OauthInfoEntity.builder()
			.memberUUID(oauthInfo.getMemberUUID())
			.provider(oauthInfo.getProvider())
			.providerID(oauthInfo.getProviderID())
			.build();
	}

	public OauthInfo toDomain() {
		return OauthInfo.builder()
			.memberUUID(this.memberUUID)
			.provider(this.provider)
			.providerID(this.providerID)
			.build();
	}

}
