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
package com.noorq.casser.test.integration.core.prepared;

import com.noorq.casser.mapping.Column;
import com.noorq.casser.mapping.PartitionKey;
import com.noorq.casser.mapping.Table;

@Table("cars")
public interface Car {

	@PartitionKey(ordinal=1)
	String make();
	
	@PartitionKey(ordinal=2)
	String model();

	@Column
	int year();
	
	@Column
	double price();
	
}
