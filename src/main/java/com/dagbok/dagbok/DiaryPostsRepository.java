package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DiaryPostsRepository extends CrudRepository<DiaryPosts, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE DiaryPosts d SET d.heading = ?1, d.mainText = ?2, d.date = ?3 WHERE d.id = ?4")
    void editPost(String newHeading, String newMainText, LocalDate newDate, int id);

    @Query("SELECT d FROM DiaryPosts d WHERE d.date <= CURRENT_DATE ORDER BY d.date ASC")
    List<DiaryPosts> orderByAndDateNotHappened();

    @Query("SELECT d FROM DiaryPosts d WHERE d.date BETWEEN ?1 AND ?2 ORDER BY d.date ASC")
    List<DiaryPosts> orderByAnddatebetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT d FROM DiaryPosts d WHERE d.date = CURRENT_DATE")
    List<DiaryPosts> todaysDate();
}