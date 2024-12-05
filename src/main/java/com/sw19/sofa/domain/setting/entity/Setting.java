package com.sw19.sofa.domain.setting.entity;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.global.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean is_remind;
    private Boolean is_recommend;
    private Boolean is_notice;

    @Builder
    public Setting(Member member, Boolean is_remind, Boolean is_recommend, Boolean is_notice) {
        this.member = member;
        this.is_remind = is_remind;
        this.is_recommend = is_recommend;
        this.is_notice = is_notice;
    }

    public String getEncryptId() {
        return EncryptionUtil.encrypt(this.id);
    }

    public void toggleRemindAlarm() {
        this.is_remind = !this.is_remind;
    }

    public void toggleRecommendAlarm() {
        this.is_recommend = !this.is_recommend;
    }

    public void toggleNoticeAlarm() {
        this.is_notice = !this.is_notice;
    }

}
