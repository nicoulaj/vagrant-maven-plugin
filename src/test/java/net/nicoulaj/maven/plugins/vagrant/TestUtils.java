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
package net.nicoulaj.maven.plugins.vagrant;

import java.io.File;
import java.io.IOException;

import static java.io.File.createTempFile;

/**
 * Static utilities for tests.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
public class TestUtils {

    public static File getTempDirectory() {
        final File tmp = new File("target/tests-workspace");
        tmp.delete();
        tmp.mkdir();
        return tmp;
    }

    public static File getUniqueTempDirectory() {
        try {
            final File tmp = createTempFile("test-", "", getTempDirectory());
            tmp.delete();
            tmp.mkdir();
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException("Failed creating temporary directory", e);
        }
    }
}
