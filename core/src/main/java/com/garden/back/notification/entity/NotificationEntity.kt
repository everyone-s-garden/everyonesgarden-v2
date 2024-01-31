package com.garden.back.notification.entity

import com.garden.back.notification.domain.NotificationType
import jakarta.persistence.*

@Table(name = "notification")
@Entity
class NotificationEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "title")
    val title: String = "",

    @Column(name = "summary")
    val summary: String? = null,

    @Column(name = "content")
    val content: String = "",

    @Column(name = "is_read")
    var isRead: Boolean = false,

    @Column(name = "is_sent")
    val isSent: Boolean = false,

    @Column(name = "member_id")
    val memberId: Long = 0,

    @Column(name = "recipient")
    val recipient: String = "",

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    val type: NotificationType = NotificationType.EMAIL,
) {
    fun isAuthorized(memberId: Long): Boolean {
        return this.memberId == memberId
    }

    fun markAsRead(): NotificationEntity {
        this.isRead = true
        return this
    }
}
