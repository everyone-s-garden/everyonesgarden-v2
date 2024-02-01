package com.garden.back.notification.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationRepository : JpaRepository<NotificationEntity, Long> {
    //fun findAllByMemberId(memberId: Long): List<NotificationEntity>
    @Query("select n from NotificationEntity n where n.memberId = ?1")
    fun findAllByMemberId(memberId: Long): List<NotificationEntity>

    //fun findAllByMemberIdAndIsReadIsFalse(memberId: Long): List<NotificationEntity>
    @Query("select noti from NotificationEntity noti where noti.memberId = :memberId and noti.isRead = false")
    fun findAllUnread(memberId: Long): List<NotificationEntity>
}
