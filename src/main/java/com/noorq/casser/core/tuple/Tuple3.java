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
package com.noorq.casser.core.tuple;

import java.util.function.Function;

import com.datastax.driver.core.Row;
import com.noorq.casser.core.reflect.CasserPropertyNode;
import com.noorq.casser.mapping.CasserMappingProperty;
import com.noorq.casser.mapping.value.ColumnValueProvider;

public final class Tuple3<V1, V2, V3> {

	public final V1 v1;
	public final V2 v2;
	public final V3 v3;

	public Tuple3(V1 v1, V2 v2, V3 v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
	
	public final static class Mapper<V1, V2, V3> implements Function<Row, Tuple3<V1, V2, V3>> {

		private final ColumnValueProvider provider;
		private final CasserMappingProperty p1;
		private final CasserMappingProperty p2;
		private final CasserMappingProperty p3;
		
		public Mapper(ColumnValueProvider provider, 
				CasserPropertyNode p1, 
				CasserPropertyNode p2, 
				CasserPropertyNode p3) {
			this.provider = provider;
			this.p1 = p1.getProperty();
			this.p2 = p2.getProperty();
			this.p3 = p3.getProperty();
		}
		
		@Override
		public Tuple3<V1, V2, V3> apply(Row row) {
			return new Tuple3<V1, V2, V3>(
					provider.getColumnValue(row, 0, p1), 
					provider.getColumnValue(row, 1, p2),
					provider.getColumnValue(row, 2, p3)
					);
		}
	}

	@Override
	public String toString() {
		return "Tuple3 [v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
	}

	
	
}