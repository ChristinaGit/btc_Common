package com.btc.common.event.notice;

public interface ManagedNoticeEvent extends NoticeEvent {
    boolean hasHandlers();

    void removeAllHandlers();

    void rise();
}
