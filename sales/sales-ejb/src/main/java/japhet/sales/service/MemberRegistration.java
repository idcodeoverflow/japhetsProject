/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package japhet.sales.service;

import japhet.sales.model.Member;
import japhet.sales.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Singleton
@Startup
public class MemberRegistration {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;
    
    @PostConstruct
    public void init(){
    	em = Persistence.createEntityManagerFactory("sales").createEntityManager();
		User user = new User();
		user.setUserId(1L);
		user = (User)em.find(User.class, new Long(1));
		logger.info("DEBUGGER: ----->" + user.getName());
		
    }

    public void register(Member member) throws Exception {
        logger.info("Registering " + member.getName());
        em.persist(member);
        memberEventSrc.fire(member);
    }
}
