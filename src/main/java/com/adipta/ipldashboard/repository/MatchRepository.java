package com.adipta.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adipta.ipldashboard.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> 
{
	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1,String teamName2,Pageable pageable);
	
	List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
																			String teamName1,LocalDate date1,LocalDate date2,
																			String teamName2,LocalDate date3,LocalDate date4
																			);
	
	@Query("select m from Match m where (m.team1=:teamName or m.team2=:teamName) and m.date between :dateStart and :dateEnd order by date desc")
	List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
												@Param("dateStart") LocalDate startDate,
												@Param("dateEnd")LocalDate endDate);
	
	default List<Match> findLatestMatchByTeam(String teamName,int count)
	{
		Pageable pageable=PageRequest.of(0, count);
		return getByTeam1OrTeam2OrderByDateDesc(teamName,teamName,pageable);
	}
	
	default List<Match> getMatchesForTeamByYear(String teamName,int year)
	{
		LocalDate startDate=LocalDate.of(year, 1, 1);
		LocalDate endDate=LocalDate.of(year+1, 1, 1);
//		return getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(teamName, startDate, endDate,
//																			teamName, startDate, endDate);
		return getMatchesByTeamBetweenDates(teamName,startDate,endDate);
	}
}