package com.sw19.sofa.global.common.constants;

import java.util.List;

public class Constants {
    //RecycleBin
    public static final String recycleBinName="휴지통";

    //Scheduler & Batch
    public static final String moveRemind = "moveUnusedLinkListToRemindJob";
    public static final String deleteRecycleBin = "deleteExpiredLinkCardListInRecycleBinJob";
    public static final String remindNotify = "RemindNotifyGroup";
    public static final String deleteExpiredAlarm = "DeleteAlarmGroup";
    public static final int batchConcurrencyLimit = 5;
    public static final int batchChunkSize = 10;

    //search History
    public static final String SEARCH_KEYWORDS_PREFIX = "search:keywords:";
    public static final String SEARCH_TAGS_PREFIX = "search:tags:";

    //AI
    public static final List<String> DEFAULT_FOLDER_CATEGORIES = List.of(
            "CS",
            "AI & 머신러닝",
            "클라우드 컴퓨팅",
            "프론트엔드",
            "백엔드",
            "빅데이터 & 데이터 분석",
            "모바일",
            "DevOps & CI/CD",
            "QA & 테스팅",
            "보안",
            "게임 & 메타버스",
            "블록체인 & Web3",
            "핀테크(FinTech)",
            "스마트시티 & 자율주행",
            "사물인터넷 IoT",
            "헬스테크(HealthTech)",
            "에듀테크(EduTech)",
            "IT 커리어 관리",
            "기술 학습 리소스",
            "UX/UI 디자인",
            "BX 디자인",
            "그래픽 디자인",
            "디지털 콘텐츠 제작",
            "IT 기획",
            "IT 마케팅"
            );
}
