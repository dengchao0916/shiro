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
 * @description :
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
