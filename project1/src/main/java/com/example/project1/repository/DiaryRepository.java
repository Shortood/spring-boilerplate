package com.example.project1.repository;

import com.example.project1.domain.Diary;
import com.example.project1.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByIdAndUser(Long id, User user);

    @Query(value = "SELECT * FROM diaries d WHERE MATCH(title, content) AGAINST(:word);",
            countQuery = "SELECT * FROM diaries d WHERE MATCH(title, content) AGAINST(:word);", nativeQuery = true)
    Page<Diary> fullTextSearch(@Param("word") String word, Pageable pageable);

    @Query(value = "UPDATE diaries d set d.user_id = 1 where d.user_id = :userId", nativeQuery = true)
    @Modifying
    void updateDiaryByUserId(@Param("userId") Long userId);
}
