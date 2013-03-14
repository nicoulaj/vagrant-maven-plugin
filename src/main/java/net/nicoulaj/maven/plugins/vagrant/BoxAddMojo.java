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

import static org.codehaus.plexus.util.StringUtils.isNotBlank;

/**
 * Invokes Vagrant {@code box add} command.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @goal box-add
 * @since 1.0
 */
@SuppressWarnings("unused")
public final class BoxAddMojo extends AbstractVagrantMojo {

    /** Mojo/Vagrant command name. */
    public static final String NAME = "box-add";

    /**
     * Box name.
     *
     * @parameter
     * @required
     */
    protected String box;

    /**
     * VM URL.
     *
     * @parameter
     * @required
     */
    protected String url;

    /**
     * Overwrite an existing box if it exists.
     *
     * @parameter default-value="false"
     */
    protected boolean force;

    /**
     * If given, Vagrant will verify the box you're adding is for the given provider. By default, Vagrant automatically detects the proper provider to use.
     *
     * @parameter
     */
    protected String provider;

    @Override
    protected void doExecute() throws IOException, ScriptException {

        final List<String> args = new ArrayList<String>();

        args.add("box");
        args.add("add");
        args.add(box);
        args.add(url);

        if (isNotBlank(provider)) {
            args.add("--provider");
            args.add(provider);
        }

        if (force)
            args.add("--force");

        cli(args);
    }
}
