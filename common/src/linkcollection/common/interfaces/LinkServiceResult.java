package linkcollection.common.interfaces;

public interface LinkServiceResult {

    /**
     * 在ES中查询所有匹配key的链接
     * @param linksAsJson 所有匹配链接
     */
    void searchAllByKey(String linksAsJson);

    /**
     * 在ES中查询链接ID对应的所有相关信息
     * @param linkAsJson 链接的相关信息
     */
    void searchLinkInfoById(String linkAsJson);

    /**
     * 在数据库中查询链接地址对应的所有相关信息
     * @param linkAsJson 链接的相关信息
     */
    void selectAllByLink(String linkAsJson);

    /**
     * 在数据库中查询链接ID对应的所有相关信息
     * @param linkAsJson 链接的相关信息
     */
    void selectAllById(String linkAsJson);

    /**
     * 在数据库中查询链接ID对应的链接地址
     * @param link 链接地址
     */
    void selectLinkById(String link);

    /**
     * 向服务器提交一个收藏链接
     * @param successful 收藏结果
     */
    void putLink(boolean successful);

    /**
     * 将ES中对应链接ID的链接删除
     * @param result 删除结果
     */
    void deleteLinkById(String result);

    /**
     * 检查服务器是否存在该链接
     * @param exist 是否存在
     */
    void existLink(boolean exist);
}
