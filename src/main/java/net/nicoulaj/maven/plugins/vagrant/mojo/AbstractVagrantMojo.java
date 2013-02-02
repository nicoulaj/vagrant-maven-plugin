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

import net.nicoulaj.maven.plugins.vagrant.binding.VagrantBinding;
import net.nicoulaj.maven.plugins.vagrant.util.MavenLogWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

import static org.codehaus.plexus.util.FileUtils.resolveFile;

/**
 * Base class for {@code Mojo}s invoking Vagrant.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
abstract class AbstractVagrantMojo extends AbstractMojo {

    /**
     * The Maven project.
     *
     * @since 1.0
     */
    @Parameter(property = "project", required = true, readonly = true)
    protected MavenProject project;

    /**
     * Custom {@code GEM_HOME}, which is the directory where Ruby gem dependencies and binaries are fetched.
     * <p/><p/>
     * Modifying this property has an impact on isolation/build portability, eg:
     * <ul>
     * <li>In {@code project.build.directory} (default): gems are downloaded every time, but user environment has no impact on execution.</li>
     * <li>In {@code project.basedir}: gems can be downloaded only once, but files are created outside of build directory.</li>
     * <li>In {@code ~/.gem}: user gems can be directly used, but files are created outside of project structure.</li>
     * </ul>
     *
     * @since 1.0
     */
    @Parameter(defaultValue = "${project.build.directory}/vagrant/gems")
    protected String gemHome;

    /**
     * Custom {@code VAGRANT_HOME}, which is the directory where Vagrant boxes are stored.
     * <p/><p/>
     * Modifying this property has an impact on isolation/build portability, eg:
     * <ul>
     * <li>In {@code project.build.directory} (default): boxes must be imported every time, but user installation is not impacted.</li>
     * <li>In {@code project.basedir}: boxes can be imported only once, but files are created outside of build directory.</li>
     * <li>In {@code ~/.vagrant.d} (Vagrant default): user boxes can be directly used, but files are created outside of project structure.</li>
     * </ul>
     *
     * @since 1.0
     */
    @Parameter(defaultValue = "${project.build.directory}/vagrant/vagrant.d")
    protected String vagrantHome;

    /**
     * Custom {@code VAGRANT_RC}, which is the configuration file used by Vagrant.
     * <p/><p/>
     * Modifying this property has an impact on isolation/build portability, eg:
     * <ul>
     * <li>In {@code project.build.directory} (default): user installation can not impact build.</li>
     * <li>In {@code project.basedir}: user installation can not impact build.</li>
     * <li>In {@code ~/.vagrantrc} (Vagrant default): user installation can impact build.</li>
     * </ul>
     *
     * @since 1.0
     */
    @Parameter(defaultValue = "${project.build.directory}/vagrant/vagrantrc")
    protected String vagrantRc;

    /** Binding used to invoke Vagrant. */
    private VagrantBinding vagrant;

    /**
     * Access Vagrant binding.
     *
     * @return a configured instance of {@link VagrantBinding}
     */
    protected VagrantBinding getVagrant() {
        if (vagrant == null)
            vagrant = new VagrantBinding(
                    resolveFile(new File(project.getBuild().getDirectory()), gemHome),
                    resolveFile(new File(project.getBuild().getDirectory()), vagrantHome),
                    resolveFile(new File(project.getBuild().getDirectory()), vagrantRc),
                    new MavenLogWriter(getLog(), false),
                    new MavenLogWriter(getLog(), true)
            );
        return vagrant;
    }
}
