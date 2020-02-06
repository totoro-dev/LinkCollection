package linkcollection.userinfo;

import linkcollection.userinfo.service.MailService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserinfoApplicationTests {

    @Resource
    private MailService mailService;

    @Test
    public void contextLoads() {
        mailService.sendMail("649676485@qq.com","1234");
    }

    @Test
    public void testStringToId() {
//        System.out.println(Long.valueOf("9223372036854775807"));
        // 需要转化为id的字符串
        String origin = "我是大学生";
        // 分隔字符串为字符数组
        char[] chars = origin.toCharArray();
        // 生成的Unicode
        String unicode = "";
        for (char c : chars) {
            // 每个字符转化为Unicode编码，进行组合
            unicode += Integer.toString(c);
        }
        System.out.println("Unicode编码 : " + unicode);
        // 分解后的10进制码，按逗号分隔，每部分对应一个9位以内的Unicode
        String int_code = "";
        int length = unicode.length();
        if (length > 9) {
            int_code = Integer.valueOf(unicode.substring(0, 9)).toString();
            // 待分解的部分数量：因为整型的最大长度为10，这里使用9作为每个分解部分的长度，避免数据溢出
            int parts = length % 9 == 0 ? length / 9 - 1 : length / 9;
            int pre = 9;
            for (int i = 0; i < parts; i++) {
                // 每9位Unicode转化为10进制码，以“，”分隔
                int_code += "," + Integer.valueOf(unicode.substring(pre, pre + 9 > length ? length : pre + 9));
                pre += 9;
            }
        } else {
            int_code = Integer.valueOf(unicode.substring(0, length)).toString();
        }
        System.out.println("十进制码 : " + int_code);

        // 译码，测试编码的正确性
//        String codes[] = int_code.split(",");
//        String int_decode = "";
//        for (String code :
//                codes) {
//            int_decode += Integer.parseUnsignedInt(code, 16);
//            System.out.println(code + " : " + Integer.parseUnsignedInt(code, 16) + "\n");
//        }
//        System.out.println("int_decode : " + int_decode);
    }

    private String generateUnicode(String origin) {
        // 分隔字符串为字符数组
        char[] chars = origin.toCharArray();
        // 生成的Unicode
        String unicode = "";
        for (char c : chars) {
            // 每个字符转化为Unicode编码，进行组合
            unicode += Integer.toString(c);
        }
//        System.out.println("Unicode编码 : " + unicode);
        return unicode;
    }

    private String generateIntUnicode(String unicode) {
        // 分解后的Int型Unicode码，按逗号分隔，每部分对应一个9位以内的Unicode
        String int_unicode = "";
        int length = unicode.length();
        if (length > 9) {
            int_unicode = Integer.valueOf(unicode.substring(0, 9)).toString();
            // 待分解的部分数量：因为整型的最大长度为10，这里使用9作为每个分解部分的长度，避免数据溢出
            int parts = length % 9 == 0 ? length / 9 - 1 : length / 9;
            int pre = 9;
            for (int i = 0; i < parts; i++) {
                // 每9位Unicode转化为10进制码，以“，”分隔
                int_unicode += "," + Integer.valueOf(unicode.substring(pre, pre + 9 > length ? length : pre + 9));
                pre += 9;
            }
        } else {
            int_unicode = Integer.valueOf(unicode.substring(0, length)).toString();
        }
//        System.out.println("Int型Unicode码 : " + int_unicode);
        return int_unicode;
    }

    @Test
    public void testDecode() {
        final int HEAD_FINAL_LENGTH = 5;
        // 需要转化为id的字符串
        String origin = "我还不是一个大学生a";
        // 分隔字符串为字符数组
        char[] chars = origin.toCharArray();
        // 尾部标识在字符串的索引
        int tailIndex = 0;
        // 尾部标识部分的长度，不超过9
        int tailLength = 0;
        // 计算尾部标识的索引
        for (int i = chars.length - 1; i > -1; i--) {
            tailLength += Integer.toString(chars[i]).length();
            if (tailLength > 9) {
                tailIndex = i + 1;
                break;
            }
        }
        // 要生成的尾部标识
        String tail = origin.substring(tailIndex);
        // 要生成的头部标识
        String head = origin.substring(0, tailIndex);
        // 当前头部部分的长度
        int headLength = tailIndex;
        // 当前长度超过头部规定最大长度，进行取舍
        if (headLength > HEAD_FINAL_LENGTH) {
            // 字符取舍时的步进长度
            int add = headLength / HEAD_FINAL_LENGTH;
            // 标识是否对中间位置的字符进行取舍
            int flag = headLength % HEAD_FINAL_LENGTH;
            // 最小中位数，如果flag大于0时，以该值为步进起始下标
            int middle = headLength / 2;
            head = String.valueOf(chars[middle]);
            int count = 1;
            if (flag > HEAD_FINAL_LENGTH / 2) {
                add++;
            }
            // 按照步进长度获取头部标识字符串
            for (int i = 1; i <= HEAD_FINAL_LENGTH / 2; i++) {
                if (count < HEAD_FINAL_LENGTH && middle - i * add >= 0) {
                    head = chars[middle - i * add] + head;
                    count++;
                }
                if (count < HEAD_FINAL_LENGTH && middle + i * add < headLength) {
                    head = head + chars[middle + i * add];
                    count++;
                }
            }
            if (count < HEAD_FINAL_LENGTH) {
                head += chars[headLength - 1];
            }
        }

        System.out.println("origin: " + origin + "\nhead: " + head + "\ntail: " + tail);

        String headCode = generateUnicode(head);
        String headId = "" + Integer.valueOf(headCode.substring(0, headCode.length() > 9 ? 9 : headCode.length())) % Integer.MAX_VALUE;
        String tailId = generateUnicode(tail);

        System.out.println("headId: " + headId);
        System.out.println("tailId: " + tailId);
    }

    private Map<String, String> checkId = new HashMap<>();
    private Map<String, String> checkOrigin = new HashMap<>();

    @Test
    public void testCorrect() {
        String[] zimu = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] url = new String[]{".com", ".cn", ".net", ".org", ".top"};
        String[] hanzi = new String[]{"啊", "阿", "吖", "嗄", "锕", "呵", "腌", "錒", "哎", "爱", "按", "唉", "安", "俺", "奥", "案", "艾", "昂", "埃", "暗",
                "吧", "不", "把", "被", "并", "比", "别", "八", "本", "帮", "版", "表", "包", "白", "部", "便", "呗", "报", "办", "班",
                "从", "才", "吃", "出", "陈", "成", "车", "此", "曾", "长", "称", "次", "村", "处", "错", "差", "查", "超", "城", "穿",
                "的", "都", "对", "等", "到", "但", "点", "大", "打", "多", "地", "得", "当", "带", "顶", "道", "队", "单", "第", "德"};
//                "","","","","","","","","","","","","","","","","","","",""};

        Date start = new Date();

        for (int i = 1; i <= 1000000; i++) {
            int length = new Random().nextInt(30);
            String origin = "";
            for (int j = 0; j < (length == 0 ? 1 : length); j++) {
                int index = new Random().nextInt(zimu.length - 1);
                origin += zimu[index == 0 ? 1 : index];
            }
            testDecodeId(i, origin);
        }
//        checkId.clear();

//        Date start = new Date();
//        for (int i = 1; i <= 1000000; i++) {
//            int length = new Random().nextInt(6);
//            String origin = "";
//            for (int j = 0; j < (length == 0 ? 1 : length); j++) {
//                int index = new Random().nextInt(hanzi.length - 1);
//                origin += hanzi[index == 0 ? 1 : index];
//            }
//            testDecodeId(i, origin);
//        }
//        checkId.clear();

//        for (int i = 1; i <= 1000000; i++) {
//            int length = new Random().nextInt(30);
//            String origin = "https://www.";
//            for (int j = 0; j < (length == 0 ? 1 : length); j++) {
//                int index = new Random().nextInt(zimu.length - 1);
//                int point = new Random().nextInt(10);
//                origin += zimu[index == 0 ? 1 : index] + (j % (point == 0 ? 9 : point) == 0 ? "." : "");
//            }
//            int index = new Random().nextInt(url.length - 1);
//            origin += url[index == 0 ? 1 : index];
//            testDecodeId(i, origin);
//        }

        Date end = new Date();
        System.out.println("测试时间：" + (end.getTime() - start.getTime()) + "ms");
    }

    private void testDecodeId(int times, String origin) {
        final int HEAD_FINAL_LENGTH = 5;
        // 需要转化为id的字符串
//        String origin = "我还不是一个大学生a";
        // 分隔字符串为字符数组
        char[] chars = origin.toCharArray();
        // 尾部标识在字符串的索引
        int tailIndex = 0;
        // 尾部标识部分的长度，不超过9
        int tailLength = 0;
        // 计算尾部标识的索引
        for (int i = chars.length - 1; i > -1; i--) {
            tailLength += Integer.toString(chars[i]).length();
            if (tailLength > 9) {
                tailIndex = i + 1;
                break;
            }
        }
        // 要生成的尾部标识
        String tail = origin.substring(tailIndex);
        // 要生成的头部标识
        String head = origin.substring(0, tailIndex);
        // 当前头部部分的长度
        int headLength = tailIndex;
        // 当前长度超过头部规定最大长度，进行取舍
        if (headLength > HEAD_FINAL_LENGTH) {
            // 字符取舍时的步进长度
            int add = headLength / HEAD_FINAL_LENGTH;
            // 标识是否对中间位置的字符进行取舍
            int flag = headLength % HEAD_FINAL_LENGTH;
            // 最小中位数，如果flag大于0时，以该值为步进起始下标
            int middle = headLength / 2;
            head = String.valueOf(chars[middle]);
            int count = 1;
            if (flag > HEAD_FINAL_LENGTH / 2) {
                add++;
            }
            // 按照步进长度获取头部标识字符串
            for (int i = 1; i <= HEAD_FINAL_LENGTH / 2; i++) {
                if (count < HEAD_FINAL_LENGTH && middle - i * add >= 0) {
                    head = chars[middle - i * add] + head;
                    count++;
                }
                if (count < HEAD_FINAL_LENGTH && middle + i * add < headLength) {
                    head = head + chars[middle + i * add];
                    count++;
                }
            }
            if (count < HEAD_FINAL_LENGTH) {
                head += chars[headLength - 1];
            }
        }

        System.out.println("测试" + times + "-->origin: " + origin + "，head: " + head + "，tail: " + tail);

        String headCode = generateUnicode(head);
        if (headCode.length() == 0) {
            headCode = "-1";
        }
        String headId = "" + Long.valueOf(headCode.substring(0, headCode.length() > 19 ? 19 : headCode.length()));
        String tailId = generateUnicode(tail);

        if (tailId.equals(checkId.get(headId))) {
            if (!origin.equals(checkOrigin.get(headId + tailId))) {
                System.err.println("origin: " + origin + " 不存在！");
                System.exit(1);
            }
            System.err.println("Id: " + headId + "-" + tailId + " 已存在！origin为" + origin);
        }
        checkId.put(headId, tailId);
        checkOrigin.put(headId + tailId, origin);

//        System.out.println("headId: " + headId);
//        System.out.println("tailId: " + tailId);
    }


    private int[] values = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    @Test
    public void testInitialLastUserId(){
        System.out.println(initialLastUserId(1,100));
    }

    private long initialLastUserId(int lastId, int rang) {
        if (rang == 1) return lastId;
        int tmp = lastId+rang/2;
        System.out.println("now:"+values[tmp]);
        if (values[tmp] != 0){
            return initialLastUserId(lastId+rang/2,rang);
        }else {
            return initialLastUserId(lastId,rang/2);
        }
    }

}