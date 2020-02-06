package linkcollection.userinfo.util;

import linkcollection.userinfo.entity.UserLoginInfo;

@SuppressWarnings("ALL")
public final class GenerateUserInfo {

    public static final UserLoginInfo generateUserLoginInfo(String name, String pwd) {
        String type;
        long headId;
        int tailId;

        final int HEAD_FINAL_LENGTH = 5;
        // 分隔字符串为字符数组
        char[] chars = name.toCharArray();
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
        String tail = name.substring(tailIndex);
        // 要生成的头部标识
        String head = name.substring(0, tailIndex);
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
            // 头部标识位数不够，取最后以位补足
            if (count < HEAD_FINAL_LENGTH) {
                head += chars[headLength - 1];
            }
        }

        String headCode = generateUnicode(head);
        if (headCode.length() == 0) {
            headCode = "0";
        }
        if (name.contains("@")) {
            type = "mail";
        } else {
            type = "nick";
        }
        headId = Long.valueOf(headCode.substring(0, headCode.length() > 19 ? 19 : headCode.length())) % Long.MAX_VALUE;
        tailId = Integer.valueOf(generateUnicode(tail));

        return new UserLoginInfo(headId, tailId, name, pwd, type);
    }

    private static String generateUnicode(String origin) {
        // 分隔字符串为字符数组
        char[] chars = origin.toCharArray();
        // 生成的Unicode
        String unicode = "";
        for (char c : chars) {
            // 每个字符转化为Unicode编码，进行组合
            unicode += Integer.toString(c);
        }
        return unicode;
    }
}
