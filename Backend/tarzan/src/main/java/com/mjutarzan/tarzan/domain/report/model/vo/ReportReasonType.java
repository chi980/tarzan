package com.mjutarzan.tarzan.domain.report.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportReasonType {
    SPAM_AD("스팸·광고"),
    OFFENSIVE("욕설·비방·혐오"),
    SCAM("사칭·사기"),
    ETC("기타");

    private final String kor;
}
