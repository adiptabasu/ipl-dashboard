package com.adipta.ipldashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adipta.ipldashboard.model.Match;
import com.adipta.ipldashboard.model.Team;
import com.adipta.ipldashboard.repository.MatchRepository;
import com.adipta.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController 
{
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@GetMapping("/{teamName}")
	public Team getTeam(@PathVariable String teamName)
	{
		Team team = teamRepository.findByTeamName(teamName);
		team.setMatches(matchRepository.findLatestMatchByTeam(teamName, 4));
		return team;
	}
	
	@GetMapping("/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable(name = "teamName") String teamName,@RequestParam(name = "year") int year)
	{
		return matchRepository.getMatchesForTeamByYear(teamName, year);
	}
	
	@GetMapping
	public Iterable<Team> getAllTeams()
	{
		return teamRepository.findAll();
	}
}