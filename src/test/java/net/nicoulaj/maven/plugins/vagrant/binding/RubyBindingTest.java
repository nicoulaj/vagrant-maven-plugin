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

import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Tests for {@link RubyBinding}.
 *
 * @author <a href="http://github.com/nicoulaj">Julien Nicoulaud</a>
 * @since 1.0
 */
public class RubyBindingTest {

    @Test
    public void init_no_exception_thrown() throws Exception {
        new RubyBinding();
    }

    @Test
    public void eval_no_exception_thrown() throws Exception {
        new RubyBinding().eval("1+1");
    }

    @Test
    public void env_correctly_set() throws Exception {
        new RubyBinding(new HashMap<String, String>() {{
            put("MY_VAR", "test");
        }}).eval("if ENV['MY_VAR'] != 'test'",
                 "  raise 'MY_VAR is not correctly set'",
                 "end");
    }
}
