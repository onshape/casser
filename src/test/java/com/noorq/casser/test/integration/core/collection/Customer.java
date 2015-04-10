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
package com.noorq.casser.test.integration.core.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.DataType.Name;
import com.noorq.casser.mapping.DataTypeName;
import com.noorq.casser.mapping.PartitionKey;
import com.noorq.casser.mapping.Table;

@Table
public interface Customer {

	@PartitionKey
	UUID id();
	
	@DataTypeName(value = Name.SET, types={Name.TEXT})
	Set<String> aliases();
	
	@DataTypeName(value = Name.LIST, types={Name.TEXT})
	List<String> name();
	
	@DataTypeName(value = Name.MAP, types={Name.TEXT, Name.TEXT})
	Map<String, String> properties();

}
