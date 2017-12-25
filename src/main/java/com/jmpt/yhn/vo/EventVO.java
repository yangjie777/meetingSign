package com.jmpt.yhn.vo;

import com.jmpt.yhn.entity.Event;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by yhn on 2017/10/18.
 */
@Data
public class EventVO {
    private List<Event> eventList;
    private String page;
    private String size;
    private String isHasPage;
}
