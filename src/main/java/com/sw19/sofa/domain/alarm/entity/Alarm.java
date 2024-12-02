package com.sw19.sofa.domain.alarm.entity;

import com.sw19.sofa.domain.alarm.enums.AlarmType;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.common.entity.BaseTimeEntity;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "is_read")
    private Boolean isRead;
    private AlarmType type;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Alarm(String content, Boolean isRead, AlarmType type, Member member) {
        this.content = content;
        this.isRead = isRead;
        this.type = type;
        this.member = member;
    }

    public String getEncryptId() {
        return EncryptionUtil.encrypt(this.id);
    }

    public void setRead(){
        this.isRead = true;
    }
}
