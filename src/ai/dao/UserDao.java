/*******************************************************************************
 * $Author: $
 * $RCSfile: $
 * $Revision: $
 * $Id: $
 *
 * Copyright (c) 2001 Citadele Bank, Latvia. All rights reserved.
 ******************************************************************************/

package ai.dao;

import org.springframework.stereotype.Component;

import ai.domain.User;


/**
 * @author Iger
 * 
 */
@Component
public class UserDao extends GenericDao<User> {

	public User getByName(String userName) {
		User testUser = new User();
		testUser.setFirstName("Laila");
		testUser.setPassword("123");
		return testUser;
	}

	@Override
	protected Class<User> getTargetClass() {
		return User.class;
	}
}
