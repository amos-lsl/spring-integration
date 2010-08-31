/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.channel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.channel.BeanFactoryChannelResolver;
import org.springframework.integration.support.channel.ChannelResolutionException;

/**
 * @author Mark Fisher
 */
public class BeanFactoryChannelResolverTests {

	@Test
	public void lookupRegisteredChannel() {
		GenericApplicationContext context = new GenericApplicationContext();
		QueueChannel testChannel = new QueueChannel();
		testChannel.setBeanName("testChannel");
		context.getBeanFactory().registerSingleton("testChannel", testChannel);
		BeanFactoryChannelResolver resolver = new BeanFactoryChannelResolver(context);
		MessageChannel lookedUpChannel = resolver.resolveChannelName("testChannel");
		assertNotNull(testChannel);
		assertSame(testChannel, lookedUpChannel);
	}

	@Test(expected = ChannelResolutionException.class)
	public void lookupNonRegisteredChannel() {
		GenericApplicationContext context = new GenericApplicationContext();
		BeanFactoryChannelResolver resolver = new BeanFactoryChannelResolver(context);
		resolver.resolveChannelName("noSuchChannel");
	}

}
