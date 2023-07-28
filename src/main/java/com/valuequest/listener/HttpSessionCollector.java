package com.valuequest.listener;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HttpSessionCollector implements HttpSessionListener {

    private static final ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> userSessionCounts = new ConcurrentHashMap<>();
    private static final int MAX_CONCURRENT_SESSIONS = 1; // Maximum allowed concurrent sessions per user

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        sessions.put(session.getId(), session);

        // Update the user session count
        String username = (String) session.getAttribute("username"); // Replace "username" with the actual attribute name you use to store the username in the session
        if (username != null) {
            userSessionCounts.put(username, userSessionCounts.getOrDefault(username, 0) + 1);

            // Check if the user has exceeded the maximum allowed concurrent sessions
            if (userSessionCounts.get(username) > MAX_CONCURRENT_SESSIONS) {
                // Invalidate the newly created session as it exceeds the limit
                session.invalidate();
            }
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        sessions.remove(session.getId());

        // Update the user session count when a session is destroyed
        String username = (String) session.getAttribute("username"); // Replace "username" with the actual attribute name you use to store the username in the session
        if (username != null) {
            userSessionCounts.put(username, userSessionCounts.getOrDefault(username, 1) - 1);
        }
    }

    public static HttpSession find(String sessionId) {
        return sessions.get(sessionId);
    }
}
