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

import casser.core.AbstractSessionOperations;
import casser.core.Filter;
import casser.mapping.CasserMappingEntity;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;

public final class CountOperation extends AbstractFilterOperation<Long, CountOperation> {

	private final CasserMappingEntity<?> entity;
	
	public CountOperation(AbstractSessionOperations sessionOperations, CasserMappingEntity<?> entity) {
		super(sessionOperations);
		
		this.entity = entity;
	}

	@Override
	public BuiltStatement buildStatement() {
		
		Select select = QueryBuilder.select().countAll().from(entity.getTableName());
		
		if (filters != null && !filters.isEmpty()) {
		
			Where where = select.where();
			
			for (Filter<?> filter : filters) {
				where.and(filter.getClause());
			}
		}
		
		return select;
	}
	
	@Override
	public Long transform(ResultSet resultSet) {
		return resultSet.one().getLong(0);
	}

}