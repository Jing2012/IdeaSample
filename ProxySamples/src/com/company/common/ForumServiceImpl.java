package com.company.common;

import com.company.common.ForumService;

/**
 * Created by jingjing.hu on 2017/9/13.
 */
public class ForumServiceImpl implements ForumService {

    @Override
    public void removeTopic(int topicId) {
        System.out.println("模拟删除Topic记录：" + topicId);
        try {
            Thread.currentThread().sleep(20L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeForum(int forumId) {
        System.out.println("模拟删除Forum记录：" + forumId);
        try {
            Thread.currentThread().sleep(40L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
