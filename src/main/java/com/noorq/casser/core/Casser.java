/*
 *      Copyright (C) 2015 Noorq, Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.noorq.casser.core;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.noorq.casser.config.CasserSettings;
import com.noorq.casser.config.DefaultCasserSettings;
import com.noorq.casser.core.reflect.CasserPropertyNode;



public final class Casser {
	
	private static volatile CasserSettings settings = new DefaultCasserSettings();
	
	private static final ConcurrentMap<Class<?>, Object> dslCache = new  ConcurrentHashMap<Class<?>, Object>();
	
	private Casser() {
	}
	
	public static CasserSettings settings() {
		return settings;
	}

	public static CasserSettings configure(CasserSettings overrideSettings) {
		CasserSettings old = settings;
		settings = overrideSettings;
		return old;
	}

	public static SessionInitializer connect(Cluster cluster) {
		Session session = cluster.connect();
		return new SessionInitializer(session);
	}

	public static SessionInitializer connect(Cluster cluster, String keyspace) {
		Session session = cluster.connect(keyspace);
		return new SessionInitializer(session);
	}
	
	public static SessionInitializer init(Session session) {
		
		if (session == null) {
			throw new IllegalArgumentException("empty session");
		}
		
		return new SessionInitializer(session);
	}
	
	public static void clearDslCache() {
		dslCache.clear();
	}
	
	public static <E> E dsl(Class<E> iface) {
		return dsl(iface, iface.getClassLoader(), Optional.empty());
	}

	public static <E> E dsl(Class<E> iface, ClassLoader classLoader) {
		return dsl(iface, classLoader, Optional.empty());
	}
	
	public static <E> E dsl(Class<E> iface, ClassLoader classLoader, Optional<CasserPropertyNode> parent) {
		
		Object instance = dslCache.get(iface);
		
		if (instance == null) {
		
			instance = settings.getDslInstantiator().instantiate(iface, classLoader, parent);
			
			Object c = dslCache.putIfAbsent(iface, instance);
			if (c != null) {
				instance = c;
			}
		}
		
		return (E) instance;
	}
	
	public static <E> E wrap(Map<String, Object> map, Class<E> iface) {
		return wrap(map, iface, iface.getClassLoader());
	}

	public static <E> E wrap(Map<String, Object> map, Class<E> iface, ClassLoader classLoader) {
		return settings.getWrapperInstantiator().instantiate(map, iface, classLoader);
	}

}