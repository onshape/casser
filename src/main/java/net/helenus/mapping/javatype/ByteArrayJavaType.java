/*
 *      Copyright (C) 2015 The Helenus Authors
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
package net.helenus.mapping.javatype;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Metadata;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Function;
import net.helenus.core.SessionRepository;
import net.helenus.mapping.ColumnType;
import net.helenus.mapping.annotation.Types;
import net.helenus.mapping.convert.ByteArrayToByteBufferConverter;
import net.helenus.mapping.convert.ByteBufferToByteArrayConverter;
import net.helenus.mapping.convert.TypedConverter;
import net.helenus.mapping.type.AbstractDataType;
import net.helenus.mapping.type.DTDataType;

public final class ByteArrayJavaType extends AbstractJavaType {

  @Override
  public Class<?> getJavaClass() {
    return byte[].class;
  }

  @Override
  public AbstractDataType resolveDataType(
      Method getter, Type genericJavaType, ColumnType columnType, Metadata metadata) {

    if (null != getter.getDeclaredAnnotation(Types.Blob.class)) {
      return new DTDataType(columnType, DataType.blob());
    }

    Types.Custom custom = getter.getDeclaredAnnotation(Types.Custom.class);

    if (null != custom) {
      return new DTDataType(columnType, DataType.custom(custom.className()));
    }

    return new DTDataType(columnType, DataType.blob());
  }

  @Override
  public Optional<Function<Object, Object>> resolveReadConverter(
      AbstractDataType dataType, SessionRepository repository) {

    return Optional.of(
        TypedConverter.create(
            ByteBuffer.class, byte[].class, ByteBufferToByteArrayConverter.INSTANCE));
  }

  @Override
  public Optional<Function<Object, Object>> resolveWriteConverter(
      AbstractDataType dataType, SessionRepository repository) {

    return Optional.of(
        TypedConverter.create(
            byte[].class, ByteBuffer.class, ByteArrayToByteBufferConverter.INSTANCE));
  }
}
