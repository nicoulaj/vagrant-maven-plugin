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

import java.io.File;
import java.io.Writer;
import java.util.HashMap;

import static net.nicoulaj.maven.plugins.vagrant.Constants.VAGRANT_VERSION;
import static org.codehaus.plexus.util.StringUtils.join;

/**
 * Binding for invoking Vagrant.
 * <p/>
 * FIXME Should be a singleton, or at least use some sort of mutex per set of
 * FIXME parameters as Vagrant does not work well concurrently.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
public final class VagrantBinding {

    private final RubyBinding ruby;

    VagrantBinding(final File workDirectory) throws Exception {
        this(new File(workDirectory, "gems"),
             new File(workDirectory, "vagrant.d"),
             new File(workDirectory, "vagrantrc"),
             null,
             null);
    }

    public VagrantBinding(final File gemHome,
                          final File vagrantHome,
                          final File vagrantRc,
                          final Writer outWriter,
                          final Writer errWriter) {
        this.ruby = new RubyBinding(
                new HashMap<String, String>() {{
                    put("GEM_HOME", gemHome.getAbsolutePath());
                    put("VAGRANT_HOME", vagrantHome.getAbsolutePath());
                    put("VAGRANT_RC", vagrantRc.getAbsolutePath());
                }},
                new HashMap<String, String>() {{
                    put("vagrant", VAGRANT_VERSION);
                }},
                outWriter,
                errWriter
        );
    }

    public synchronized void cli(String... args) throws Exception {
        ruby.eval("puts 'Running \"vagrant " + join(args, " ") + "\"...'",
                  "Vagrant::Environment.new.cli '" + join(args, "','") + "'");
    }
}
