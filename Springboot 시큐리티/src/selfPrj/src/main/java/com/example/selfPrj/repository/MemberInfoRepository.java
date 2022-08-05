package com.example.selfPrj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.selfPrj.entity.MemberInfo;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Integer> {
	Optional<MemberInfo> findByUsername(String userName);
}
