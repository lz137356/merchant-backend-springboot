package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.OperationLog;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OperationLogHandler extends SimpleIbatisEntityHandler<OperationLog> {

    public List<OperationLog> findByPage(Map<String, Object> params) {
        return queryForList("findByPage", params);
    }

    public int countTotal(Map<String, Object> params) {
        return queryForObject("countTotal", params);
    }

    public void add(OperationLog log) {
        insert(log);
    }

    public void insertOperationLog(OperationLog log) {
        insert("insert", log);
    }
}
