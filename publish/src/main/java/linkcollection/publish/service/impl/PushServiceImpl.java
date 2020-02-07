package linkcollection.publish.service.impl;

import linkcollection.publish.mapper.PushMapper;
import linkcollection.publish.service.PushService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;

@Service
public class PushServiceImpl implements PushService {

    @Resource
    private PushMapper pushMapper;

    @Override
    public LinkedList<Long> selectAllByType(String type) {
        LinkedList<Long> list = pushMapper.selectAllByType(type);
        return list == null ? new LinkedList<Long>() : list;
    }

    @Override
    public boolean insert(String type, long linkId) {
        return pushMapper.insert(type, linkId);
    }
}
