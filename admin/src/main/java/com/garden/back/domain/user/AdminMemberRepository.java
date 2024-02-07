package com.garden.back.domain.user;

import com.garden.back.domain.user.response.MemberInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {

    @Query(value = """
                   SELECT m.id, COUNT(p.id)
                   FROM members m
                   JOIN posts p ON m.id = p.post_author_id
                   GROUP BY m.id
                   ORDER BY COUNT(p.id) DESC
                   LIMIT :limit OFFSET :offset
                   """, nativeQuery = true)
    List<Object[]> findAllByPostCountDesc(@Param("offset") int offset, @Param("limit") int limit);

    List<MemberInfoResponse> findAllByMemberMannerGrade(MemberMannerGrade memberMannerGrade, Pageable pageable);
}
