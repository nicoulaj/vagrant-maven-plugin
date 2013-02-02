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
package net.nicoulaj.maven.plugins.vagrant.binding;

import org.testng.annotations.Test;

import java.io.File;

import static net.nicoulaj.maven.plugins.vagrant.Constants.VAGRANT_VERSION;
import static net.nicoulaj.maven.plugins.vagrant.TestUtils.getTempDirectory;
import static net.nicoulaj.maven.plugins.vagrant.TestUtils.getUniqueTempDirectory;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link VagrantBinding}.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
public class VagrantBindingTest {

    @Test
    public void init_no_exception_thrown() throws Exception {
        new VagrantBinding(getUniqueTempDirectory()).cli();
    }

    @Test
    public void init_right_version_fetched() throws Exception {
        new VagrantBinding(getTempDirectory()).cli();
        assertTrue(new File(getTempDirectory(), "gems/gems/vagrant-" + VAGRANT_VERSION + "/vagrant.gemspec").isFile());
    }

    @Test
    public void cli_no_exception_thrown() throws Exception {
        new VagrantBinding(getTempDirectory()).cli();
    }
}
