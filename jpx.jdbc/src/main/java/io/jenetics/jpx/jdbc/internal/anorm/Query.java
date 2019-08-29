/*
 * Java GPX Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package io.jenetics.jpx.jdbc.internal.anorm;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
public class Query {

	private static final Pattern PARAM_PATTERN = Pattern.compile("\\{(\\w+?)\\}");

	private final String _sql;
	private final List<String> _names;

	private Query(final String sql, final List<String> names) {
		_sql = requireNonNull(sql);
		_names = unmodifiableList(names);
	}

	public String sql() {
		return _sql;
	}

	public List<String> names() {
		return _names;
	}

	public PreparedQuery on(final Param... params) {
		return null;
	}


	public static Query of(final String sql) {
		final List<String> names = new ArrayList<>();
		final StringBuffer parsedQuery = new StringBuffer();

		final Matcher matcher = PARAM_PATTERN.matcher(sql);
		while (matcher.find()) {
			final String name = matcher.group(1);
			names.add(name);

			matcher.appendReplacement(parsedQuery, "?");
		}
		matcher.appendTail(parsedQuery);

		return new Query(parsedQuery.toString(), names);
	}

}