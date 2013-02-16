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

import de.saumya.mojo.ruby.script.ScriptException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Invokes Vagrant {@code halt} command.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @goal halt
 * @since 1.0
 */
@SuppressWarnings("unused")
public final class HaltMojo extends AbstractVagrantMojo {

    /** Mojo/Vagrant command name. */
    public static final String NAME = "halt";

    /**
     * VM name.
     *
     * @parameter
     */
    protected String vm;

    /**
     * Force shut down.
     *
     * @parameter default-value="false"
     */
    protected boolean force;

    @Override
    protected void doExecute() throws IOException, ScriptException {

        final List<String> args = new ArrayList<String>();

        args.add(NAME);

        if (vm != null)
            args.add(vm);

        if (force)
            args.add("--force");

        cli(args);
    }
}
