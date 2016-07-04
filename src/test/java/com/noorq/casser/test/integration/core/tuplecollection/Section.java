/*
 *      Copyright (C) 2015 The Casser Authors
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
package com.noorq.casser.test.integration.core.tuplecollection;

import com.noorq.casser.mapping.annotation.Column;
import com.noorq.casser.mapping.annotation.Tuple;

@Tuple
public interface Section {

	@Column(ordinal=0)
	String title();
	
	@Column(ordinal=1)
	int page();
	
}
