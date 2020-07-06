/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.iotdb.db.qp.physical.sys;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import org.apache.iotdb.db.qp.logical.Operator.OperatorType;
import org.apache.iotdb.db.qp.physical.PhysicalPlan;
import org.apache.iotdb.tsfile.read.common.Path;

public class SetTTLPlan extends PhysicalPlan {

  private String storageGroup;
  private long dataTTL;

  public SetTTLPlan() {
    super(false, OperatorType.TTL);
  }

  public SetTTLPlan(String storageGroup, long dataTTL) {
    // set TTL
    super(false, OperatorType.TTL);
    this.storageGroup = storageGroup;
    this.dataTTL = dataTTL;
  }

  public SetTTLPlan(String storageGroup) {
    // unset TTL
    this(storageGroup, Long.MAX_VALUE);
  }

  @Override
  public List<Path> getPaths() {
    return null;
  }

  @Override
  public List<String> getPathsStrings() {
    return null;
  }

  @Override
  public void serialize(DataOutputStream stream) throws IOException {
    int type = PhysicalPlanType.TTL.ordinal();
    stream.writeByte((byte) type);
    stream.writeLong(dataTTL);
    putString(stream, storageGroup);
  }

  @Override
  public void serialize(ByteBuffer buffer) {
    int type = PhysicalPlanType.TTL.ordinal();
    buffer.put((byte) type);
    buffer.putLong(dataTTL);
    putString(buffer, storageGroup);
  }

  @Override
  public void deserialize(ByteBuffer buffer) {
    this.dataTTL = buffer.getLong();
    this.storageGroup = readString(buffer);
  }

  public String getStorageGroup() {
    return storageGroup;
  }

  public void setStorageGroup(String storageGroup) {
    this.storageGroup = storageGroup;
  }

  public long getDataTTL() {
    return dataTTL;
  }

  public void setDataTTL(long dataTTL) {
    this.dataTTL = dataTTL;
  }
}