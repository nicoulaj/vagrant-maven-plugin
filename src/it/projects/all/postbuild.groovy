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

    // box-add goal successful
    helper.assertBuildLogContains(":box-add")
    helper.assertBuildLogContains("Cleaning up downloaded box")

    // init goal successful
    helper.assertBuildLogContains(":init")
    helper.assertBuildLogContains("A `Vagrantfile` has been placed in this directory")
    helper.assertFileExists("Vagrantfile")
    helper.assertFileContains("Vagrantfile", "config.vm.box = \"testbox\"")

    // up goal successful
    helper.assertBuildLogContains(":up")
    helper.assertBuildLogContains("VM booted and ready for use!")

    // ssh-config goal successful
    helper.assertBuildLogContains(":ssh-config")
    helper.assertBuildLogContains("HostName 127.0.0.1")
    helper.assertBuildLogContains("User vagrant")
    helper.assertBuildLogContains("StrictHostKeyChecking no")

    // ssh goal successful
    helper.assertBuildLogContains(":ssh")
    helper.assertBuildLogContains("prebuild.groovy")
    helper.assertBuildLogContains("postbuild.groovy")
    helper.assertBuildLogContains("build.log")

    // provision goal successful
    helper.assertBuildLogContains(":provision")

    // reload goal successful
    helper.assertBuildLogContains(":reload")
    helper.assertBuildLogContains("Clearing any previously set forwarded ports")

    // suspend goal successful
    helper.assertBuildLogContains(":suspend")
    helper.assertBuildLogContains("Saving VM state and suspending execution")

    // resume goal successful
    helper.assertBuildLogContains(":resume")
    helper.assertBuildLogContains("Resuming suspended VM")

    // halt goal successful
    helper.assertBuildLogContains(":halt")
    helper.assertBuildLogContains("Attempting graceful shutdown of VM")

    // resume goal successful
    helper.assertBuildLogContains(":package")
    helper.assertBuildLogContains("Exporting VM")
    helper.assertFileExists("target/packaged.box")

    // destroy goal successful
    helper.assertBuildLogContains(":destroy")
    helper.assertBuildLogContains("Destroying VM and associated drives")

}
catch (Exception e) {
    System.err.println(e.getMessage())
    return false;
}
