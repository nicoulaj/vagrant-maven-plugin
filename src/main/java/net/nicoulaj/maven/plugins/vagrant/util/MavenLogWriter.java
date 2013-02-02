/*
 * Copyright 2013 vagrant-maven-plugin contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nicoulaj.maven.plugins.vagrant.util;

import org.apache.maven.plugin.logging.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A {@link java.io.Writer} that outputs to a Maven {@link Log}.
 *
 * // FIXME Buggy ? Missing output when running with {@link net.nicoulaj.maven.plugins.vagrant.binding.RubyBinding}.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
public final class MavenLogWriter extends PrintWriter {

    /** System-dependent line separator character. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public MavenLogWriter(final Log log) {
        this(log, false);
    }

    public MavenLogWriter(final Log log, final boolean error) {
        super(new ByteArrayOutputStream() {
            @Override
            public void flush() throws IOException {
                synchronized (this) {
                    super.flush();
                    final String buffer = toString();
                    if (buffer.length() > 0 && !LINE_SEPARATOR.equals(buffer))
                        if (error) log.error(buffer);
                        else log.info(buffer);
                    super.reset();
                }
            }
        }, true);
    }
}
