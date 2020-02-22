package linkcollection.publish.controller;

import linkcollection.publish.service.PushService;
import linkcollection.retrofit.LinkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Random;

@RestController("/")
public class PushController {

    @Autowired
    private PushService pushService;

    @RequestMapping("select")
    public LinkedList<String> selectByType(@RequestParam("type") String types) {
        LinkedList<String> result = new LinkedList<>();
        String[] ts = types.split(",");
        for (String type :
                ts) {
            LinkedList<Long> ids = pushService.selectAllByType(type);
            for (long id : ids) {
                // 对结果进行乱序处理
                int random = 0;
                String tmp = "";
                if (result.size() > 0) {
                    random = new Random().nextInt(result.size());
                    tmp = result.get(random);
                }
                String linkInfo = LinkController.instance().searchById(id + "");
                result.add(linkInfo);
                if (result.size() > 1) {
                    result.remove(random);
                    result.add(tmp);
                }
            }
        }
        return result;
    }

    @RequestMapping("insert")
    public boolean insert(@RequestParam("type") String type, @RequestParam("linkId") long linkId) {
        return pushService.insert(type, linkId);
    }

    @RequestMapping("delete")
    public boolean delete(@RequestParam("linkId") long linkId) {
        return pushService.delete(linkId);
    }
}
