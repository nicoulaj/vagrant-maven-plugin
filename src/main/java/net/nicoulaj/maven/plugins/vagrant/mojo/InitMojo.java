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
package net.nicoulaj.maven.plugins.vagrant.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import static org.codehaus.plexus.util.StringUtils.isEmpty;

/**
 * Invokes Vagrant {@code init} command.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
@Mojo(name = InitMojo.NAME,
      requiresProject = true,
      inheritByDefault = false,
      threadSafe = false)
public final class InitMojo extends AbstractVagrantMojo {

    /** Mojo name * */
    public static final String NAME = "init";

    /** Box name. */
    @Parameter
    protected String boxName;

    /** Box URL. */
    @Parameter
    protected String boxURL;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            if (isEmpty(boxName))
                getVagrant().cli(NAME);

            else if (isEmpty(boxURL))
                getVagrant().cli(NAME, boxName);

            else
                getVagrant().cli(NAME, boxName, boxURL);

        } catch (Exception e) {
            throw new MojoFailureException("Vagrant execution failed", e);
        }
    }
}
