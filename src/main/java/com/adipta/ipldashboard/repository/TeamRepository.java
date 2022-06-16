package com.adipta.ipldashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adipta.ipldashboard.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> 
{
	public Team findByTeamName(String teamName);
}