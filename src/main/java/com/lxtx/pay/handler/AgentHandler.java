package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.Agent;
import com.lxtx.pay.pojo.AgentStatistics;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AgentHandler  extends SimpleIbatisEntityHandler<Agent> {


    public Agent queryOne(Map map){
        return  queryForObject("queryOne",map);

    }
    public List<Agent> queryAllagent(Map map){
        return queryForList("queryAllagent",map);
    }

    public int deleteagent(Integer Id){
        return super.delete(Id);
    }

    public List<AgentStatistics> agentStatistics(){
        return queryForList("agentStatistics");


    }

    public int updateStatus(Agent agent){
        return update("updateStatus",agent);
    }

    public List<Agent> selectWithCpinfo(Map map){
        return queryForList("selectWithCpinfo",map);
    }
}