/*******************************************************************************
 * $Author: eques
 * $RCSfile: $
 * $Revision: $
 * $Id: $
 *
 * Copyright (c) 2013 eques. All rights reserved.
 ******************************************************************************/

package ai;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import ai.pages.HomePage;

public class Application extends WebApplication {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.protocol.http.WebApplication#newSession(org.apache.
	 * wicket.Request, org.apache.wicket.Response)
	 */
	@Override
	public Session newSession(Request request, Response response) {
		return new AiSession(request);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}
}
