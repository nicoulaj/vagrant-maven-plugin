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

import de.saumya.mojo.gem.AbstractGemMojo;
import de.saumya.mojo.jruby.AbstractJRubyMojo;
import de.saumya.mojo.ruby.gems.GemManager;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.plexus.archiver.UnArchiver;
import org.sonatype.plexus.build.incremental.BuildContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static java.util.Arrays.asList;
import static org.codehaus.plexus.util.StringUtils.isNotBlank;

/**
 * Base class for {@code Mojo}s invoking Vagrant.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
abstract class AbstractVagrantMojo extends AbstractGemMojo {

    /**
     * Custom gems directory.
     * <p/>
     * Modifying this property has an impact on isolation/build portability, eg:
     * <ul>
     * <li>In {@code project.build.directory} (default): user installation can not impact build, but gems are unpacked at every build.</li>
     * <li>In {@code project.basedir}: user installation can not impact build, gems are unpacked once for all, but files are created outside of build directory.</li>
     * <li>Outside {@code project.basedir} (Vagrant default): user installation can impact build.</li>
     * </ul>
     *
     * @since 1.0
     */
    @Parameter(defaultValue = "${project.build.directory}/vagrant/gems")
    protected File gemHome;

    /**
     * Custom {@code VAGRANT_HOME}, which is the directory where Vagrant boxes are stored.
     * <p/>
     * Modifying this property has an impact on isolation/build portability, eg:
     * <ul>
     * <li>In {@code project.build.directory} (default): boxes must be imported every time, but no file is created outside of build directory.</li>
     * <li>In {@code project.basedir}: boxes can be imported once for all, but files are created outside of build directory.</li>
     * <li>In {@code ~/.vagrant.d} (Vagrant default): user boxes can be directly used, but files are created outside of project structure.</li>
     * </ul>
     *
     * @since 1.0
     */
    @Parameter(defaultValue = "${project.build.directory}/vagrant/vagrant.d")
    protected File vagrantHome;

    /**
     * Custom {@code VAGRANT_RC}, which is the configuration file used by Vagrant.
     * <p/>
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
    protected File vagrantRc;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Parameter( defaultValue = "${project}", readonly = true )
    protected MavenProject project;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Parameter( defaultValue = "${plugin}", readonly = true )
    protected PluginDescriptor plugin;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Component
    protected RepositorySystem repositorySystem;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Parameter( readonly = true, defaultValue="${localRepository}" )
    protected ArtifactRepository localRepository;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Parameter( readonly = true, defaultValue="${dummy}" )
    protected ClassRealm classRealm;

    /**
     * Required by {@link AbstractGemMojo}.
     *
     * @component role-hint="zip"
     */
    @Component(hint="zip")
    protected UnArchiver unzip;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Component
    protected GemManager manager;

    /**
     * Required by {@link AbstractGemMojo}.
     */
    @Component
    protected BuildContext buildContext;

    /** Setup {@link AbstractGemMojo}. */
    private void setup() throws MojoFailureException {
        super.project = this.project;
        super.localRepository = this.localRepository;
        super.classRealm = this.classRealm;
        super.repositorySystem = this.repositorySystem;
        super.jrubyFork = true;
        super.jrubyVerbose = false;
        super.unzip = unzip;
        super.plugin = plugin;
        super.includeOpenSSL = true;
        super.includeRubygemsInTestResources = false;
        super.installRDoc = false;
        super.installRI = false;
        super.gemUseSystem = false;
        super.gemHome = this.gemHome;
        super.gemPath = this.gemHome;
        super.binDirectory = new File(gemHome.getAbsolutePath() + "-" + plugin.getArtifactId(), "bin");
        super.supportNative = true;
        super.manager = this.manager;
        try {
            Field field = AbstractJRubyMojo.class.getDeclaredField("buildContext");
            field.setAccessible(true);
            field.set(this, buildContext);
        } catch (NoSuchFieldException e) {
            throw new MojoFailureException("Mojo configuration failed",e);
        } catch (IllegalAccessException e) {
            throw new MojoFailureException("Mojo configuration failed",e);
        }
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        setup();
        super.execute();
    }

    @Override
    protected final void executeWithGems() throws MojoExecutionException, MojoFailureException {
        try {
            doExecute();
        } catch (IOException e) {
            throw new MojoFailureException("Vagrant execution failed", e);
        } catch (ScriptException e) {
            throw new MojoFailureException("Vagrant execution failed", e);
        }
    }

    abstract protected void doExecute() throws IOException, ScriptException;

    protected final void cli(String... args) throws IOException, ScriptException {
        cli(asList(args));
    }

    protected final void cli(Iterable<String> args) throws IOException, ScriptException {
        factory.addEnv("VAGRANT_HOME", vagrantHome);
        factory.addEnv("VAGRANT_RC", vagrantRc);
        final Script script = factory.newScriptFromSearchPath("vagrant");
        for (String arg : args) if (isNotBlank(arg)) script.addArg(arg);
        script.execute();
    }
}
