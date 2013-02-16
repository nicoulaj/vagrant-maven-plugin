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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.codehaus.plexus.util.StringUtils.isEmpty;
import static org.codehaus.plexus.util.StringUtils.join;

/**
 * Invokes Vagrant {@code package} command.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @goal package
 * @since 1.0
 */
@SuppressWarnings("unused")
public final class PackageMojo extends AbstractVagrantMojo {

    /** Mojo/Vagrant command name. */
    public static final String NAME = "package";

    /**
     * VM name.
     *
     * @parameter
     */
    protected String vm;

    /**
     * Name of a VM in virtualbox to package as a base box.
     *
     * @parameter
     */
    protected String base;

    /**
     * Name of the file to output.
     *
     * @parameter
     */
    protected File output;

    /**
     * Additional files to package with the box.
     *
     * @parameter
     */
    protected List<File> includes;

    /**
     * Vagrantfile to package with the box.
     *
     * @parameter
     */
    protected File vagrantfile;

    @Override
    protected void doExecute() throws IOException, ScriptException {

        final List<String> args = new ArrayList<String>();

        args.add(NAME);

        if (!isEmpty(vm))
            args.add(vm);

        if (!isEmpty(base)) {
            args.add("--base");
            args.add(base);
        }

        if (output != null) {
            args.add("--output");
            args.add(output.getAbsolutePath());
        }

        if (includes != null && !includes.isEmpty()) {
            args.add("--include");
            args.add(join(includes.iterator(), ","));
        }

        if (vagrantfile != null) {
            args.add("--vagrantfile");
            args.add(vagrantfile.getAbsolutePath());
        }

        cli(args);
    }
}
