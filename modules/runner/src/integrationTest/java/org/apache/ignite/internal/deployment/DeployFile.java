/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.deployment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.ignite.internal.util.IgniteUtils;

class DeployFile {
    private final Path file;

    private final long expectedSize;

    private final int replicaTimeout;

    DeployFile(Path file, long expectedSize, int replicaTimeout) throws IOException {
        this.file = file;
        this.expectedSize = expectedSize;
        this.replicaTimeout = replicaTimeout;
        ensureExists();
    }

    private void ensureExists() throws IOException {
        if (!Files.exists(file)) {
            IgniteUtils.fillDummyFile(file, expectedSize);
        }
    }

    Path file() {
        return file;
    }

    long expectedSize() {
        return expectedSize;
    }

    int replicaTimeout() {
        return replicaTimeout;
    }
}
