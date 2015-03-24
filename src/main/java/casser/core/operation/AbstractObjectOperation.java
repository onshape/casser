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
package casser.core.operation;

import java.util.concurrent.Future;

import casser.core.AbstractSessionOperations;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public abstract class AbstractObjectOperation<E, O extends AbstractObjectOperation<E, O>> extends AbstractOperation<E, O> {

	public abstract BuiltStatement buildStatement();
	
	public abstract E transform(ResultSet resultSet);
	
	public AbstractObjectOperation(AbstractSessionOperations sessionOperations) {
		super(sessionOperations);
	}
	
	public E sync() {
		
		ResultSet resultSet = sessionOperations.executeAsync(buildStatement()).getUninterruptibly();

		return transform(resultSet);
	}
	
	public Future<E> async() {

		ResultSetFuture resultSetFuture = sessionOperations.executeAsync(buildStatement());

		ListenableFuture<E> future = Futures.transform(resultSetFuture, new Function<ResultSet, E>() {

			@Override
			public E apply(ResultSet resultSet) {
				return transform(resultSet);
			}

		}, sessionOperations.getExecutor());
		
		return future;
	}
	
	
}