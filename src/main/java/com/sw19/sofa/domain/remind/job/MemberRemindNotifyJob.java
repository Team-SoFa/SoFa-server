package com.sw19.sofa.domain.remind.job;

import com.sw19.sofa.domain.alarm.service.AlarmService;
import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.service.MemberService;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.service.RemindService;
import com.sw19.sofa.global.common.dto.MailRequestDto;
import com.sw19.sofa.global.util.EncryptionUtil;
import com.sw19.sofa.infra.mail.service.MailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberRemindNotifyJob implements Job {
    private MailService mailService;
    private RemindService remindService;
    private MemberService memberService;
    private AlarmService alarmService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
    @Autowired
    public void setRemindService(RemindService remindService) {
        this.remindService = remindService;
    }
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    @Autowired
    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        String encryptMemberId = context.getJobDetail().getKey().getName();
        Long memberId = EncryptionUtil.decrypt(encryptMemberId);

        Member member = memberService.getMemberById(memberId);
        String email = member.getEmail();

        List<Remind> remindList = remindService.getRemindListByMember(member);
        List<String> titleList = remindList.stream().map(remind -> remind.getLinkCard().getTitle()).toList();
        String content = String.join(", ", titleList);

        Map<String,Object> data = new HashMap<>();
        data.put("title", content);

        mailService.sendMail(new MailRequestDto(
                "리마인드",data,email,"리마인드"
        ));

        alarmService.addRemindAlarm(member,content);
    }
}
