package ai;


import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ai.domain.User;

public class AiSession extends WebSession {

	private User user;
	
	public AiSession(Request request) {
		super(request);
	}

	public static AiSession get() {
		return (AiSession) Session.get();
	}

	public boolean isAuthenticated() {
		return user != null;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
