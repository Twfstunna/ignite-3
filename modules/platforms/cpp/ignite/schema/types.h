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

#pragma once

#include <ignite/common/bytes_view.h>

#include <cstdint>
#include <optional>
#include <vector>

namespace ignite {

/** C++ version of java int. Used as column number, etc. */
using IntT = int32_t;

/** Data size for columns and entire rows too. */
using SizeT = uint32_t;

/** Non-existent column/element number. */
static constexpr IntT NO_NUM = -1;

/** Binary value for a potentially nullable column. */
using element_view = std::optional<bytes_view>;

/** A set of binary values for a whole or partial row. */
using tuple_view = std::vector<element_view>;

/** A set of binary values for the key part of a row. */
using key_tuple_view = std::vector<bytes_view>;

} // namespace ignite