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

import static org.codehaus.plexus.util.StringUtils.isBlank;
import static org.codehaus.plexus.util.StringUtils.isEmpty;
import static org.codehaus.plexus.util.StringUtils.isNotBlank;

/**
 * Invokes Vagrant {@code init} command.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @goal init
 * @since 1.0
 */
@SuppressWarnings("unused")
public final class InitMojo extends AbstractVagrantMojo {

    /** Mojo/Vagrant command name. */
    public static final String NAME = "init";

    /**
     * Box name.
     *
     * @parameter
     */
    protected String box;

    /**
     * Box URL.
     *
     * @parameter
     */
    protected String url;

    @Override
    protected void doExecute() throws IOException, ScriptException {

        if (isBlank(box))
            cli(NAME);

        else if (isBlank(url))
            cli(NAME, box);

        else
            cli(NAME, box, url);
    }
}
