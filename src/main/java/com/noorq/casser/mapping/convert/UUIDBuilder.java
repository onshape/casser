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
package com.noorq.casser.mapping.convert;

import java.util.UUID;


public final class UUIDBuilder {

	public static final long NUM_100NS_IN_MILLISECOND = 10000L;

	public static final long NUM_100NS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;

	
	private long leastSigBits = 0x8000000000000000L;

	private long mostSigBits = 0L;

	public long getLeastSignificantBits() {
		return leastSigBits;
	}

	public long getMostSignificantBits() {
		return mostSigBits;
	}

	public UUID build() {
		return new UUID(mostSigBits, leastSigBits);
	}

	public UUIDBuilder addVersion(int version) {
		if (version < 1 || version > 4) {
			throw new IllegalArgumentException("unsupported version " + version);
		}

		mostSigBits |= ((long) (version & 0x0f)) << 12;

		return this;
	}

	public UUIDBuilder addTimestamp(long uuid100Nanos) {

		long timeLow = uuid100Nanos & 0xffffffffL;
		long timeMid = uuid100Nanos & 0xffff00000000L;
		long timeHi = uuid100Nanos & 0xfff000000000000L;

		mostSigBits |= (timeLow << 32) | (timeMid >> 16) | (timeHi >> 48);

		return this;
	}

	public UUIDBuilder addTimestampMillis(long milliseconds) {
		long uuid100Nanos = milliseconds * NUM_100NS_IN_MILLISECOND + NUM_100NS_SINCE_UUID_EPOCH;
		return addTimestamp(uuid100Nanos);
	}

	public UUIDBuilder addClockSequence(int clockSequence) {
		leastSigBits |= ((long) (clockSequence & 0x3fff)) << 48;
		return this;
	}

	public UUIDBuilder addNode(long node) {
		leastSigBits |= node & 0xffffffffffffL;
		return this;
	}

	public static long getTimestampMillis(UUID uuid) {
		return (uuid.timestamp() - NUM_100NS_SINCE_UUID_EPOCH) / NUM_100NS_IN_MILLISECOND;
	}

}
