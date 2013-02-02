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


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static org.codehaus.plexus.util.StringUtils.join;

/**
 * Wrapper around {@link ScriptEngine} for Ruby that adds support for:
 * <ul>
 * <li>Custom environment</li>
 * <li>Resolution of gem dependencies</li>
 * </ul>
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
final class RubyBinding {

    private static final String NL = System.getProperty("line.separator");

    private static final String TAB = "  ";

    private static final String JRUBY_SCRIPT_ENGINE_NAME = "jruby";

    private final Map<String, String> env;

    private final Map<String, String> gems;

    private final ScriptEngine engine;

    RubyBinding() {
        this(new HashMap<String, String>(), new HashMap<String, String>());
    }

    RubyBinding(Map<String, String> env, Map<String, String> gems) {
        this(env, gems, null, null);
    }

    RubyBinding(Map<String, String> env) {
        this(env, new HashMap<String, String>(), null, null);
    }

    RubyBinding(Map<String, String> env, Map<String, String> gems, Writer outWriter, Writer errWriter) {
        this.env = unmodifiableMap(env);
        this.gems = unmodifiableMap(gems);
        this.engine = initEngine(outWriter, errWriter);
    }

    static ScriptEngine initEngine(Writer outWriter, Writer errWriter) {
        final ScriptEngine engine = new ScriptEngineManager().getEngineByName(JRUBY_SCRIPT_ENGINE_NAME);
        if (engine == null) throw new IllegalStateException("Failed to initialize JRuby script engine");
        if (outWriter != null) engine.getContext().setWriter(outWriter);
        if (errWriter != null) engine.getContext().setErrorWriter(errWriter);
        return engine;
    }

    public void eval(String... script) throws ScriptException {

        final StringBuilder sb = new StringBuilder();

        sb.append("require 'rubygems'").append(NL);

        // Export env
        for (Map.Entry<String, String> entry : env.entrySet())
            sb.append("ENV['" + entry.getKey() + "'] = '" + entry.getValue() + "'").append(NL);

        // Define main method
        sb.append("def main").append(NL);

        // Add requires
        for (String gem : gems.keySet())
            sb.append(TAB).append("require '" + gem + "'").append(NL);

        // Add actual script
        sb.append(TAB).append(join(script, NL + TAB)).append(NL);

        // Main method defined
        sb.append("end").append(NL);

        // Define main block
        sb.append("begin").append(NL);

        // Add gem requirements
        for (Map.Entry<String, String> gem : gems.entrySet())
            sb.append(TAB).append("gem '" + gem.getKey() + "', '=" + gem.getValue() + "'").append(NL);

        // Call main method
        sb.append(TAB).append("main").append(NL);

        // Catch load errors and try fetching missing dependencies
        // FIXME Find a way to make gem fetching more verbose, see Gem::ConfigFile.really_verbose() ?
        sb.append("rescue LoadError").append(NL)
          .append(TAB).append("require 'rubygems/dependency_installer'").append(NL);
        for (Map.Entry<String, String> gem : gems.entrySet())
            sb.append(TAB).append("puts 'Fetching " + gem.getKey() + " " + gem.getValue() + "...'").append(NL)
              .append(TAB).append("Gem::DependencyInstaller.new.install '" + gem.getKey() + "', '=" + gem.getValue() + "'").append(NL);

        // Try calling main method again after dependency resolution
        sb.append(TAB).append("main").append(NL)
          .append("end").append(NL);

        // FIXME Unwrap throwables (eg EvalFailedExceptions)
        engine.eval(sb.toString());
    }
}
