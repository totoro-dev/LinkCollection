package linkcollection.publish.controller;

import linkcollection.publish.service.PushService;
import linkcollection.retrofit.LinkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

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
            for (long id :
                    ids) {
                String linkInfo = LinkController.instance().searchById(id + "");
                result.add(linkInfo);
            }
        }
        return result;
    }

    @RequestMapping("insert")
    public boolean insert(@RequestParam("type") String type, @RequestParam("linkId") long linkId) {
        return pushService.insert(type, linkId);
    }
}
