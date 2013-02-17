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

import net.nicoulaj.maven.plugins.vagrant.it.PrePostBuildScriptHelper

try {
    helper = new PrePostBuildScriptHelper(basedir, localRepositoryPath, context)

    // vagrant-maven-plugin invoked
    helper.assertBuildLogContains("vagrant-maven-plugin:")

    // init goal successful
    helper.assertBuildLogContains(":init")
    helper.assertBuildLogContains("A `Vagrantfile` has been placed in this directory")
    helper.assertFileExists("Vagrantfile")
    helper.assertFileContains("Vagrantfile", "config.vm.box = \"testbox\"")

}
catch (Exception e) {
    System.err.println(e.getMessage())
    return false;
}
