package com.dengchao.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * @author : dengchao
 * @description : shiro自己实现了一套session管理机制 不用借助web容器使用session
 *                通过自己实现session管理，将session放进request中，减少从redis中读取的次数
 * @create : 2019/7/14
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        if (null != request && null != sessionId) {
            Session session = (Session)request.getAttribute(sessionId.toString());
            if (null != session) {
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (null != request && null != sessionId) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
