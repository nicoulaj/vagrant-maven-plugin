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
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

/**
 * Invokes Vagrant {@code box add} command.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
@SuppressWarnings("unused")
@Mojo(name = "box-add")
public final class BoxAddMojo extends AbstractVagrantMojo {

    /** Mojo/Vagrant command name. */
    public static final String NAME = "box-add";

    /**
     * Box name.
     */
    @Parameter(required = true)
    protected String box;

    /**
     * VM URL.
     */
    @Parameter(required = true)
    protected String url;

    /**
     * Overwrite an existing box if it exists.
     */
    @Parameter(defaultValue = "false")
    protected boolean force;

    @Override
    protected void doExecute() throws IOException, ScriptException {

        if (force)
            cli("box", "add", box, url, "--force");

        else
            cli("box", "add", box, url);
    }
}
